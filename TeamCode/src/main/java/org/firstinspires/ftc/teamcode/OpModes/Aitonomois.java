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
                //.forward(28*25.4)
          .splineTo(new Pose2d(23.0*25.4,-18.0*25.4,0))
//          .splineTo(new Pose2d(25.4*48.0,25.4*11.0,-270))
          .build();


        if (isStopRequested()) return;

        //r.followTrajectorySync(trajectory);
        Trajectory trajectoryPt2ElectricBoogallo =r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(0,10*25.4,90)).build();

        waitForStart();
       // sleep(5000);


        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);

    }
}