package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.heading.SplineInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.AutonomousValues;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import kotlin.Unit;
@Autonomous(name = "nvm Dont KillMe")
public class NewTwoStone extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(telemetry,new Location(),hardwareMap);
        Pose2d startPose = new Pose2d(-33*25.4, -63.0*25.4, -3.14);
        waitForStart();
      // r.chainbar.autoHold();
        r.movey.dropItLikeItsHot();
        r.chainbar.goPlace();
        r.intake.turbo();
        r.chainbar.grabOpen();
        r.rrBot.setPoseEstimate(startPose);
        //go to stone grab
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder() .addMarker(1,() ->{r.intake.intake(.85); return Unit.INSTANCE;})
                .lineTo( new Vector2d(startPose.getX()+RobotValues.offset, startPose.getY()+680),new SplineInterpolator( r.rrBot.getPoseEstimate().getHeading(),startPose.getHeading()+Math.toRadians(-45)))
                .build());

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(10*25.4).build());
        r.chainbar.intoGround();

        sleep(500);
        r.intake.intake(-1.25);
        sleep(1000);
        r.chainbar.goPlace();
        r.intake.intake(1);
        sleep(750);
        r.chainbar.intoGround();


        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().reverse()
                .splineTo(new Pose2d(0,-40.0*25.4,Math.toRadians(-180)))
                .splineTo(new Pose2d(42*25.4,-33*25.4,Math.toRadians(-90)))
                .addMarker(() ->{r.intake.intake(0);r.chainbar.grabClose();r.movey.grabFoundation(); return Unit.INSTANCE;}).build());
        r.chainbar.liftKinda();
        r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(10*25.4)
                .addMarker(2,()->{r.chainbar.grabOpen();r.chainbar.goDown(); r.movey.dropItLikeItsHot();return Unit.INSTANCE;})
                //.addMarker(1,()->{r.movey.dropItLikeItsHot();r.intake.intake(1);return Unit.INSTANCE;})
                .splineTo(new Pose2d( 0*25.4,-42*25.40,Math.toRadians(-180)))
                .addMarker(.5,()->{r.chainbar.goDown();r.chainbar.grabOpen();r.intake.intake(.85);return Unit.INSTANCE;})

                .addMarker(2.5,()->{r.movey.dropItLikeItsHot();r.intake.intake(1);return Unit.INSTANCE;})
                .splineTo(new Pose2d(0,-40*25.4, Math.toRadians(-180)))
//               .splineTo(new Pose2d(AutonomousValues.x*25.4+RobotValues.offset,startPose.getY()+850,Math.toRadians(RobotValues.heading)))
//                .reverse()
//                .addMarker(()->{r.chainbar.goDown(); return Unit.INSTANCE;})
//                .splineTo(new Pose2d(-9*25.4,-37*25.4,Math.toRadians(-180)))
//
//
//                .splineTo(new Pose2d(40*25.4,-37*25.4,Math.toRadians(-180)))
//                .addMarker(10,()->{r.chainbar.goDown();r.chainbar.grabOpen();return Unit.INSTANCE;})
//                .addMarker(11,()->{r.chainbar.grabClose();return Unit.INSTANCE;})
//                .addMarker(12,()->{r.chainbar.goPlace();return Unit.INSTANCE;})
//                .addMarker(13,()->{r.chainbar.grabOpen();return Unit.INSTANCE;})
//
//
//
               .build());


//        builder1 = TrajectoryBuilder(Pose2d(42.0,-31.0,(-90.0).toRadians), constraints)
//
//        builder1     .splineTo(Pose2d(20.0,-37.0,Math.PI))
//        list.add(builder1.build())
//        builder1 = TrajectoryBuilder(Pose2d(20.0,-37.0,Math.PI), constraints)
//        builder1.strafeTo(Vector2d(0.0,-36.0))
//                .splineTo(Pose2d(-19.0,-33.0,(135.0N).toRadians))
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
