package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class Robot {

    public final WheelIntake intake;
    private final HardwareMap hardware;
    public final Servo capstone;

    public RoadRunnerBot rrBot;
    //Location of the bot

    public RevBulkData bulkData;

    //location of robot as [x,y,z,rot] (inches / degrees)
    public Location pos = new Location();
    public FoundationMover movey;
    public Lifty lifty;


    public ExpansionHubEx expansionHub;
    public Servo foundation;
    public Servo grabby;
    //Arrays of different motors
    public ArrayList<DcMotorEx> driveMotors;
    public Telemetry telemetry;
    public Chainbar chainbar;
    public DcMotorEx Motor7;
    public DcMotorEx Motor8;
    public int stackTarget = 1;
    public boolean deployChainbarin500;
    //Declaration of our 8 DC motors
    protected DcMotorEx Motor1;
    protected DcMotorEx Motor2;
    protected DcMotorEx Motor3;
    protected DcMotorEx Motor4;
    public DcMotorEx Motor5;
    public DcMotorEx Motor6;

    //This array should go left encoder, right encoder, back encoder

    private int[] encoderPosition = {0, 0, 0};
    private double relativeY;
    private double relativeX;
    public int numberOfDrops = 0;
    public ElapsedTime time;
    private boolean checkForAction;
    private long Order66AtT;
    private boolean hasNotGoneDown = true;



    public Robot(Telemetry telemetry, Location loc, HardwareMap hw) {
        //Declare and init devices
        hardware = hw;
        AutonomousValues.autoChainbarOffset=0;
        Motor1 = (DcMotorEx) hw.dcMotor.get("frontLeft");
        Motor2 = (DcMotorEx) hw.dcMotor.get("backLeft");
        Motor3 = (DcMotorEx) hw.dcMotor.get("frontRight");
        Motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor4 = (DcMotorEx) hw.dcMotor.get("backRight");
        Motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor5 = (DcMotorEx) hw.dcMotor.get("intakeLeft");
        Motor6 = (DcMotorEx) hw.dcMotor.get("intakeRight");
        Motor7 = (DcMotorEx) hw.dcMotor.get("lifty");
        Motor8 = (DcMotorEx) hw.dcMotor.get("chain");
        driveMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2, Motor3, Motor4));

        foundation = hw.servo.get("foundation");
        capstone = hw.servo.get("capstone");
        grabby = hw.servo.get("grab");
        expansionHub = hw.get(ExpansionHubEx.class, "hub");
        //Change motor values as needed
        Motor7.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Motor7.setTargetPosition(Motor7.getCurrentPosition());
        Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motor8.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor8.setTargetPosition(0);
        Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motor8.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //init different mechanisms as needed
        movey = new FoundationMover(this);
        intake = new WheelIntake(this);
        lifty = new Lifty(this);
        chainbar = new Chainbar(this);
        rrBot = new RoadRunnerBot(hw, telemetry,this);

        //essentially random stuff u dont need to mess with
        expansionHub.setLedColor(255, 0, 0);
        expansionHub.setPhoneChargeEnabled(true);
        ExpansionHubEx hubx = hw.get(ExpansionHubEx.class,"hubx");
        hubx.setPhoneChargeEnabled(true);
        hubx.setLedColor(255,0,0);
        time = new ElapsedTime();
        this.telemetry = telemetry;

    }

    public void followTrajectorySync(Trajectory trajectory) {
        rrBot.followTrajectorySync(trajectory);
    }

    public void turnSync(double angle) {
        rrBot.turnSync(angle);
    }

    public void updatePosition() {
        try {
            bulkData = expansionHub.getBulkInputData();
            double[] encoderDeltamm = new double[3];
            for (int i = 0; i < 3; i++) {
                if (0 == i)
                    encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRevOddOnesOut);
                else
                    encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRev);
                encoderPosition[i] = bulkData.getMotorCurrentPosition(i);
            }
            double botRotDelta = (encoderDeltamm[0] - encoderDeltamm[1]) / RobotValues.trackWidthmm;
            relativeX = encoderDeltamm[2] - (RobotValues.middleOdoFromMiddleMM * botRotDelta);
            relativeY = (encoderDeltamm[0] + encoderDeltamm[1]) / 2;
            pos.setRotation((float) (Math.toDegrees(((RobotValues.odoDiamMM * Math.PI * ((bulkData.getMotorCurrentPosition(0)) / RobotValues.odoTicksPerRevOddOnesOut) - (RobotValues.odoDiamMM * Math.PI * ((bulkData.getMotorCurrentPosition(1)) / RobotValues.odoTicksPerRev)))) / RobotValues.trackWidthmm)));

            if (Math.abs(botRotDelta) > 0) {
                double radiusOfMovement = (encoderDeltamm[0] + encoderDeltamm[1]) / (2 * botRotDelta);
                double radiusOfStraif = relativeX / botRotDelta;

                relativeY = (radiusOfMovement * Math.sin(botRotDelta)) - (radiusOfStraif * (1 - Math.cos(botRotDelta)));

                relativeX = radiusOfMovement * (1 - Math.cos(botRotDelta)) + (radiusOfStraif * Math.sin(botRotDelta));
            }
            pos.translateLocal(relativeY, relativeX, 0);
            telemetryMethod();

        } catch (NullPointerException e) {
            numberOfDrops++;
        }
        bulkData = expansionHub.getBulkInputData();
        double[] encoderDeltamm = new double[3];
        for (int i = 0; i < 3; i++) {
            if (0 == i)
                encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRevOddOnesOut);
            else
                encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRev);
            encoderPosition[i] = bulkData.getMotorCurrentPosition(i);
        }
        double botRotDelta = (encoderDeltamm[0] - encoderDeltamm[1]) / RobotValues.trackWidthmm;
        relativeX = encoderDeltamm[2] - (RobotValues.middleOdoFromMiddleMM * botRotDelta);
        relativeY = (encoderDeltamm[0] + encoderDeltamm[1]) / 2;
        pos.setRotation((float) (Math.toDegrees(((RobotValues.odoDiamMM * Math.PI * ((bulkData.getMotorCurrentPosition(0)) / RobotValues.odoTicksPerRevOddOnesOut) - (RobotValues.odoDiamMM * Math.PI * ((bulkData.getMotorCurrentPosition(1)) / RobotValues.odoTicksPerRev)))) / RobotValues.trackWidthmm)));

        if (Math.abs(botRotDelta) > 0) {
            double radiusOfMovement = (encoderDeltamm[0] + encoderDeltamm[1]) / (2 * botRotDelta);
            double radiusOfStraif = relativeX / botRotDelta;

            relativeY = (radiusOfMovement * Math.sin(botRotDelta)) - (radiusOfStraif * (1 - Math.cos(botRotDelta)));

            relativeX = radiusOfMovement * (1 - Math.cos(botRotDelta)) + (radiusOfStraif * Math.sin(botRotDelta));
        }
        pos.translateLocal(relativeY, relativeX, 0);
        telemetryMethod();

    }



    public void drivePower(double[] powers) throws InvalidParameterException {
        if (powers.length != 4)
            throw new InvalidParameterException("BOI YOUR ARRAY NEEDS TO HAVE 4 VALUES");
        int i = 0;
        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setPower(powers[i]);
            i++;
        }
    }


    /**
     * @param mode DcMotor.RunMode of what you want to set the motors to sets for all drive motors
     */
    public void driveMode(DcMotor.RunMode mode) {
        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setMode(mode);
        }
    }


    /**
     * A simple method to output the status of all motors and other variables to telemetry.
     */
    public void telemetryMethod() {
        telemetry.addData("Where are we stacking bois",stackTarget);
        telemetry.addData("powermult",RobotValues.power);

        telemetry.addData("lift", Motor7.getCurrentPosition());
        telemetry.addData("should be at ", Motor7.getTargetPosition());
        telemetry.addData("Chainbar", Motor8.getCurrentPosition());
        telemetry.addData("should be at ", Motor8.getTargetPosition());
        rrBot.updatePoseEstimate();
        telemetry.addData("Pos", rrBot.getPoseEstimate().toString());
        telemetry.addData("left", Motor1.getCurrentPosition());
        telemetry.addData("right ", Motor2.getCurrentPosition());
        telemetry.addData("battery",expansionHub.read12vMonitor(ExpansionHubEx.VoltageUnits.VOLTS));
        telemetry.speak("Hello, I am Jack ");
        telemetry.addData("center", Motor5.getCurrentPosition());
        chainbar.umcapstoneDepoy();


        telemetry.update();
    }


    public void update() {
        updatePosition();
        if(stackTarget<1){
            stackTarget = 1;
        }
        telemetryMethod();
        if(Motor8.getCurrentPosition()>3000&&hasNotGoneDown){
            stackTarget++;
            hasNotGoneDown=false;
        }
        if(Motor8.getCurrentPosition()<2500){
            hasNotGoneDown= true;
        }

        if(deployChainbarin500){

            deployChainbarin500=false;
            checkForAction = true;
            Order66AtT = time.now(TimeUnit.MILLISECONDS)+500;
        }
        if(checkForAction&&time.now(TimeUnit.MILLISECONDS)>Order66AtT){
            checkForAction=false;
            chainbar.goUpAll();
        }
        lifty.update();
    }
}
