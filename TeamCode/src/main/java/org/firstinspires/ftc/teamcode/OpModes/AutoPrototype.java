package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

@Autonomous(name = "Tunning OpMode", group = "trajPaths")
public class AutoPrototype extends LinearOpMode {
    Robot r;


    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Trajectory trajectory = r.rrBot.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(RobotValues.x * 25.4, RobotValues.y * 25.4, 0))
                //.lineTo(new Vector2d(RobotValues.x-back*25.4,RobotValues.y*25.4))
                .build();
        waitForStart();


        if (isStopRequested()) return;

        //press b to go 1 m


        r.followTrajectorySync(trajectory);
        sleep(2000);
//        r.followTrajectorySync(trajectory2);
//        sleep(2000);
        sleep(2000);
        r.turnSync(Math.toRadians(90));

    }
}
