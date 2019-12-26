package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;
@Autonomous(name = "Red Auto Solo")
public class testingIntake extends LinearOpMode {
    public Robot r;
    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(),hardwareMap);

        waitForStart();
        if(RobotValues.yPos1==1)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(5*25.4).build());
        if(RobotValues.yPos1==3)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back(8*25.4).build());

        r.turnSync(Math.toRadians(-55));
        r.intake.turbo();
        sleep(250);
        r.intake.intake(1);
        r.lifty.goUpBit();
//        if(RobotValues.yPos1==1)
//        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(7*25.4).build());
//        if(RobotValues.yPos1==3)
//            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(23*25.4).build());
//        else
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward((23-7)*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(10*25.4).build());
        sleep(500);

        r.lifty.goDown();
        r.intake.intake(0);
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(-24*25.4,-24*25.4,0)).reverse().back(36*25.4).build());

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-74*25.4,-30.5*25.4,Math.toRadians(90))).reverse().back(5*25.4).build());

        r.movey.grabFoundation();
        sleep(500);
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(12*25.4).build());
        r.turnSync(Math.toRadians(-98));
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(15*25.4).build());
        r.movey.dropItLikeItsHot();
        sleep(500);
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(38*25.4).build());
    }
}
