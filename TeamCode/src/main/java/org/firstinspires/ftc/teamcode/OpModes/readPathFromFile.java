package org.firstinspires.ftc.teamcode.OpModes;

import android.content.Context;
import android.content.res.AssetManager;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryLoader;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.io.FileUtils;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

import java.io.File;
import java.io.IOException;

@Autonomous(name = "aito", group = "trajPaths")
public class readPathFromFile extends LinearOpMode {
    Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        Context context = org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.context;
        AssetManager ass = context.getAssets();
        File test = new File("ahh");
        try {
            FileUtils.copyInputStreamToFile(ass.open("trajectory/untitled2.yaml"), test);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //  r.rrBot.setPoseEstimate();
        Trajectory trajectoryPt2ElectricBoogallo = TrajectoryLoader.load(test);


        waitForStart();

        if (isStopRequested()) return;
        r.followTrajectorySync(trajectoryPt2ElectricBoogallo);

    }
}