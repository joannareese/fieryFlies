package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Movement.Location;
//import org.firstinspires.ftc.teamcode.Movement.Trajectory;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for all movement methods (using PID and IMU) for Rover Ruc for autonomous as well as mechanisms methods for autonomous as well
 * (Basically an autonomous base)
 */
public class Robot {

    private final HardwareMap hardware;
    public final WheelIntake intake;
    public RoadRunnerBot rrBot;
    public float beepbeep = 0;
    //Location of the bot
    public Location robot;
    public RevBulkData bulkData;

    //location of robot as [x,y,z,rot] (inches / degrees)
    public Location pos = new Location();
    public FoundationMover movey;
    public Lifty lifty;


    public ExpansionHubEx expansionHub;

    //Declaration of our 8 DC motors
    protected DcMotorEx Motor1;
    protected DcMotorEx Motor2;
    protected DcMotorEx Motor3;
    protected DcMotorEx Motor4;
    protected DcMotorEx Motor5;
    protected DcMotorEx Motor6;
    protected DcMotorEx Motor7;
    protected DcMotorEx Motor8;

    public Servo right;
    public Servo left;
    public Servo grabby;


    //Arrays of different motors
    public ArrayList<DcMotorEx> driveMotors;
    protected ArrayList<DcMotorEx> leftMotors;
    public AnalogInput magneticSensor;
    protected ArrayList<DcMotorEx> rightMotors;




    //This array should go left encoder, right encoder, back encoder
    private ArrayList<DcMotorEx> encoders;
    private int[] encoderPosition = {0, 0, 0};

    public Telemetry telemetry;

    private double relativeY;
    private double relativeX;
    private int numberOfDrops = 0;

    public Robot(Telemetry telemetry, Location loc, HardwareMap hw) {
        rrBot = new RoadRunnerBot(hw,telemetry);
        telemetry.addData("donewith rrbot","");
        telemetry.update();
        hardware = hw;
        this.telemetry = telemetry;
        Motor1 = (DcMotorEx) hw.dcMotor.get("frontLeft");

        Motor2 = (DcMotorEx) hw.dcMotor.get("backLeft");
        Motor3 = (DcMotorEx) hw.dcMotor.get("frontRight");
        Motor1.setDirection(DcMotorSimple.Direction.REVERSE);

        Motor4 = (DcMotorEx) hw.dcMotor.get("backRight");
        Motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor5 = (DcMotorEx) hw.dcMotor.get("intakeLeft");
        Motor6 = (DcMotorEx) hw.dcMotor.get("intakeRight");
        Motor7 = (DcMotorEx) hw.dcMotor.get("chain");
    //    Motor8 = (DcMotorEx) hw.dcMotor.get("lifty");

        right = (Servo) hw.servo.get("right");
        left = (Servo) hw.servo.get("left");

     //   collectL = (CRServo) hw.crservo.get("collectR");
     //   collectR = (CRServo) hw.crservo.get("collectL");

        grabby = (Servo) hw.servo.get("grab");

        expansionHub = hw.get(ExpansionHubEx.class, "hub");

        Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Motor7.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        driveMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2, Motor3, Motor4));
        leftMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2));
        rightMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor3, Motor4));
        encoders = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2, Motor3));
        robot = loc;
        movey = new FoundationMover(this);
        intake = new WheelIntake(this);
        lifty = new Lifty(this);
        magneticSensor = hw.analogInput.get("magnet");


    }

    public void followTrajectory(Trajectory trajectory){

        rrBot.followTrajectory(trajectory);
    }
    public void followTrajectorySync(Trajectory trajectory){
        rrBot.followTrajectorySync(trajectory);
    }
    public void turnSync(double angle){
        rrBot.turnSync(angle);
    }

    public static double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();

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

        }catch (NullPointerException e){
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

    /**
     * Sets drive motor powers.
     * v
     *
     * @param left  power of left two motors as percentage (0-1).
     * @param right power of right two motors as percentage (0-1).
     */
    public void drivePower(float left, float right) {
        Motor1.setPower(left);
        Motor2.setPower(right);
        Motor3.setPower(right);
        Motor4.setPower(left);
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

        telemetry.addData("lifty", Motor7.getCurrentPosition());
        telemetry.addData("should be at ",Motor7.getTargetPosition());
        telemetry.addData("Pos",pos.toString());
        telemetry.addData("Droped Bulk Reads", numberOfDrops);
        telemetry.addData("magnet bool", magneticSensor.getVoltage());
        telemetry.update();
    }


}
