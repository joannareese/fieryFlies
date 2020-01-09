package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

@Autonomous(name = "test stop")
public class testStop extends LinearOpMode {
    private Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);
        waitForStart();

        r.rrBot.followTrajectorySync(r.rrBot.trajectoryBuilder().forward(10 * 25.4).build());
    }
}
