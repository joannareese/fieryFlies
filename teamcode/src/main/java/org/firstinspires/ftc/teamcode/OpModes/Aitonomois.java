package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "oneStone Auto", group = "trajPaths")
public class Aitonomois extends LinearOpMode {
    public static int skystoneSpot;
    private Robot r;
    private Boolean arewered;
    private int sideMult = 1;
    private OpenCvInternalCamera webcam;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        while (arewered == null) {
            telemetry.addData("", "what side are we a = red b = blue");
            telemetry.update();
            if (gamepad1.a) {
                arewered = true;
                sideMult = 1;
            }
            if (gamepad1.b) {
                arewered = false;
                sideMult = -1;
            }
        }
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        webcam.openCameraDevice();
        webcam.setPipeline(new Spotter());
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        while (!isStarted()) {
            telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.update();
        }
        waitForStart();
        webcam.closeCameraDevice();
        waitForStart();
        webcam.closeCameraDevice();
        if (isStopRequested()) return;
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
        r.intake.turbo();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20 * 25.4, RobotValues.yPos1 * 25.4 * sideMult, 0)).build());
        r.intake.intake(0);
        r.lifty.grabOpen();
        r.lifty.intoGround();
        sleep(1000);
        if (skystoneSpot != 3)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(6 * 25.4).build());
        r.turnSync(Math.toRadians(sideMult * -85));
        if (skystoneSpot != 3)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(6 * 25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back((65 - (sideMult * RobotValues.yPos1)) * 25.4).build());
        r.lifty.grabClose();
        r.lifty.goDown();
        sleep(1000);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(15 * 25.4).build());
    }
}

