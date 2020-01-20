//package org.firstinspires.ftc.teamcode.OpModes;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import kotlin.Unit;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.Hardware.Robot;
//import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
//import org.firstinspires.ftc.teamcode.Hardware.Spotter;
//import org.firstinspires.ftc.teamcode.Movement.Location;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//
//@Autonomous(name = "Solo Auto")
//public class SoloAuto extends LinearOpMode {
//    public static int skystoneSpot;
//    public Robot r;
//    private boolean isRed;
//    private boolean byWall;
//    private int sidemult = 1;
//    private OpenCvCamera webcam;
//    private Spotter spot;
//    private String webcamName;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        r = new Robot(telemetry, new Location(), hardwareMap);
//        while ((!isStarted() && !isStopRequested()) && (!gamepad1.x)) {
//            telemetry.addData("press a to togle side", "press b to toggle where to park and x to save and move on");
//            telemetry.addData("Side:", isRed ? "red" : "blue");
//            if (gamepad1.a) {
//                isRed = !isRed;
//            }
//            sidemult = isRed ? 1 : -1;
//
//            telemetry.update();
//        }
//        webcamName= isRed ? "Webcam 1" : "Webcam 2";
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
//        webcam.openCameraDevice();
//        spot = new Spotter();
//        webcam.setPipeline(spot);
//        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
//        while (!isStarted() && !isStopRequested()) {
//            if (isStopRequested()) {
//                webcam.closeCameraDevice();
//                break;
//            }
//            telemetry.addData("Skystone Spot: ", skystoneSpot);
//            telemetry.addData("skystone x", spot.best);
//            telemetry.update();}
//        waitForStart();
//        webcam.closeCameraDevice();
//        if (!isStopRequested()) {
//            r.chainbar.goUpBit();
//            if (skystoneSpot == 3) {
//                telemetry.addData("thing", "3")
//                ;
//                telemetry.update();
//                if (isRed) {
//                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5 * 25.4).build());
//                } else
//                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
//
//            } else if (skystoneSpot == 2) {
//                telemetry.addData("thing", "2");
//                telemetry.update();
//                r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(2 * 25.4).build());
//            } else if (skystoneSpot == 1) {
//                telemetry.addData("thing", "1")
//                ;
//                telemetry.update();
//                if (isRed) {
//
//                    r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(14 * 25.4).build());
//                } else {
//                    r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(10 * 25.4).build());
//                }
//
//            }
//
//            r.turnSync(Math.toRadians(RobotValues.heading * sidemult));
//            r.intake.turbo();
//            Thread.sleep(250);
//            r.intake.intake(1);
//            r.chainbar.autoHold();
//
//            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward((26) * 25.4).build());
//            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(8 * 25.4).build());
//            sleep(500);
//
//            //r.lifty.goDown();
//            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().addMarker(() -> {r.intake.intake(0);return Unit.INSTANCE;}).reverse().splineTo(new Pose2d(-24 * 25.4, sidemult * -24 * 25.4, 0)).reverse().back(50 * 25.4).build());
//            r.rrBot.setPoseEstimate(new Pose2d(0,0,r.rrBot.getLocalizer().getPoseEstimate().getHeading()));
////            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-15*25.4*sidemult,sidemult*9.5*25.4,Math.toRadians(90*sidemult))).reverse().back(5*25.4).build());
//           // r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-80 * 25.4, sidemult * 30.5 * 25.4, Math.toRadians(90 * sidemult))).reverse().back(5 * 25.4).build());
//            r.turnSync(Math.toRadians(90*sidemult));
//            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10*25.4).build());
//
//            r.movey.grabFoundation();
//            sleep(250);
//            r.intake.intake(0);
//            r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(12 * 25.4).build());
//            r.turnSync(Math.toRadians(-98 * sidemult));
//            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(15* 25.4).build());
//            r.movey.dropItLikeItsHot();
//            sleep(250);
//            if(isRed)
//                r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeLeft(3*25.4).build());
//            else
//            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(3*25.4).build());
//            r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(38 * 25.4).build());
//        }
//    }
//}
