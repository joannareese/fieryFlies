package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
//
@Autonomous(name = "oneStone Auto", group="trajPaths")
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

        Trajectory trajectoryPt2ElectricBoogallo =r.rrBot.trajectoryBuilder() //first movement
                .splineTo(new Pose2d(23*25.4, Spotter.yPos1*-25.4, Math.toRadians(90))).build(); //RIGHT --> -18.0, center/Left -25
        Trajectory trajectoryP3ElectricBoogallo =r.rrBot.trajectoryBuilder()
                .strafeLeft(72*25.4)//cross map
                //.splineTo(new Pose2d(-47*25.4, 10*25.4, 0))
                .build();
        Trajectory blockPos2 = r.rrBot.trajectoryBuilder()
                .strafeRight(45*25.4)
                //.splineTo(new Pose2d(18*25.4, -Spotter.yPos2*25.4,90 )) //RIGHT -4, LEFT -10, CENTER, -8
                .build();
        Trajectory blockDeposit2 =r.rrBot.trajectoryBuilder()    //crossmap 2
//                .splineTo(new Pose2d(RobotValues.x1*25.4, RobotValues.y1*25.4, RobotValues.heading1))
                //.back(5*25.4)
                //.splineTo(new Pose2d(RobotValues.x*25.4, RobotValues.y*25.4, RobotValues.heading))
                .strafeLeft(50*25.4)
                .build();
        Trajectory park = r.rrBot.trajectoryBuilder().strafeRight(12*25.40).build();

        telemetry.addData("Skystone Spot: ", skystoneSpot);
        telemetry.update();

        waitForStart();

        //webcam.closeCameraDevice();

        if (isStopRequested()) return;
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);
        r.intake.zuuuck();
        sleep(2000);
        r.rrBot.setPoseEstimate(new Pose2d(0,0,0));
        r.intake.nozuck();//
        r.followTrajectorySync(trajectoryP3ElectricBoogallo);
        r.intake.unzuck();
        sleep(2000);
        r.rrBot.setPoseEstimate(new Pose2d(0,0,0));
        r.rrBot.followTrajectorySync(blockPos2);
        r.rrBot.setPoseEstimate(new Pose2d(0,0,0));
        r.rrBot.followTrajectorySync(blockDeposit2);
        r.rrBot.setPoseEstimate(new Pose2d(0,0,0));
        r.followTrajectorySync(park);//cross map
      //  r.followTrajectorySync(blockPos2);                    //block 2 pickup
     //   sleep(2000);
       // r.followTrajectorySync(blockDeposit2);                //crossmap2
    }
}