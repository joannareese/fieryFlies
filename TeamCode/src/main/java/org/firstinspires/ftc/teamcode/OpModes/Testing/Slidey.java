package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp(name="slidey", group="Outreach")

/**
 * This class is for our outreach robot that uses mecanum wheels.
 * This outreach bot was a prototype drivetrain to our two iterations of our competition robot from last year
 * This class allows the outreach bot to be omnidirectional
 */
public class Slidey extends OpMode {

    public CRServo slidey;


    public void init() {

        slidey = hardwareMap.crservo.get("slidey");
    }

    public void loop() {
        if (gamepad2.left_bumper) { //
            slidey.setPower(-0.5);
        } else if (gamepad2.left_trigger > 0.5) {
            slidey.setPower(0.5);
        } else {
            slidey.setPower(0);
        }
    }

}


