package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "2stone")
public class Aitonomois extends LinearOpMode {
    public static int skystoneSpot;
    public Robot r;
    private double skystone2Pos= -15;
    private OpenCvCamera webcam;
    private Spotter spot;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(),hardwareMap);
        
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        while (!isStarted()) {
            telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.addData("skystone x",spot.best);
            telemetry.update();
        }
        waitForStart();
        if(RobotValues.yPos1==1){
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5*25.4).build());
            skystone2Pos=-9;}
        if(RobotValues.yPos1==3){
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(8*25.4).build());
            skystone2Pos=-24;}

        r.turnSync(Math.toRadians(RobotValues.heading));
        r.intake.turbo();
        sleep(250);
        r.intake.intake(1);
        r.lifty.goUpBit();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward((23-4)*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(12*25.4).build());
        sleep(500);

        r.lifty.goDown();
        r.intake.intake(0);
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(-24*25.4,-24*25.4,0)).reverse().back(25*25.4).build());
        r.intake.intake(1);
        sleep(2000);

        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(5*25.4).splineTo(new Pose2d(skystone2Pos*25.4,-24*25.4,Math.toRadians(-55))).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(12*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(-24*25.4,-24*25.4,0)).reverse().back(25*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5*25.4).build());

        //r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().splineTo(new Pose2d(-30*25.4,-36*25.4,Math.toRadians(55))).build());

    }
}
