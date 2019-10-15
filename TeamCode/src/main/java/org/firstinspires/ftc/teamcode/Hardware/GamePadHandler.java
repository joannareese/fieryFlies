package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadHandler {
    public Gamepad gm1;
    public Gamepad gm2;
    private float driveRot;
    private float driveX;
    private float driveY;

    public GamePadHandler() {

    }
    public void handleInput(Robot bot, Gamepad game1, Gamepad game2) {
        ///-------------GAMEPAD 1 --------////
        //  (_)               | | (_)    | |
        //   _  ___  _   _ ___| |_ _  ___| | __
        //  | |/ _ \| | | / __| __| |/ __| |/ /
        //  | | (_) | |_| \__ \ |_| | (__|   <
        //  | |\___/ \__, |___/\__|_|\___|_|\_\
        // _/ |       __/ |
        //|__/       |___/
        driveX = game1.left_stick_x;
        driveY = game1.left_stick_y;
        driveRot = game1.right_stick_x;
        // Thx for the Code ally :) ///
        double P = Math.hypot(-driveX, -driveY);
        double robotAngle = Math.atan2(-driveY, -driveX);
        double sinRAngle = Math.sin(robotAngle);
        double cosRAngle = Math.cos(robotAngle);

        final double v1 = (P * sinRAngle) + (P * cosRAngle) - driveRot;
        final double v2 = (P * sinRAngle) - (P * cosRAngle) + driveRot;
        final double v3 = (P * sinRAngle) - (P * cosRAngle) - driveRot;
        final double v4 = (P * sinRAngle) + (P * cosRAngle) + driveRot;
        double[] powers = {v1,v2,v3,v4};
        bot.drivePower(powers);
    }
}
