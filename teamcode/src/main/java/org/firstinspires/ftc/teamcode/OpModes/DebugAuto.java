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

        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
        r.intake.turbo();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20 * 25.4, RobotValues.yPos1 * 25.4, 0)).build());
        r.intake.intake(0);
        r.lifty.intoGround();
        sleep(1000);
        r.turnSync(Math.toRadians(-90));
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20*25.4,-72*25.4,Math.toRadians(90)),new ConstantInterpolator(Math.toRadians(90))).build());
    }
}