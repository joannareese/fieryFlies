package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.HeadingInterpolator;
import com.acmerobotics.roadrunner.path.heading.LinearInterpolator;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Mechanum;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

//
@Autonomous(name = "oneStone Auto", group = "trajPaths")
public class Aitonomois extends LinearOpMode {
    Robot r;
    public static int skystoneSpot;

    //
    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);

//        OpenCvWebcam webcam;
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//
//        webcam = new OpenCvWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//
//        webcam.openCameraDevice();
//
//        webcam.setPipeline(new Spotter());
//
//        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        Trajectory trajectoryPt2ElectricBoogallo = r.rrBot.trajectoryBuilder().reverse() //first movement
                .splineTo(new Pose2d(-23 * 25.4, RobotValues.y * 25.4, 0)).build(); //RIGHT --> -18.0, center/Left -25


        telemetry.addData("Skystone Spot: ", Spotter.yPos2);
        telemetry.update();

        waitForStart();

        //webcam.closeCameraDevice();

        if (isStopRequested()) return;
        //go to the First Stone
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);
        r.lifty.goUpAll();
        sleep(1000);
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().lineTo(new Vector2d(-15 * 25.4, -42 * 25.4),new LinearInterpolator(r.rrBot.getPoseEstimate().getHeading(),0)).build());
        r.lifty.goUpBit();
        sleep(1000);
        if (RobotValues.areWeFar) {
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(10 * 25.4).reverse().splineTo(new Pose2d(-500, 650, 5.8),new SplineInterpolator(2*Math.PI,5.8)).build());
            r.lifty.goUpAll();
            sleep(1000);
            r.rrBot.turnSync(Math.PI * 2 - 5.8);
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(72).build());
        } else {
            //second stone
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().lineTo((new Vector2d(-23 * 25.4, RobotValues.yPos1 * 25.4))).build());
            r.lifty.goUpAll();
            sleep(1000);
            r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(-18 * 25.4, -70 * 25.4,0)).build());//cross map
            r.lifty.goDown();
            sleep(1000);
            r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeTo(new Vector2d(-18 * 25.4, -42 * 25.4)).build());//cross map

        }
    }
}