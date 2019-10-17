package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="wheelieBOI", group="TESTING")

public class wheelieBoi extends OpMode {

    public DcMotor wheelieboi;
    public DcMotor wheeliegurl;

    public void init() {
        wheelieboi = hardwareMap.dcMotor.get("wBoi");
        wheeliegurl = hardwareMap.dcMotor.get("wGurl");
    }
    public void loop() {
        if (gamepad2.right_bumper) {
            wheelieboi.setPower(-0.5);
            wheeliegurl.setPower(-0.5);
        } else if (gamepad2.right_trigger > 0.5) {
            wheelieboi.setPower(0.5);
            wheeliegurl.setPower(0.5);
        } else {
            wheelieboi.setPower(0);
            wheeliegurl.setPower(0);
        }

    }
}
