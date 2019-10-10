package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryLoader;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

import java.io.File;

@Autonomous(name = "TrajTest",group="trajPaths")
public class AutoPrototype extends LinearOpMode {
    Robot r;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                .forward(1000)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        r.followTrajectorySync(trajectory);


    }
}
