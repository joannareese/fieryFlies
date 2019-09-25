package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for all movement methods (using PID and IMU) for Rover Ruckus for autonomous as well as mechanisms methods for autonomous as well
 * (Basically an autonomous base)
 */
public class Robot {
    private final HardwareMap hardware;
    public float beepbeep = 0;
    //Declaration of our 8 DC motors
    protected DcMotorEx Motor1;
    protected DcMotorEx Motor2;
    protected DcMotorEx Motor3;
    protected DcMotorEx Motor4;
    protected DcMotorEx Motor5;
    protected DcMotorEx Motor6;
    protected DcMotorEx Motor7;
    protected DcMotorEx Motor8;
    //Location of the bot
    public Location robot;
    //Array of different types of things
    protected ArrayList<DcMotorEx> driveMotors;
    protected ArrayList<DcMotorEx> leftMotors;
    /**
     * Sets all drive motor run modes to given mode.
     *
     * @param mode name DcMotor mode to given value.
     */
    protected ArrayList<DcMotorEx> rightMotors;
    BNO055IMU imu;
    private Orientation angles;
    private int gameState = 0;
    public RevBulkData bulkData;
    private ExpansionHubEx expansionHub;
    //This array should go left encoder, right encoder, back encoder
    private ArrayList<DcMotorEx> encoders;
    private int[] encoderPosition = {0, 0, 0};
    private Telemetry telemetry;
    private long prevTime;
    private int prevEncoder;
    private float velocity = 0f;
    private float wheelDistance = 6.66f;                //distance from center of robot to center of wheel (inches)
    private float wheelDiameter = 4;                //diameter of wheel (inches)
    //location of robot as [x,y,z,rot] (inches / degrees)
    private Location pos = new Location();
    //-----motors-----//
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    public Robot(Telemetry telemetry, Location loc, HardwareMap hw) {
        hardware = hw;
        this.telemetry = telemetry;
        Motor1 = (DcMotorEx) hw.dcMotor.get("frontLeft");
        Motor2 = (DcMotorEx) hw.dcMotor.get("backLeft");
        Motor3 = (DcMotorEx) hw.dcMotor.get("frontRight");
        Motor4 = (DcMotorEx) hw.dcMotor.get("backRight");
        expansionHub = hw.get(ExpansionHubEx.class, "Expansion Hub 2");


        driveMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2, Motor3, Motor4));
        leftMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2));
        rightMotors = new ArrayList<DcMotorEx>(Arrays.asList(Motor3, Motor4));
        encoders = new ArrayList<DcMotorEx>(Arrays.asList(Motor1, Motor2, Motor3));
        for (DcMotorEx motorEx : driveMotors) {
            //motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           // motorEx.setTargetPositionTolerance(50);
        }
        robot=loc;
        BNO055IMU.Parameters noots = new BNO055IMU.Parameters();
        noots.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        noots.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        noots.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        noots.loggingEnabled = true;
        noots.loggingTag = "IMU";
        noots.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hw.get(BNO055IMU.class, "imu");
        imu.initialize(noots);
    }


    double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
    public void updatePosition2(){
//        if(Math.abs(angleIncrement) > 0){
//            double radiusOfMovement = (wheelRightDeltaScale+wheelLeftDeltaScale)/(2*angleIncrement);
//            double radiusOfStraif = r_xDistance/angleIncrement;
//            relativeY = (radiusOfMovement * Math.sin(angleIncrement)) + (radiusOfStraif * (1 - Math.cos(angleIncrement)));
//            relativeX = radiusOfMovement * (1 - Math.cos(angleIncrement)) + (radiusOfStraif * Math.sin(angleIncrement));

        }

    /**
     * Sets drive motor powers.
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

    public void drivePower(float[] powers) throws InvalidParameterException {
        if (powers.length != 4)
            throw new InvalidParameterException("BOI YOUR ARRAY NEEDS TO HAVE 4 VALUES");
        int i = 0;
        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setPower(i);
            i++;
        }
    }

    /**
     * Sets drive motor target encoder to given values.
     *
     * @param left  encoder set for left motors.
     * @param right encoder set for right motors.
     */
    public void drivePosition(int left, int right) {
        frontLeft.setTargetPosition(left);
        frontRight.setTargetPosition(right);
        backRight.setTargetPosition(right);
        backLeft.setTargetPosition(left);
    }

    public void updatePosition() {
        bulkData = expansionHub.getBulkInputData();
        double[] encoderDeltamm = new double[3];
        for (int i = 0; i < 3; i++) {
            encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - bulkData.getMotorCurrentPosition(i)) / RobotValues.twentyTicksPerRev);
            encoderPosition[i] = bulkData.getMotorCurrentPosition(i);
        }
        double botRotDelta = (encoderDeltamm[0] - encoderDeltamm[1]) / RobotValues.odoDistBetweenMM;
        double robotXDelta = encoderDeltamm[2] - RobotValues.middleOdoFromMiddleMM * botRotDelta;
        double robotYDelta = (encoderDeltamm[0] - encoderDeltamm[1]) / 2;
        robot.translateLocal(robotYDelta, robotXDelta, botRotDelta);

    }

    public void driveMode(DcMotor.RunMode mode) {
        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setMode(mode);
        }
    }

    /**
     * Stops all drive motors and resets encoders.
     */
    public void stopAllMotors() {
        drivePower(0f, 0f);
        driveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    /**
     * A simple method to output the status of all motors and other variables to telemetry.
     */
    public void telemetryMethod() {
        telemetry.addData("Party has started", "woot woot");
        telemetry.addData("Game State = ", gameState);
        String motorString = "FL = " + frontLeft.getCurrentPosition() + " BL = " + backLeft.getCurrentPosition() + " FR = " + frontRight.getCurrentPosition() + " BR = " + backRight.getCurrentPosition();
        telemetry.addData("Drive = ", motorString);
        telemetry.addData("Pos = ", pos);
        telemetry.addData("Velocity = ", velocity);
        telemetry.update();
    }

    /**
     * Calibrates the imu, probably best to do in init
     * May take a hot second.
     */
    public void calibrateHeading() {
        BNO055IMU.Parameters noots = new BNO055IMU.Parameters();
        noots.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        noots.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        noots.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        noots.loggingEnabled = true;
        noots.loggingTag = "IMU";
        noots.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(noots);

        BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
        String filename = "BNO055IMUCalibration.json";
        File file = AppUtil.getInstance().getSettingsFile(filename);
        ReadWriteFile.writeFile(file, calibrationData.serialize());

        pos.setRotation((imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)).firstAngle);

        telemetry.update();
        telemetry.log().add("IMU: CALIBRATED", filename);
        telemetry.update();
    }

    /**
     * Compares given heading value with IMU heading value. If less than error, returns true.
     *
     * @param heading the heading to check for, heading in is degrees
     * @param err     the amount of error (in degrees) allowed to return true
     * @return boolean.
     */
    public boolean checkHeading(float heading, float err) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return Math.abs(heading - angles.firstAngle) < err;
    }


}
