package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.ConstantInterpolator;
import com.acmerobotics.roadrunner.path.heading.LinearInterpolator;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;

//
@Autonomous(name = "DebugAuto", group = "trajPaths")
public class DebugAuto extends LinearOpMode {
    public static int skystoneSpot;
    Robot r;

    //
    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Trajectory trajectoryPt2ElectricBoogallo = r.rrBot.trajectoryBuilder().reverse() //first movement
                .splineTo(new Pose2d(-23 * 25.4, RobotValues.y * 25.4, 0)).build(); //RIGHT --> -18.0, center/Left -25


        telemetry.addData("Skystone Spot: ", Spotter.yPos2);
        telemetry.update();

        waitForStart();

        //webcam.closeCameraDevice();

        if (isStopRequested()) return;
        //go to the First Stone
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);
        r.lifty.goUpAll();
        sleep(1000);
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().lineTo(new Vector2d(-15 * 25.4, -50 * 25.4), new LinearInterpolator(r.rrBot.getPoseEstimate().getHeading(), 0)).build());
        r.lifty.goUpBit();
        sleep(1000);
        //second stone
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().lineTo((new Vector2d(-23 * 25.4, RobotValues.yPos1 * 25.4)),new ConstantInterpolator(0)).build());
        r.lifty.goUpAll();
        sleep(1000);
//        r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(-18 * 25.4, -70 * 25.4, 0)).build());//cross map
//        r.lifty.goDown();
//        sleep(1000);
//        r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeTo(new Vector2d(-18 * 25.4, -42 * 25.4)).build());//cross map

    }
}