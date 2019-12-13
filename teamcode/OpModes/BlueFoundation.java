package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryLoader;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import java.io.File;

import static org.firstinspires.ftc.teamcode.Hardware.RobotValues.back;

@Autonomous(name = "blue foundation",group="trajPaths")
public class BlueFoundation extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                //.reverse()
                .splineTo(new Pose2d(-28 *25.4,0 , 0))
                //.lineTo(new Vector2d(RobotValues.x-back*25.4,RobotValues.y*25.4))
                .build();
        Trajectory trajectory2 = r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(20*25.4, 15*25.4,90))
                .build();
        Trajectory trajectory3 = r.rrBot.trajectoryBuilder()
                .forward(30)
                .build();
        Trajectory trajectory4 = r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(-5*25.4,12*25/4,Math.toRadians(90)))
                .build();
        waitForStart();

        if (isStopRequested()) return;

        //press b to go 1 m



        r.rrBot.
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(22*25.4).build());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(9*25.4).build());
        sleep(1000);
        r.movey.grabFoundation();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(-15*25.4,-10*25.4,Math.toRadians(270))).build());
        r.movey.dropItLikeItsHot();
        r.movey.dropItLikeItsHot();
        sleep(1000);
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(20*25.4).build());
        // r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(0*25.4,0*25/4,Math.toRadians(90))).build());
    }
}