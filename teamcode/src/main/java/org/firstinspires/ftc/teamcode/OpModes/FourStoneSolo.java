package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

public class FourStoneSolo extends LinearOpMode {
    private Robot r;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        waitForStart();
        r.intake.intake(1);
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().splineTo(new Pose2d(99.67,909.053,Math.toRadians(-55))).build());
    }
}
