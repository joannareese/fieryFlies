package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

public class EpicStoneFoundationAuto extends LinearOpMode {
    private Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        r.rrBot.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(-90)));
        waitForStart();
        //webcam.closeCameraDevice();
        if (isStopRequested()) return;
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(20, RobotValues.yPos1, Math.toRadians(RobotValues.heading))).build());
//
//        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
//        r.intake.turbo();
//        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20 * 25.4, RobotValues.yPos1 * 25.4, 0)).build());
//        r.intake.intake(0);
//        r.lifty.grabOpen();
//        r.lifty.intoGround();
//        sleep(1000);
//        if(RobotValues.yPos1!=8)
//            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(6  *25.4).build());
//        r.turnSync(Math.toRadians(-85));
//        if(RobotValues.yPos1!=8)
//            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(6*25.4).build());
//        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back((65-RobotValues.yPos1)*25.4).build());
//        r.lifty.grabClose();
//        r.lifty.goDown();
//        sleep(1000);
//        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(15*25.4).build());
//    }
    }
}
