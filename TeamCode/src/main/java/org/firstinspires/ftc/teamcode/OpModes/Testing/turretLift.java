package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="hi_jonah", group="Outreach")

public class turretLift extends OpMode {

    public DcMotor turret;
    public DcMotor jonah;

    public void init() {
        turret = hardwareMap.dcMotor.get("turret");
        jonah = hardwareMap.dcMotor.get("jonah");
    }
    public void loop() {
        if (gamepad2.left_bumper) {
            turret.setPower(-0.5);
        } else if (gamepad2.left_trigger > 0.5) {
            turret.setPower(0.5);
        } else {
            turret.setPower(0);
        }

        if (gamepad2.right_bumper) {
            jonah.setPower(-0.5);
        } else if (gamepad2.right_trigger > 0.5) {
            jonah.setPower(0.5);
        } else {
            jonah.setPower(0);
        }
    }
}
