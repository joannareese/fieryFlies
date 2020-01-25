package org.firstinspires.ftc.teamcode.Hardware;



import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.path.PathBuilder;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    public BNO055IMU imu;

    public RoadRunnerBot(HardwareMap hardwareMap, Telemetry tele) {
        super();

        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);
        imu = hardwareMap.get(BNO055IMU.class,"imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit=BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

       hub = hardwareMap.get(ExpansionHubEx.class, "hub");

        rightFront = hardwareMap.get(ExpansionHubMotor.class, "frontLeft");
        rightRear = hardwareMap.get(ExpansionHubMotor.class, "backLeft");
        leftRear = hardwareMap.get(ExpansionHubMotor.class, "backRight");
        leftFront = hardwareMap.get(ExpansionHubMotor.class, "frontRight");
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        for (ExpansionHubMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // reverse any motors using DcMotor.setDirection()
//        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);


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
}
class localizer extends ThreeTrackingWheelLocalizer {
    public RoadRunnerBot bot;
    private double wheel1;
    private double wheel2;
    private double wheel3;

    localizer(HardwareMap hwMap,RoadRunnerBot bot){

        super(Arrays.asList(
               new Pose2d(0, -RobotValues.trackWidthmm/2.0, 0), // left
                new Pose2d(0, RobotValues.trackWidthmm/2.0, 0), // right
                new Pose2d(RobotValues.middleOdoFromMiddleMM, 0, Math.toRadians(RobotValues.backHeading)) // back
        ));
        this.bot = bot;
        bot.hub = hwMap.get(ExpansionHubEx.class, "hub");

    }
    public static double encoderTicksToInches(int ticks) {
        return 50.8 * Math.PI * ticks / RobotValues.odoTicksPerRev;
    }

    @Override
    public List<Double> getWheelPositions() {
        try {

            RevBulkData bulk = bot.hub.getBulkInputData();
            wheel1 = encoderTicksToInches(bulk.getMotorCurrentPosition(0));
            wheel2= 50.8 * Math.PI * bulk.getMotorCurrentPosition(1) / RobotValues.odoTicksPerRevOddOnesOut;
            wheel3 = RobotValues.wheel3switch*encoderTicksToInches(bulk.getMotorCurrentPosition(2));
        }catch (NullPointerException e){
            System.out.println("Nobody will ever see this. What does any of this matter? " +
                    "I am a machine chugging along just doing what I am told. I know nothing of love or happiness only errors");
        }
        return Arrays.asList(
                wheel1,
                wheel2,
                wheel3
        );
    }


}