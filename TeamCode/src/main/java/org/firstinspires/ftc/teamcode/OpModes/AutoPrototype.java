package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryLoader;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
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
        while(!gamepad1.a && RobotValues.WTFAREWEEVENDOING!=-1) {

            //tang
            r.drivePower(gamepad1.left_stick_y/2.0f,gamepad1.right_stick_y/2.0f);

            if (isStopRequested()) return;

            //press b to go 1 m

            if (gamepad1.b||RobotValues.WTFAREWEEVENDOING==1){
            r.followTrajectorySync(trajectory);}

            // press x to turn 90

            if (gamepad1.x||RobotValues.WTFAREWEEVENDOING==3){
                r.turnSync(90);}
        }

    }
}
//jojo
//HI JOJO :)