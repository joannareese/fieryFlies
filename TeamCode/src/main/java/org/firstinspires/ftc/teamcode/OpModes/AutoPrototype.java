package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

@Autonomous(name = "Tunning OpMode", group = "trajPaths")
public class AutoPrototype extends LinearOpMode {
    Robot r;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                .forward(300)
                .back(300)
                .build();
        Trajectory trajectory2 = r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(300, 300, Math.PI / 2))
                .reverse()
                .splineTo(new Pose2d(0, 0, 0))
                .build();

        Trajectory trajectory3 = r.rrBot.trajectoryBuilder()
                .build();


        waitForStart();


        if (isStopRequested()) return;

        //press b to go 1 m


        r.followTrajectorySync(trajectory);
        wait(2000);
        r.followTrajectorySync(trajectory2);
        wait(2000);
        r.turnSync(Math.toRadians(270));
    }
}
