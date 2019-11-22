package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.revextensions2.RevBulkData;

import static android.os.SystemClock.sleep;

@TeleOp(name="Motor Debug")
public class MotorVeloDebug extends OpMode {
    Robot robot;
    private RevBulkData bulk;

    @Override
    public void init() {

        robot = new Robot(telemetry, new Location(), hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            robot.driveMotors.get(0).setPower(.5);
        }
        if(gamepad1.b){
            robot.driveMotors.get(1).setPower(.5);
        }
        if(gamepad1.x){
            robot.driveMotors.get(2).setPower(.5);
        }
        if(gamepad1.y){
            robot.driveMotors.get(3).setPower(.5);
        }
        if(gamepad1.a&&gamepad1.b){
            robot.stopAllMotors();
            sleep(100);
        }
        bulk = robot.expansionHub.getBulkInputData();
        telemetry.addData("frontLeft", bulk.getMotorVelocity(0));
        telemetry.addData("backLeft", bulk.getMotorVelocity(1));
        telemetry.addData("frontRight", bulk.getMotorVelocity(2));
        telemetry.addData("backRight", bulk.getMotorVelocity(3));
        telemetry.update();

    }
}
