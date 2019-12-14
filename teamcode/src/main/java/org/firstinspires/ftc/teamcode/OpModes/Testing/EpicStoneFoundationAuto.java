package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.heading.LinearInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;
@Autonomous(name="solo auto")

public class EpicStoneFoundationAuto extends LinearOpMode {
    private Robot r;
    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);

        waitForStart();
        //webcam.closeCameraDevice();
        if (isStopRequested()) return;

        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
        r.intake.turbo();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20 * 25.4, RobotValues.yPos1 * 25.4, 0)).build());
        r.intake.intake(0);
        r.lifty.grabOpen();
        r.lifty.intoGround();
        sleep(1000);
        r.lifty.grabFull();
       // r.lifty.autoHold();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(6  *25.4).build());
        r.turnSync(Math.toRadians(-90));
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(10*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back((65-RobotValues.yPos1)*25.4).build());
        //foundation time
        r.rrBot.setPoseEstimate(new Pose2d(0,0,0));
        r.lifty.grabOpen();
        sleep(300);
        r.lifty.goDown();
        r.movey.dropItLikeItsHot();

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(RobotValues.x, RobotValues.ycrax,Math.toRadians(90))).build());
        r.lifty.goDown();
        sleep(300);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(12*25.4).build());
        r.movey.grabFoundation();
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(20 * 25.4).build());
        r.turnSync(Math.toRadians(-98));
        r.movey.dropItLikeItsHot();
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(21 * 25.4).build());
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(10 * 25.4).build());
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(45 * 25.4).build());


    }
}
