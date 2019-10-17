package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="hi_Thomas", group="Outreach")

public class servos extends OpMode {

    public Servo right;
    public Servo left;

    public void init() {
        right = hardwareMap.servo.get("right");
        left = hardwareMap.servo.get("left");
    }
    public void loop() {
        if(gamepad2.a){
            right.setPosition(0.5);
            left.setPosition(0.5);
        }if(gamepad2.b){
            right.setPosition(0.1);
            left.setPosition(0.1);
        }if(gamepad2.x){
            right.setPosition(0.9);
            left.setPosition(0.9);
        }
    }
}




