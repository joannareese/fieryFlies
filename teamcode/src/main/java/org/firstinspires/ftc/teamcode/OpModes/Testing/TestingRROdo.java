package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.GamePadHandler;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;


@TeleOp(name = "RR OdoTest")

public class TestingRROdo extends LinearOpMode {
    public Robot r;
    private GamePadHandler gamePadHandler;
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() throws InterruptedException {
        dashboard = FtcDashboard.getInstance();
        r = new Robot(telemetry, new Location(0, 0, 0, 0), hardwareMap);
        gamePadHandler = new GamePadHandler();
        waitForStart();
        while (!isStopRequested()) {
            gamePadHandler.handleInput(r,gamepad1,gamepad2);
            r.rrBot.updatePoseEstimate();
            Pose2d currentPose = r.rrBot.getPoseEstimate();
            Pose2d lastError = r.rrBot.getLastError();

            TelemetryPacket packet = new TelemetryPacket();
            Canvas fieldOverlay = packet.fieldOverlay();


            packet.put("x", currentPose.getX());
            packet.put("y", currentPose.getY());
            packet.put("heading", currentPose.getHeading());
            dashboard.sendTelemetryPacket(packet);
            telemetry.addData("x", currentPose.getX());
            telemetry.addData("x", currentPose.getY());
            telemetry.addData("x", currentPose.getHeading());
            telemetry.update();

        }


    }
}
