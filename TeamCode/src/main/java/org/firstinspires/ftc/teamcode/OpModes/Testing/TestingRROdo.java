package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;


@TeleOp(name = "RR OdoTest")

public class TestingRROdo extends LinearOpMode {
    public Robot r;

    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(0, 0, 0, 0), hardwareMap);
        waitForStart();
        while (!isStopRequested()) {
            r.rrBot.update();
        }


    }
}
