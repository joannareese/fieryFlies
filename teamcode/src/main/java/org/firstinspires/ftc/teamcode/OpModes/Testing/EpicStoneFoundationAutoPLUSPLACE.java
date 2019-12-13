package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.heading.LinearInterpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;
@Autonomous(name="solo auto")
public class EpicStoneFoundationAutoPLUSPLACE extends LinearOpMode {
    private Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        r.rrBot.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(-90)));
        waitForStart();
        //webcam.closeCameraDevice();
        if (isStopRequested()) return;
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().splineTo(new Pose2d(20, RobotValues.yPos1, Math.toRadians(RobotValues.heading))).build());

        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(10 * 25.4).build());
        r.intake.turbo();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(-20 * 25.4, RobotValues.yPos1 * 25.4, 0)).build());
        r.intake.intake(0);
        r.lifty.grabOpen();
        r.lifty.intoGround();
        sleep(1000);
        if(RobotValues.yPos1!=8)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(6  *25.4).build());
        r.turnSync(Math.toRadians(-85));
        if(RobotValues.yPos1!=8)
            r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(6*25.4).build());
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().back((65-RobotValues.yPos1)*25.4).build());
        r.lifty.grabClose();
        r.lifty.goDown();
        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().reverse().splineTo(new Pose2d(RobotValues.x, RobotValues.ycrax,0),new LinearInterpolator(r.rrBot.getPoseEstimate().getHeading(),0)).build());
        r.movey.grabFoundation();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(20 * 25.4).build());

        r.turnSync(Math.toRadians(-98));
        r.movey.dropItLikeItsHot();
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().back(21 * 25.4).build());
        r.followTrajectorySync(r.rrBot.trajectoryBuilder().strafeRight(10 * 25.4).build());
        //r.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(45 * 25.4).build());


    }
}
