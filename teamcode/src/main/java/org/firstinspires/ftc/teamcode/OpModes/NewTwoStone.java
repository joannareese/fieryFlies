package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import kotlin.Unit;
@Autonomous(name = "kill me")
public class NewTwoStone extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(telemetry,new Location(),hardwareMap);
        Pose2d startPose = new Pose2d(-33*25.4, -63.0*25.4, -3.14);
        waitForStart();
      // r.chainbar.autoHold();
        r.movey.dropItLikeItsHot();
        r.rrBot.setPoseEstimate(startPose);
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder() .addMarker(1,() ->{r.intake.intake(1); return Unit.INSTANCE;})   .lineTo( new Vector2d(startPose.getX()-110+RobotValues.offset, startPose.getY()+730),new SplineInterpolator( r.rrBot.getPoseEstimate().getHeading(),startPose.getHeading()+-Math.PI/4))  .build());
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse().splineTo(new Pose2d(0,-45.0*25.4,Math.toRadians(-180))).splineTo(new Pose2d(42*25.4,-33*25.4,Math.toRadians(-90)))
                .addMarker(() ->{r.intake.intake(0);r.movey.grabFoundation(); return Unit.INSTANCE;}).build());
        r.chainbar.goDown();
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().addMarker(()->{r.chainbar.grabClose();return Unit.INSTANCE;}).addMarker(.5,()->{r.chainbar.holdPosition();return Unit.INSTANCE;}).forward(10*25.4)
                .splineTo(new Pose2d(RobotValues.x *25.4,RobotValues.y*25.40,Math.toRadians(-180)))
                .addMarker(()->{r.chainbar.grabOpen();return Unit.INSTANCE;})
                .addMarker(RobotValues.time,()->{r.chainbar.grabOpen();r.movey.dropItLikeItsHot();return Unit.INSTANCE;}).build());
        r.movey.dropItLikeItsHot();

//        builder1 = TrajectoryBuilder(Pose2d(42.0,-31.0,(-90.0).toRadians), constraints)
//
//        builder1     .splineTo(Pose2d(20.0,-37.0,Math.PI))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(Pose2d(20.0,-37.0,Math.PI), constraints)
//        builder1.strafeTo(Vector2d(0.0,-36.0))
//                .splineTo(Pose2d(-19.0,-33.0,(135.0).toRadians))
//        list.add(builder1.build())
//
//        builder1 = TrajectoryBuilder(Pose2d(-19.0,-33.0,(135.0).toRadians), constraints)
//        builder1.reverse().splineTo(Pose2d(20.0,-37.0,Math.PI))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(Pose2d(20.0,-37.0,(180.0).toRadians), constraints)
//        builder1.splineTo(Pose2d(-26.0,-33.0,(135.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(-26.0,-33.0,(135.0).toRadians),
//                .......
//                constraints)
//        builder1.reverse().splineTo(Pose2d(20.0,-37.0,(180.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(20.0,-37.0,(180.0).toRadians), constraints)
//        builder1.splineTo(Pose2d(-31.0,-30.0,(135.0).toRadians))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(
//                Pose2d(-31.0,-30.0,(135.0).toRadians), constraints)
//        builder1.reverse().splineTo( Pose2d(20.0,-37.0,(180.0).toRadians)).reverse().back(25.0)
//        list.add(builder1.build())
    }
}
