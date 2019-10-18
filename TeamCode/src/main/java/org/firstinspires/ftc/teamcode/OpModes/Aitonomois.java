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

@Autonomous(name = "aito",group="trajPaths")
public class Aitonomois extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                .strafeLeft(28*25.4)
//          .splineTo(new Pose2d(-48.0*25.4,-11.0*25.4,270))
//          .splineTo(new Pose2d(25.4*48.0,25.4*11.0,-270))
          .build();

        waitForStart();

        if (isStopRequested()) return;

        //press b to go 1 m

        r.followTrajectorySync(trajectory);
//
    }
}