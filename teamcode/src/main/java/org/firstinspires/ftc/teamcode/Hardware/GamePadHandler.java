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
  /*    _______________________________________________________________________
         _______  _______  _______  _______  _______  _______  ______     __
        (  ____ \(  ___  )(       )(  ____ \(  ____ )(  ___  )(  __  \   /  \
        | (    \/| (   ) || () () || (    \/| (    )|| (   ) || (  \  )  \/) )
        | |      | (___) || || || || (__    | (____)|| (___) || |   ) |    | |
        | | ____ |  ___  || |(_)| ||  __)   |  _____)|  ___  || |   | |    | |
        | | \_  )| (   ) || |   | || (      | (      | (   ) || |   ) |    | |
        | (___) || )   ( || )   ( || (____/\| )      | )   ( || (__/  )  __) (_
        (_______)|/     \||/     \|(_______/|/       |/     \|(______/   \____/
         _______________________________________________________________________
         _______________________________________________________________________*/


  /*    __________________________
        ~~|~         | '   |
          |/~\\  /(~~|~|/~~|_/(~
        \_|\_/ \/ _) | |\__| \_)
              _/
         _________________________*/

        double P = Math.hypot(-game1.left_stick_x, -game1.left_stick_y);
        double robotAngle = Math.atan2(-game1.left_stick_y, -game1.left_stick_x);
        double rightX = game1.right_stick_x;
        double sinRAngle = Math.sin(robotAngle);
        double cosRAngle = Math.cos(robotAngle);

        final double v1 = (P * sinRAngle) - (P * cosRAngle) + rightX;
        final double v2 = (P * sinRAngle) + (P * cosRAngle) - rightX;
        final double v3 = (P * sinRAngle) + (P * cosRAngle) + rightX;
        final double v4 = (P * sinRAngle) - (P * cosRAngle) - rightX;

        double[] powers = {.75 * v2, .75 * v4, .75 * v1, .75 * v3};
        bot.drivePower(powers);

        /*
        ___________________________________________________________________________
        ___________________________________________________________________________
         _______  _______  _______  _______  _______  _______  ______     _______
        (  ____ \(  ___  )(       )(  ____ \(  ____ )(  ___  )(  __  \   / ___   )
        | (    \/| (   ) || () () || (    \/| (    )|| (   ) || (  \  )  \/   )  |
        | |      | (___) || || || || (__    | (____)|| (___) || |   ) |      /   )
        | | ____ |  ___  || |(_)| ||  __)   |  _____)|  ___  || |   | |    _/   /
        | | \_  )| (   ) || |   | || (      | (      | (   ) || |   ) |   /   _/
        | (___) || )   ( || )   ( || (____/\| )      | )   ( || (__/  )  (   (__/\
        (_______)|/     \||/     \|(_______/|/       |/     \|(______/   \_______/
        _____________________________________________________________________________
        _____________________________________________________________________________ */

        /*________________________
         |     '
        ~|~|/~\|/~~|/~~|/~/|/~\(~
         | |   |\__|\__|\/_|   _)
                \__|\__|
        __________________________*/

        double intakepower = game2.right_trigger - game2.left_trigger;
        bot.intake.intake(intakepower);
        if(game2.right_bumper)
        bot.intake.turbo();


        /*___________________________
        |         |  |
        |~~\|   |~|~~|~/~\|/~\ (~
        |__/ \_/| |  | \_/|   |_)
        ___________________________*/

//        if (game2.left_bumper) {
//            bot.lifty.vexIn();
//        } else if (game2.right_bumper){
//            bot.lifty.vexOut();
//        }

        if (game2.b) {
            bot.movey.grabFoundation();
        }
        if (game2.a) {
            bot.movey.dropItLikeItsHot();
        }
        if (game2.b) {
            bot.movey.grabFoundation();
        }

        if (game2.x) {
            bot.lifty.grabOpen();
        } else if (game2.y) {
            bot.lifty.grabClose();
        }


        /*_______________
        |~~\            |
        |   ||~~\/~~|/~~|
        |__/ |__/\__|\__|
             |
         ________________*/

        if (game2.dpad_down) { //
            bot.lifty.goDown();

        } else if (game2.dpad_left) {
            bot.lifty.goUpBit();
        } else if (game2.dpad_up) {
            bot.lifty.goUpAll();
        } else if (game2.right_stick_button) {
            bot.lifty.goupBalance();

        } else if (Math.abs(game2.left_stick_y) > .5) {
            bot.lifty.moveUpWithStick(game2.left_stick_y);

        }
        if (game2.left_stick_button) {
            bot.lifty.lockOut();
        }
    }

}


