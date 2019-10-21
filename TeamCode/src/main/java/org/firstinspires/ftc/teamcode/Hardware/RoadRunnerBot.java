package org.firstinspires.ftc.teamcode.Hardware;



import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Utils.LynxModuleUtil;
import org.firstinspires.ftc.teamcode.Utils.LynxOptimizedI2cFactory;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.ExpansionHubMotor;
import org.openftc.revextensions2.RevBulkData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.firstinspires.ftc.teamcode.Hardware.DriveConstants.encoderTicksToInches;

public class RoadRunnerBot extends SampleMecanumDriveBase {
    public ExpansionHubEx hub;
    private ExpansionHubMotor leftFront, leftRear, rightRear, rightFront;
    private List<ExpansionHubMotor> motors;
    private BNO055IMU imu;

    public RoadRunnerBot(HardwareMap hardwareMap) {
        super();

        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);

        // TODO: adjust the names of the following hardware devices to match your configuration
        // for simplicity, we assume that the desired IMU and drive motors are on the same hub
        // if your motors are split between hubs, **you will need to add another bulk read**
        hub = hardwareMap.get(ExpansionHubEx.class, "hub");

        imu = LynxOptimizedI2cFactory.createLynxEmbeddedImu(hub.getStandardModule(), 0);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        // BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);

        leftFront = hardwareMap.get(ExpansionHubMotor.class, "frontLeft");
        leftRear = hardwareMap.get(ExpansionHubMotor.class, "backLeft");
        rightRear = hardwareMap.get(ExpansionHubMotor.class, "backRight");
        rightFront = hardwareMap.get(ExpansionHubMotor.class, "frontRight");


        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        for (ExpansionHubMotor motor : motors) {
            // if you keep it, then don't tune kStatic or kA
            // otherwise, comment out the following line
            //motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // reverse any motors using DcMotor.setDirection()
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);


        setLocalizer(new localizer(hardwareMap,this));
    }

    @Override
    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {
        PIDFCoefficients coefficients = leftFront.getPIDFCoefficients(runMode);
        return new PIDCoefficients(coefficients.p, coefficients.i, coefficients.d);
    }

    @Override
    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {
        for (ExpansionHubMotor motor : motors) {
            motor.setPIDFCoefficients(runMode, new PIDFCoefficients(
                    coefficients.kP, coefficients.kI, coefficients.kD, 1
            ));
        }
    }

    
    @Override
    public List<Double> getWheelPositions() {
            return Arrays.asList(0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        leftFront.setPower(v);
        leftRear.setPower(v1);
        rightRear.setPower(v2);
        rightFront.setPower(v3);
    }

    @Override
    public double getRawExternalHeading() {
        return imu.getAngularOrientation().firstAngle;
    }
}//
class localizer extends ThreeTrackingWheelLocalizer{
    public RoadRunnerBot bot;
    localizer(HardwareMap hwMap,RoadRunnerBot bot){

        super(Arrays.asList(
                new Pose2d(0, -RobotValues.trackWidthmm/2.0, 0), // left
                new Pose2d(0, RobotValues.trackWidthmm/2.0, 0), // right
                new Pose2d(RobotValues.middleOdoFromMiddleMM, 0, Math.toRadians(90)) // front
        ));
        this.bot = bot;
        bot.hub = hwMap.get(ExpansionHubEx.class, "hub");

    }
    public static double encoderTicksToInches(int ticks) {
        return 50.8 * Math.PI * ticks / RobotValues.odoTicksPerRev;
    }

    @Override
    public List<Double> getWheelPositions() {
        RevBulkData bulk = bot.hub.getBulkInputData();
        return Arrays.asList(
                /**
                 * TODO double check my math here
                 * I THINK I DID AN OOOF
                 */
                (double)(50.8*Math.PI* bulk.getMotorCurrentPosition(0)/RobotValues.odoTicksPerRevOddOnesOut),
                encoderTicksToInches(-bulk.getMotorCurrentPosition(1)),
                encoderTicksToInches(-bulk.getMotorCurrentPosition(2))
        );
    }
}