package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadHandler {
    public Gamepad gm1;
    public Gamepad gm2;
    private float driveRot;
    private float driveX;
    private float driveY;
    public boolean single=false;

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
if(!single){
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

        if(game1.a){
            bot.pos.setLocation(0,0,0,0);
        }

        if (game2.b) {
            bot.movey.grabFoundation();
        }
        if (game2.a) {
            bot.movey.dropItLikeItsHot();
        }
        if (game2.b) {
            bot.movey.grabFoundation();
        }

        if (game2.y) {
            bot.lifty.grabOpen();
        } else if (game2.x) {
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
            bot.lifty.grabOpen();

        } else if (game2.dpad_left) {
            bot.lifty.goUpBit();
        } else if (game2.dpad_up) {
            bot.lifty.goUpAll();
        } else if (game2.dpad_right) {
            bot.lifty.holdPosition();

        }
else if (Math.abs(game2.left_stick_y) > .5) {
           // bot.lifty.wild();
            bot.lifty.moveUpWithStick(game2.left_stick_y*0.25);

        }
        if (game2.left_stick_button) {
            bot.lifty.lockOut();
        }
        if(game2.right_stick_button){
            bot.lifty.grabmega();
        }
        if (game1.a&&game1.b&&game1.right_bumper&&game1.right_trigger>.5){
            single= !single;
        }

    }
///THIS IS TOP SECRET IT DOES NOT EXIST DO NOT TELL ANYBODY ABOUT THIS IT IS FOR PROGRAMMERS ONLY SHHHHHHHHHHHHHHHHHHH
    else {
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

    double intakepower = game1.right_trigger - game1.left_trigger;
    bot.intake.intake(intakepower);
    if(game1.right_bumper)
        bot.intake.turbo();


        /*___________________________
        |         |  |
        |~~\|   |~|~~|~/~\|/~\ (~
        |__/ \_/| |  | \_/|   |_)
        ___________________________*/

    if(game1.a){
        bot.pos.setLocation(0,0,0,0);
    }

    if (game1.b) {
        bot.movey.grabFoundation();
    }
    if (game1.a) {
        bot.movey.dropItLikeItsHot();
    }
    if (game1.b) {
        bot.movey.grabFoundation();
    }

    if (game1.y) {
        bot.lifty.grabOpen();
    } else if (game1.x) {
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
        bot.lifty.grabOpen();

    } else if (game2.dpad_left) {
        bot.lifty.goUpBit();
    } else if (game2.dpad_up) {
        bot.lifty.goUpAll();
    } else if (game2.dpad_right) {
        bot.lifty.holdPosition();

    }
    else if (game1.dpad_up) {
        // bot.lifty.wild();
        bot.lifty.moveUpWithStick(0.125);

    }
    else if (game1.dpad_down) {
        // bot.lifty.wild();
        bot.lifty.moveUpWithStick(-0.125);

    }
    if (game2.left_stick_button) {
        bot.lifty.lockOut();
    }
    bot.lifty.trim(game2.right_stick_y);
    if(game1.right_stick_button)
        single = !single;
}
    }


}


