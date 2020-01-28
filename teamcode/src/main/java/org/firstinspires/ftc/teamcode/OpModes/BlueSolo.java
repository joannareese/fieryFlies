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

@Autonomous(name = "Blue- Collecting Stones Like I'm Thanos")
public class BlueSolo extends LinearOpMode {
    public static int skystoneSpot = 1;
    private Robot r;
    private boolean isRed = false;
    private int sidemult=-1;
    private String webcamName = "Webcam 1";
    private OpenCvCamera webcam;
    private Spotter spot;

    @Override
    public void runOpMode() throws InterruptedException {
        msStuckDetectStop=8000;

        r = new Robot(telemetry, new Location(), hardwareMap);
        Pose2d startPose = new Pose2d(-33 * 25.4, 63.0 * 25.4, -3.14);
        r.rrBot.setPoseEstimate(startPose);

        webcamName= isRed ? "Webcam 2" : "Webcam 1";

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        while (!isStarted() && !isStopRequested()) {

            telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.addData("skystone x", spot.best);
            telemetry.update();
            AutonomousValues.offset=(3-skystoneSpot)*-195;
        }

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
                .lineTo(new Vector2d(startPose.getX() + AutonomousValues.offset-15, startPose.getY() + sidemult*680), new SplineInterpolator(r.rrBot.getPoseEstimate().getHeading(), startPose.getHeading() + Math.toRadians(-45*sidemult)))
                .build());

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11 * 25.4).back(8*25.4).build());

        r.chainbar.goDown();

        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
                .splineTo(new Pose2d(0, sidemult*-40.0 * 25.4, Math.toRadians(-180)))
                .splineTo(new Pose2d(53  * 25.4, sidemult*-31* 25.4, Math.toRadians(sidemult*-90)))
                .addMarker(() -> {
                    r.intake.intake(0);
                    r.chainbar.grabClose();
                    r.movey.grabFoundation();
                    return Unit.INSTANCE;
                }).build());
        r.chainbar.liftKinda();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder()
                .forward(17 * 25.4)
                .addMarker(() -> {
                    r.intake.turbo();
                    r.chainbar.grabOpen();
                    r.chainbar.intoGround();
                    return Unit.INSTANCE;
                })
                .addMarker(2.5, () -> {
                    r.chainbar.grabOpen();
                    r.movey.dropItLikeItsHot();
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(30 * 25.4,sidemult*-55 * 25.40, Math.toRadians(sidemult*-205)))
                .splineTo(new Pose2d(12 * 25.4, sidemult*-48 * 25.40, Math.toRadians(sidemult*-180)))
                .splineTo(new Pose2d(startPose.getX()+AutonomousValues.offset+550, startPose.getY() + sidemult*600, Math.toRadians(sidemult*135)))

                .build());
        r.chainbar.goUpBit();
        r.intake.intake(1);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(11*25.4).build());
        r.chainbar.goDown();
        r.rrBot.followTrajectorySync(
                r.rrBot.fastTrajectoryBuilder().reverse()
                        .splineTo(new Pose2d(19 * 25.4, sidemult*-39 * 25.40, Math.toRadians(-180)))
                        .reverse()
                        .forward(4*25.4)
                        .build());
    }
}