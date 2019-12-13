package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;


@TeleOp(name="ProToleOp", group="Outreach")

/**
 * This class is for our outreach robot that uses mecanum wheels.
 * This outreach bot was a prototype drivetrain to our two iterations of our competition robot from last year
 * This class allows the outreach bot to be omnidirectional
 */
public class ProteleOp extends OpMode {

    //DRIVETRAIN\\
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackLeft;
    public DcMotor motorBackRight;

    public Servo right;
    public Servo left;

    public int calibToggle;
    BNO055IMU imu;
    public Orientation angles;
    public Acceleration gravity;


    public void init() {

        //DRIVETRAIN\\
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");

        right = hardwareMap.servo.get("right");
        left = hardwareMap.servo.get("left");

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        calibToggle = 0;
    }


    public void loop() {

        ///////////////
        // GAMEPAD 1 //
        ///////////////
        if (gamepad1.y) { //orientation calibration
            // Get the calibration data
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            imu.initialize(parameters);

            BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
            String filename = "BNO055IMUCalibration.json";
            File file = AppUtil.getInstance().getSettingsFile(filename);
            ReadWriteFile.writeFile(file, calibrationData.serialize());
            telemetry.log().add("saved to '%s'", filename);
        }

        if (gamepad1.x) { //toggle on
            calibToggle = 1;
        }

        if (gamepad1.b) { //toggle off
            calibToggle = 0;
        }

        if (calibToggle == 1) { //when toggled we are oriented with this math
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
            double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x);
            double rightX = -gamepad1.right_stick_x;

            final double v5 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) + rightX;
            final double v6 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) - rightX;
            final double v7 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) + rightX;
            final double v8 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) - rightX;

            motorFrontLeft.setPower(v5*0.75);//1
            motorFrontRight.setPower(v6*0.75);//2
            motorBackLeft.setPower(v7*0.75);//3
            motorBackRight.setPower(v8*0.75);//4

        } else if (calibToggle == 0) { //regular drive
            double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            double rightX = -gamepad1.right_stick_x;
            double sinRAngle = Math.sin(robotAngle);
            double cosRAngle = Math.cos(robotAngle);

            final double v1 = (P * sinRAngle) + (P * cosRAngle) - rightX;
            final double v2 = (P * sinRAngle) - (P * cosRAngle) + rightX;
            final double v3 = (P * sinRAngle) - (P * cosRAngle) - rightX;
            final double v4 = (P * sinRAngle) + (P * cosRAngle) + rightX;

            motorFrontRight.setPower(v1*0.75);
            motorFrontLeft.setPower(v2*0.75);
            motorBackRight.setPower(v3*0.75);
            motorBackLeft.setPower(v4*0.75);
        }

        telemetry.addData("frontLeft: ", motorFrontLeft.getCurrentPosition());
        telemetry.addData("frontRight ", motorFrontRight.getCurrentPosition());
        telemetry.addData("backLeft: ", motorBackLeft.getCurrentPosition());
        telemetry.addData("backRight ", motorBackRight.getCurrentPosition());

    if(gamepad1.dpad_left){
        right.setPosition(0.5);
        left.setPosition(0.5);
    }if(gamepad1.dpad_right){
            right.setPosition(0.1);
            left.setPosition(0.1);
    }if(gamepad1.dpad_up){
            right.setPosition(0.9);
            left.setPosition(0.9);
        }
    }

//    private String composeTelemetry() {
//        // At the beginning of each telemetry update, grab a bunch of data
//        // from the IMU that we will then display in separate lines.
//        telemetry.addAction(new Runnable() {
//            @Override
//            public void run() {
//                // Acquiring the angles is relatively expensive; we don't want
//                // to do that in each of the three items that need that info, as that's
//                // three times the necessary expense.
//                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
//                gravity = imu.getGravity();
//            }
//        });
//
//        telemetry.addLine()
//                .addData("status", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return imu.getSystemStatus().toShortString();
//                    }
//                })
//                .addData("calib", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return imu.getCalibrationStatus().toString();
//                    }
//                });
//
//        telemetry.addLine()
//                .addData("heading", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return formatAngle(angles.angleUnit, angles.firstAngle);
//                    }
//                })
//                .addData("roll", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return formatAngle(angles.angleUnit, angles.secondAngle);
//                    }
//                })
//                .addData("pitch", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return formatAngle(angles.angleUnit, angles.thirdAngle);
//                    }
//                });
//
//        telemetry.addLine()
//                .addData("grvty", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return gravity.toString();
//                    }
//                })
//                .addData("mag", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return String.format(Locale.getDefault(), "%.3f",
//                                Math.sqrt(gravity.xAccel * gravity.xAccel
//                                        + gravity.yAccel * gravity.yAccel
//                                        + gravity.zAccel * gravity.zAccel));
//                    }
//                });
//        return formatAngle(angles.angleUnit, angles.firstAngle);
//    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatRadians(AngleUnit.RADIANS.fromUnit(angleUnit, angle));
    }

    String formatRadians(double radians) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.RADIANS.normalize(radians));
    } }