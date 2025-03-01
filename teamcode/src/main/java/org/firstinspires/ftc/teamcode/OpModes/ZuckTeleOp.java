package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.GamePadHandler;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "(TELE)DON't DISCONNECT, I BELIEVE IN YOU ")
public class ZuckTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(telemetry, new Location(),hardwareMap);
        GamePadHandler gamePadHandler = new GamePadHandler(this);
        waitForStart();
        while(opModeIsActive()){
            gamePadHandler.handleInput(robot,gamepad1,gamepad2);
            robot.update();
        }
    }
}
