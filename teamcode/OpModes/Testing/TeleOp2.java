package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.GamePadHandler;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

@TeleOp(name = "Zuck ZuckTeleOp")
public class TeleOp2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(telemetry, new Location(),hardwareMap);
        GamePadHandler gamePadHandler = new GamePadHandler();
        waitForStart();
        while(opModeIsActive()){
            gamePadHandler.handleInput(robot,gamepad1,gamepad2);
            robot.updatePosition();
        }
    }
}
