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
        double P = Math.hypot(-game1.left_stick_x, -game1.left_stick_y);
        double robotAngle = Math.atan2(-game1.left_stick_y, -game1.left_stick_x);
        double rightX = game1.right_stick_x;
        double sinRAngle = Math.sin(robotAngle);
        double cosRAngle = Math.cos(robotAngle);

        final double v1 = (P * sinRAngle) + (P * cosRAngle) + rightX;
        final double v2 = (P * sinRAngle) - (P * cosRAngle) - rightX;
        final double v3 = (P * sinRAngle) - (P * cosRAngle) + rightX;
        final double v4 = (P * sinRAngle) + (P * cosRAngle) - rightX;

        double[] powers = {.75*v2,.75*v4,.75*v1,.75*v3};
        bot.drivePower(powers);
        //_________ _______ _________ _______  _______  _______  _______  _______
        //\__   __/(  ____ )\__   __/(  ____ \(  ____ \(  ____ \(  ____ )(  ____ \
        //   ) (   | (    )|   ) (   | (    \/| (    \/| (    \/| (    )|| (    \/
        //   | |   | (____)|   | |   | |      | |      | (__    | (____)|| (_____
        //   | |   |     __)   | |   | | ____ | | ____ |  __)   |     __)(_____  )
        //   | |   | (\ (      | |   | | \_  )| | \_  )| (      | (\ (         ) |
        //   | |   | ) \ \_____) (___| (___) || (___) || (____/\| ) \ \__/\____) |
        //   )_(   |/   \__/\_______/(_______)(_______)(_______/|/   \__/\_______)
        double intakepower = game1.right_trigger-game1.left_trigger;
        bot.intake.intake(intakepower);
        if(game1.a){
            bot.movey.dropItLikeItsHot();
        }
        if(game1.b){
            bot.movey.grabFoundation();
        }

    }
}
