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

@Autonomous(name = "foundation",group="trajPaths")
public class autoFoundation extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-35 * 25.4, -15 * 25.4, 0))
                .build();
        Trajectory trajectory2 = r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(20*25.4, 15*25.4,90))
                .build();
        Trajectory trajectory3 = r.rrBot.trajectoryBuilder()
                .forward(30)
                .build();
        waitForStart();

        if (isStopRequested()) return;

        //press b to go 1 m

        r.followTrajectorySync(trajectory);
        r.movey.dropItLikeItsHot();
        sleep(1000);
        r.followTrajectorySync(trajectory2);
        r.movey.grabFoundation();
        sleep(1000);
        r.followTrajectorySync(trajectory3);
    }
}