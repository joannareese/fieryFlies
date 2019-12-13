package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;


@Autonomous(name = "blue foundation wall",group="trajPaths")
public class

BlueFoundationWall extends LinearOpMode {
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
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(18*25.4).build());

        r.turnSync(Math.toRadians(98));
        r.movey.dropItLikeItsHot();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(21*25.4).build());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(RobotValues.foundationStrafe * 25.4).strafeLeft(2*25.4).build());
        //r.turnSync(Math.toRadians(-90)-r.rrBot.getPoseEstimate().getHeading());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(45*25.4).build());

    }
}