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
                //.forward(28*25.4)
                .splineTo(new Pose2d(0,23.0*25.4,0))

                .build();
        Trajectory trajectory2 = r.rrBot.trajectoryBuilder()
                //.forward(28*25.4)
                .splineTo(new Pose2d(0,23.0*25.4,0))

                .build();
        waitForStart();

        if (isStopRequested()) return;

        //press b to go 1 m

        r.followTrajectorySync(trajectory);
        r.left.setPosition(0.5);

        r.followTrajectorySync(trajectory2);
    }
}