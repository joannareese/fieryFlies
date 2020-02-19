package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.AutonomousValues;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.revextensions2.ExpansionHubEx;

import kotlin.Unit;

@Autonomous(name = "red auto")
public class KM_And_Let_Me_See_It extends LinearOpMode {
    public static int skystoneSpot;
    private Robot r;
    private boolean isRed;
    private int sidemult;
    private String webcamName = "Webcam 2";
    private OpenCvCamera webcam;
    private Spotter spot;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Pose2d startPose = new Pose2d(-33 * 25.4, -63.0 * 25.4, -3.14);
        r.rrBot.setPoseEstimate(startPose);
    msStuckDetectStop=8000;

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
//        webcam.openCameraDevice();
//        spot = new Spotter();
//        webcam.setPipeline(spot);
//        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
//        while (!isStarted() && !isStopRequested()) {
//
//            telemetry.addData("Skystone Spot: ", skystoneSpot);
//            telemetry.addData("skystone x", spot.best);
//            telemetry.update();
//            AutonomousValues.offset=(3-skystoneSpot)*-195;
//        }
//        telemetry.update();
        waitForStart();
       // AutonomousValues.autoChainbarOffset=550;
        r.movey.get_in_position();
        r.chainbar.goPlace();
       // webcam.closeCameraDevice();
        r.chainbar.grabOpen();
        r.rrBot.setPoseEstimate(startPose);
        //go to stone grab
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                .addMarker(.25, () -> {
                    r.intake.turbo();
                    return Unit.INSTANCE;
                })
                .addMarker(1, () -> {
//                    r.intake.intake(.75);
                    r.Motor5.setPower(.40);
                    r.Motor6.setPower(.55);

                    return Unit.INSTANCE;
                })
                .lineTo(new Vector2d(startPose.getX() + AutonomousValues.offset-60, startPose.getY() + 820), new SplineInterpolator(r.rrBot.getPoseEstimate().getHeading(), startPose.getHeading() + Math.toRadians(-45)))
                .build());

        r.chainbar.goDown();


        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
                .addMarker(.5,() -> {
                    r.chainbar.grabClose();
                    return Unit.INSTANCE;
                })                .splineTo(new Pose2d(0, -40.0 * 25.4, Math.toRadians(-180)))
                .addMarker(() -> {
                    r.chainbar.goUpAll();
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(43 * 25.4, -32 * 25.4, Math.toRadians(-90)))
                .reverse()
                .back(3*25.4)
                .addMarker(() -> {
                    r.intake.intake(0);
                    r.chainbar.grabClose();
                    r.movey.grabFoundation();
                    return Unit.INSTANCE;
                }).build());
        r.chainbar.goUpAll();
        //r.turnSync(Math.toRadians(-90));
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                //.forward(11 * 25.4)
                .addMarker(() -> {
                    r.intake.turbo();
                    r.chainbar.grabOpen();
                    //r.Chainbar.intoGround();
                    return Unit.INSTANCE;
                })
                .addMarker(.75, () -> {
                    r.chainbar.intoGround();

                    return Unit.INSTANCE;
                })
                .addMarker(1.5, () -> {
                    r.chainbar.grabOpen();
                    r.movey.dropItLikeItsHot();
                    r.chainbar.intoGround();
                    return Unit.INSTANCE;
                })
//                .addMarker( () -> {
//                    r.intake.intake(1);
//                    return Unit.INSTANCE;
//                })
                .splineTo(new Pose2d(30 * 25.4,-48 * 25.40, Math.toRadians(-205)))
                .splineTo(new Pose2d(12 * 25.4, -48 * 25.40, Math.toRadians(-180)))
                .addMarker( () -> {
                    r.Motor5.setPower(.4);
                    r.Motor6.setPower(.55);

                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(startPose.getX()+AutonomousValues.offset+625, startPose.getY() + 620, Math.toRadians(135)))
                .addMarker( () -> {
                    r.chainbar.goUpBalance();

                    return Unit.INSTANCE;
                })
                //.forward(7*25.4)
                .build());
        r.chainbar.goUpBalance();

       r.chainbar.goDown();
        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder().reverse()
                        .addMarker(new Vector2d(0*25.4,-39*25.4),() -> {
                            r.chainbar.grabClose();
                            return Unit.INSTANCE;})
                        .addMarker(new Vector2d(5*25.4,-39*25.4),() -> {
                            r.chainbar.goUpAll();
                            return Unit.INSTANCE;})
                        .splineTo(new Pose2d(35* 25.4, -39 * 25.40, Math.toRadians(-180)))
                        .build());
        r.chainbar.grabOpen();
        r.chainbar.goDown();
        sleep(1000);
        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder()
                .forward(22*25.4)
                .build()
        );
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