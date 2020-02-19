package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.AutonomousValues;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import kotlin.Unit;

@Autonomous(name = "Blue Vision")
public class VisionBlue extends LinearOpMode {
    public static int skystoneSpot = 1;
    private Robot r;
    private boolean isRed;
    private int sidemult;
    private String webcamName = "Webcam 2";
    private OpenCvCamera webcam;
    private Spotter spot;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Pose2d startPose = new Pose2d(-33 * 25.4, sidemult*-63.0 * 25.4, -3.14);
        r.rrBot.setPoseEstimate(startPose);
    msStuckDetectStop=2000;
//
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
//        while (!isStarted() && !isStopRequested()) {
//
//            telemetry.addData("Skystone Spot: ", skystoneSpot);
//            telemetry.addData("skystone x", spot.best);
//            telemetry.update();
//        }
                    telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.addData("skystone x", spot.best);
            telemetry.update();
        waitForStart();
        AutonomousValues.offset=(3-skystoneSpot)*-195;
        telemetry.addData("skystoneSpit",skystoneSpot);
        telemetry.update();
        //AutonomousValues.autoChainbarOffset=550;

        r.movey.get_in_position();
        r.chainbar.goPlace();
       // webcam.closeCameraDevice();
        r.chainbar.grabOpen();
        r.rrBot.setPoseEstimate(startPose);


        //go to stone grab
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                .addMarker(.5, () -> {
                    r.intake.turbo();
                    return Unit.INSTANCE;
                })
                .addMarker(.75, () -> {
                    r.Motor5.setPower(.40);
                    r.Motor6.setPower(.55);

                    return Unit.INSTANCE;
                })
                .lineTo(new Vector2d(startPose.getX() + AutonomousValues.offset-60, startPose.getY() + sidemult*820), new SplineInterpolator(r.rrBot.getPoseEstimate().getHeading(), startPose.getHeading() + Math.toRadians(sidemult*-45)))
                .build());


        //movechainbardown
        r.chainbar.goDown();


        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
                //this bit of code may help correct for stones that are intook wonky
                .addMarker(.2,() -> {
                    r.chainbar.liftKinda();
                    return Unit.INSTANCE;
                })
                .addMarker(.5,() -> {
                    r.chainbar.goDown();
                    return Unit.INSTANCE;
                })
                               .addMarker(.8,() -> {
            r.chainbar.grabClose();
            return Unit.INSTANCE;
        })
                //go under bridge
                .splineTo(new Pose2d(0, sidemult*-40.0 * 25.4, Math.toRadians(-180)))
                //after the bridge is cleared start lifting
                .addMarker(() -> {
                    r.chainbar.grabClose();
                    r.chainbar.liftKinda();
                    return Unit.INSTANCE;
                })
                //go to foundation
                .splineTo(new Pose2d(43 * 25.4, sidemult*-32 * 25.4, Math.toRadians(sidemult*-90)))
                .reverse()
                .back(3*25.4)
                //get a good grip on foundation
                .addMarker(() -> {
                    r.intake.intake(0);
                    r.chainbar.grabClose();
                    r.movey.grabFoundation();
                    return Unit.INSTANCE;
                }).build());
        //spit out and also drop
        r.intake.turbo();
        r.chainbar.grabOpen();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                //start moving chainbar back to a good pos
                .addMarker(.75, () -> {
                    r.chainbar.intoGround();
                    return Unit.INSTANCE;
                })
                .addMarker(1.5, () -> {
                    r.chainbar.grabOpen();
                    r.movey.dropItLikeItsHot();
                    return Unit.INSTANCE;
                })

                //mess with this
                .splineTo(new Pose2d(28 * 25.4,sidemult*-42* 25.40, Math.toRadians(-180)))
                .build());
                r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()

                        //.forward(29*25.4)

                .splineTo(new Pose2d(    AutonomousValues.offset+0* 25.4, sidemult*-42 * 25.40, Math.toRadians(-180)))

                //turn intake back on
                .addMarker( () -> {
                    r.Motor5.setPower(.4);
                    r.Motor6.setPower(.55);

                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(startPose.getX()+AutonomousValues.offset+625, sidemult*startPose.getY() + 650, Math.toRadians(sidemult*135)))
                .addMarker( () -> {
                    r.chainbar.goUpBalance();
                    return Unit.INSTANCE;
                })
                .forward(13*25.4)
                .build());

//
       r.chainbar.goDown();

        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder().reverse()
                        .addMarker(new Vector2d(0*25.4,sidemult*-39*25.4),() -> {
                            r.chainbar.grabClose();
                            return Unit.INSTANCE;})
                        .addMarker(new Vector2d(5*25.4,sidemult*-39*25.4),() -> {
                            r.chainbar.goUpAll();
                            return Unit.INSTANCE;})
                        .splineTo(new Pose2d(0* 25.4, sidemult*-39 * 25.40, Math.toRadians(-180)))

                        .splineTo(new Pose2d(35* 25.4, sidemult*-39 * 25.40, Math.toRadians(-180)))

                        .build());
        r.chainbar.grabOpen();
        r.chainbar.goDown();
        sleep(1000);
        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder()
                .forward(28*25.4)
                .build()
        );

    }
}