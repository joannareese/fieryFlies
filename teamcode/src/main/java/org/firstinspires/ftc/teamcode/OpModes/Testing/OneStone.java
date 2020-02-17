package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
@Disabled
@Autonomous(name = "One Stone0 Auto PROB NO WORKY")
public class OneStone extends LinearOpMode {
    public static int skystoneSpot = 1;
    public Robot r;
    private double skystone2Pos = -15;
    private OpenCvCamera webcam;
    private Spotter spot;
    private boolean isRed;
    private int sidemult;
    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        msStuckDetectStop = 8000;
        r = new Robot(telemetry, new Location(), hardwareMap);
        while (!isStopRequested() && !gamepad1.x) {
            telemetry.addData("press a to toggle side", " x to save and move on");
            telemetry.addData("Side:", isRed ? "red" : "blue");
            if (gamepad1.a) {
                isRed = !isRed;
            }
            webcamName = isRed ? "Webcam 1" : "Webcam 2";
            telemetry.update();
        }
        if (isStopRequested()) {
            stop();
        }
        if (isRed) {
            sidemult = 1;
        } else {
            sidemult = -1;
        }
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        while (!isStarted() && !isStopRequested()) {
            if (isStopRequested()) {
                webcam.closeCameraDevice();
                break;
            }
            telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.addData("skystone x", spot.best);
            telemetry.update();
        }
        webcam.closeCameraDevice();
        if (!isStopRequested()) {
            r.chainbar.goUpBit();
            if (skystoneSpot == 3) {
                telemetry.addData("thing", "3")
                ;
                telemetry.update();
                if (isRed) {
                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5 * 25.4).build());
                } else
                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(7 * 25.4).build());
                skystone2Pos = -9;
            } else if (skystoneSpot == 2) {
                telemetry.addData("thing", "2");
                telemetry.update();
                r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(4 * 25.4).build());
            } else if (skystoneSpot == 1) {
                telemetry.addData("thing", "1")
                ;
                telemetry.update();
                if (isRed) {

                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(7 * 25.4).build());
                } else {
                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5 * 25.4).build());
                }
                skystone2Pos = -24;
            }

            r.turnSync(Math.toRadians(RobotValues.heading * sidemult));
            r.intake.turbo();
            Thread.sleep(250);
            r.intake.intake(1);
            r.chainbar.autoHold();

            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward((24) * 25.4).build());
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(8 * 25.4).build());
            Thread.sleep(500);


            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(-24 * 25.4, sidemult * -24 * 25.4, 0)).reverse().back(25 * 25.4).build());
//        r.intake.intake(0);
//        r.Chainbar.goDown();
//        Thread.sleep(500);
//        r.Chainbar.grabClose();
//        Thread.sleep(250);
//        r.Chainbar.goUpAll();
//
//        Thread.sleep(5000);
//        r.Chainbar.grabOpen();
//
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(5 * 25.4).splineTo(new Pose2d(skystone2Pos * 25.4, -24 * 25.4, Math.toRadians(-55))).build());
//        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(12 * 25.4).build());
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(-24 * 25.4, -24 * 25.4, 0)).reverse().back(25 * 25.4).build());
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5 * 25.4).build());
        }
    }
}
