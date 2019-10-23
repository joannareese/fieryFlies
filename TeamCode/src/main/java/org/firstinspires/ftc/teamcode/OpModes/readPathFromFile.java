package org.firstinspires.ftc.teamcode.OpModes;

import android.content.Context;
import android.content.res.AssetManager;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.io.FileUtils;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

import java.io.File;
import java.io.IOException;

@Autonomous(name = "aito",group="trajPaths")
public class readPathFromFile extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry,new Location(), hardwareMap);
        Context context = org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.context;
        AssetManager ass = context.getAssets();
        File test = new File("ahh");
        try {
            FileUtils.copyInputStreamToFile(ass.open("thing"),test);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  r.rrBot.setPoseEstimate();
        Trajectory trajectoryPt2ElectricBoogallo =r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(23*25.4,-18.0*25.4,0)).build();
        Trajectory trajectoryP2ElectricBoogallo =r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(23*25.4,45.0*25.4,1.57)).build();
        Trajectory trajectoryP4ElectricBoogallo =r.rrBot.trajectoryBuilder()
                .splineTo(new Pose2d(23*25.4,22.0*25.4,0)).build();



        waitForStart();

        if (isStopRequested()) return;
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);
        sleep(1000);
        r.followTrajectorySync(trajectoryP2ElectricBoogallo);
        r.followTrajectorySync(trajectoryP4ElectricBoogallo);
        r.followTrajectorySync(trajectoryP2ElectricBoogallo);
    }
}