package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.AutonomousValues;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import kotlin.Unit;

@Autonomous(name = "Red- Collecting Stones Like I'm Thanos")
public class NewTwoStone extends LinearOpMode {
    private boolean isRed = true;
    private int sidemult = 1;
    public static int skystoneSpot = 3;
    private String webcamName;
    private OpenCvCamera webcam;
    private Spotter spot;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(telemetry, new Location(), hardwareMap);

        webcamName= isRed ? "Webcam 2" : "Webcam 1";

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        waitForStart();
////

        AutonomousValues.offset=(3-skystoneSpot)*-180;
        telemetry.update();
        Pose2d startPose = new Pose2d(-33 * 25.4, -63.0 * 25.4, -3.14);
        telemetry.update();
        webcam.closeCameraDevice();
        // r.chainbar.autoHold();
        r.movey.dropItLikeItsHot();
        r.chainbar.goPlace();

        r.chainbar.grabOpen();
        r.rrBot.setPoseEstimate(startPose);
        //go to stone grab
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                .addMarker(.75, () -> {
                    r.intake.turbo();
                    return Unit.INSTANCE;
                })
                .addMarker(1, () -> {
                    r.intake.intake(1);
                    return Unit.INSTANCE;
                })
                .lineTo(new Vector2d(startPose.getX()+15 + AutonomousValues.offset, startPose.getY() + 680), new SplineInterpolator(r.rrBot.getPoseEstimate().getHeading(), startPose.getHeading() + Math.toRadians(-45)))
                .build());

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11 * 25.4).back(8*25.4).build());

        r.chainbar.goDown();

        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
                .splineTo(new Pose2d(0, -40.0 * 25.4, Math.toRadians(-180)))
                .splineTo(new Pose2d(52 * 25.4, -31 * 25.4, Math.toRadians(-90)))
                .addMarker(() -> {
                    r.intake.intake(0);
                    r.chainbar.grabClose();
                    r.movey.grabFoundation();
                    return Unit.INSTANCE;
                }).build());
        r.chainbar.goUpAll();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                .forward(17 * 25.4)
                .addMarker(() -> {
                    r.intake.turbo();
                    r.chainbar.grabOpen();
                    r.chainbar.goDown();
                    return Unit.INSTANCE;
                })
                .addMarker(2.5, () -> {
                    r.chainbar.grabOpen();
                    r.movey.dropItLikeItsHot();
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(30 * 25.4,-55 * 25.40, Math.toRadians(-205)))
                .splineTo(new Pose2d(12 * 25.4, -48 * 25.40, Math.toRadians(-180)))
                .splineTo(new Pose2d(startPose.getX()+AutonomousValues.offset+610, startPose.getY() + 600, Math.toRadians(135)))

                .build());
        r.chainbar.goUpBit();
        r.intake.intake(1);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11*25.4).build());
        r.chainbar.goDown();
        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder().reverse()
                        .splineTo(new Pose2d(19 * 25.4, -39 * 25.40, Math.toRadians(-180)))
                        .reverse()
                        .forward(4*25.4)
                        .build());




//        telemetry.update();
//        Pose2d startPose = new Pose2d(-33 * 25.4, -63.0 * 25.4, -3.14);
//        telemetry.update();
//        webcam.closeCameraDevice();
//        // r.chainbar.autoHold();
//        r.movey.dropItLikeItsHot();
//        r.chainbar.goPlace();
//
//        r.chainbar.grabOpen();
//        r.rrBot.setPoseEstimate(startPose);
//        //go to stone grab
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
//                .addMarker(.75, () -> {
//            r.intake.turbo();
//            return Unit.INSTANCE;
//        })
//                .addMarker(1, () -> {
//                    r.intake.intake(1);
//                    return Unit.INSTANCE;
//                })
//                .lineTo(new Vector2d(startPose.getX()+15 + AutonomousValues.offset, startPose.getY() + 680), new SplineInterpolator(r.rrBot.getPoseEstimate().getHeading(), startPose.getHeading() + Math.toRadians(-45)))
//                .build());
//
//        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11 * 25.4).back(8*25.4).build());
//
//        r.chainbar.goDown();
//
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
//                .splineTo(new Pose2d(0, -40.0 * 25.4, Math.toRadians(-180)))
//                .splineTo(new Pose2d(52 * 25.4, -31 * 25.4, Math.toRadians(-90)))
//                .addMarker(() -> {
//                    r.intake.intake(0);
//                    r.chainbar.grabClose();
//                    r.movey.grabFoundation();
//                    return Unit.INSTANCE;
//                }).build());
//        r.chainbar.goUpAll();
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
//                .forward(17 * 25.4)
//                .addMarker(() -> {
//                    r.intake.turbo();
//                    r.chainbar.grabOpen();
//                    r.chainbar.goDown();
//                    return Unit.INSTANCE;
//                })
//                .addMarker(2.5, () -> {
//                    r.chainbar.grabOpen();
//                    r.movey.dropItLikeItsHot();
//                    return Unit.INSTANCE;
//                })
//                .splineTo(new Pose2d(30 * 25.4,-38 * 25.40, Math.toRadians(-205)))
//                .splineTo(new Pose2d(12 * 25.4, -38 * 25.40, Math.toRadians(-180)))
//                .splineTo(new Pose2d(startPose.getX()+AutonomousValues.offset+700, startPose.getY() + 600, Math.toRadians(135)))
//
//                .build());
//        r.chainbar.goUpBit();
//        r.intake.intake(1);
//        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11*25.4).build());
//        r.chainbar.goDown();
//        r.rrBot.followTrajectorySync(
//                r.rrBot.fastTrajectoryBuilder().reverse()
//                .splineTo(new Pose2d(19 * 25.4, -39 * 25.40, Math.toRadians(-180)))
//                 .reverse()
//                 .forward(4*25.4)
//                .build());


//        builder1 = TrajectoryBuilder(Pose2d(42.0,-31.0,(-90.0).toRadians), constraints)
//
//        builder1     .splineTo(Pose2d(20.0,-37.0,Math.PI))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(Pose2d(20.0,-37.0,Math.PI), constraints)
//        builder1.strafeTo(Vector2d(0.0,-36.0))
//                .splineTo(Pose2d(-19.0,-33.0,(135.0N).toRadians))
//        list.add(builder1.build())
//
//        builder1 = TrajectoryBuilder(Pose2d(-19.0,-33.0,(135.0).toRadians), constraints)
//        builder1.reverse().splineTo(Pose2d(20.0,-37.0,Math.PI))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(Pose2d(20.0,-37.0,(180.0).toRadians), constraints)
//        builder1.splineTo(Pose2d(-26.0,-33.0,(135.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(-26.0,-33.0,(135.0).toRadians),
//                .......
//                constraints)
//        builder1.reverse().splineTo(Pose2d(20.0,-37.0,(180.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(20.0,-37.0,(180.0).toRadians), constraints)
//        builder1.splineTo(Pose2d(-31.0,-30.0,(135.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(-31.0,-30.0,(135.0).toRadians), constraints)
//        builder1.reverse().splineTo( Pose2d(20.0,-37.0,(180.0).toRadians)).reverse().back(25.0)
//        list.add(builder1.build())
    }
}
