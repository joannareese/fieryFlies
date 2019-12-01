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

        Trajectory trajectoryPt2ElectricBoogallo =r.rrBot.trajectoryBuilder().reverse() //first movement
                .splineTo(new Pose2d(-23*25.4, Spotter.yPos2*25.4, 0)).build(); //RIGHT --> -18.0, center/Left -25
//
//        Trajectory trajectoryP3ElectricBoogallo =r.rrBot.trajectoryBuilder()
//                .back(18*25.4)
//                .strafeRight(40*25.4)//cross map
//                //.splineTo(new Pose2d(-47*25.4, 10*25.4, 0))
//                .build();
//        Trajectory blockPos2 = r.rrBot.trajectoryBuilder()
//                .back(23*25.4)
//                .strafeLeft(.1*25.4).reverse()
//                .splineTo(new Pose2d(-524,569, 5.826))
//                //.splineTo(new Pose2d(18*25.4, -Spotter.yPos2*25.4,90 )) //RIGHT -4, LEFT -10, CENTER, -8
//                .build();
//        Trajectory blockDeposit2 =r.rrBot.trajectoryBuilder()    //crossmap 2
////                .splineTo(new Pose2d(RobotValues.x1*25.4, RobotValues.y1*25.4, RobotValues.heading1))
//                //.back(5*25.4)
//                //.splineTo(new Pose2d(RobotValues.x*25.4, RobotValues.y*25.4, RobotValues.heading))
//                .strafeRight(50*25.4)
//                .build();
//        Trajectory park = r.rrBot.trajectoryBuilder().strafeRight(12*25.40).build();

        telemetry.addData("Skystone Spot: ", skystoneSpot);
        telemetry.update();

        waitForStart();

        //webcam.closeCameraDevice();

        if (isStopRequested()) return;
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);
        r.lifty.goUpAll();
        sleep(1000);
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeTo(new Vector2d(-18*25.4,-42*25.4)).build());
        r.lifty.goUpBit();
        sleep(1000);
        if(Spotter.yPos2==-9.0){
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(5*25.4).splineTo(new Pose2d(-524,569, 5.826)).build());
        r.lifty.goUpAll();
        sleep(1000);
        r.rrBot.turnSync(Math.PI*2-5.826);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(72).build());}
        /**
         * TODO ADD CASE IF IT ISNT THAT ONE THING LOL
         */
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeTo(new Vector2d(-23*25.4,-42*25.4)).build());//cross map
    }
}