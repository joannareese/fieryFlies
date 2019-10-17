package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="liftyTest", group="TESTING")

public class turretLift extends OpMode {

    public DcMotor lifty;
    public DcMotor chainy;

    public void init() {
        lifty = hardwareMap.dcMotor.get("lifty");
        chainy = hardwareMap.dcMotor.get("chainy");
    }
    public void loop() {

        lifty.setPower(-gamepad2.right_stick_y);
        if (gamepad2.dpad_up) {
            chainy.setPower(-0.5);
        } else if (gamepad2.left_trigger > 0.5) {
            chainy.setPower(0.5);
        } else {
            chainy.setPower(0);
        }

    }
}

/**if (gamepad2.dpad_down && nav.canMoveLiftyJr && !nav.limitSwitch.isPressed()) { //
        nav.goDown();
        } else if (gamepad2.dpad_left && nav.canMoveLiftyJr) {
        nav.goUpBit();
        } else if (gamepad2.dpad_up && nav.canMoveLiftyJr) {
        nav.goUpAll();
        } else if (gamepad2.right_stick_button && nav.canMoveLiftyJr) {
        nav.goupBalance();
        } else {
        if (Math.abs(gamepad2.left_stick_y) > .5 && !nav.limitSwitch.isPressed()){
        nav.liftyJr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        nav.liftyJr.setPower(gamepad2.left_stick_y);
        }
        }

        if (gamepad2.dpad_right ) {
        nav.lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nav.ITS_ENDGAME_NOW();
        } else if (!nav.lifty.isBusy()){
        nav.lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        nav.lifty.setPower(-gamepad2.right_stick_y);
        }

        telemetry.addData("ManualMode: ", nav.manualMode);
        telemetry.addData("LiftyJr: ", nav.liftyJr.getCurrentPosition()); **/
