package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.TrajectoryLoader;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import java.io.File;

import static org.firstinspires.ftc.teamcode.Hardware.RobotValues.back;

@Autonomous(name = "Red foundation",group="trajPaths")
public class

RedFoundation extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        if (isStopRequested()) return;
        waitForStart();
        //press b to go 1 m




        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(22*25.4).build());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(9*25.4).build());
        r.movey.grabFoundation();
        sleep(500);
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(20








                *25.4).build());

        r.turnSync(Math.toRadians(-98));
        r.movey.dropItLikeItsHot();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(9*25.4).build());
        //r.turnSync(Math.toRadians(-90)-r.rrBot.getPoseEstimate().getHeading());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(36*25.4).build());

    }
}