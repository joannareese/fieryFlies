package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.concurrent.TimeUnit;

import kotlin.Unit;

public class GamePadHandler {
    public final OpMode Opmodethi;
    public Gamepad gm1;
    public Gamepad gm2;
    public boolean single = false;
    private float driveRot;
    private float driveX;
    private float driveY;
    private boolean undeployed = true;
    private int slowToggle = 0;
    private float slowToggleDelay = 0;

    public GamePadHandler(OpMode opy) {
        Opmodethi = opy;

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

        double[] powers = {RobotValues.power * v2, RobotValues.power * v4, RobotValues.power * v1, RobotValues.power * v3};
        bot.drivePower(powers);
        if (game1.right_bumper) {
            if (slowToggle % 2 == 0 && slowToggleDelay < bot.time.now(TimeUnit.MILLISECONDS)) {
                RobotValues.power = 0.45;
            } else if (slowToggleDelay < bot.time.now(TimeUnit.MILLISECONDS)) {
                RobotValues.power = 0.9;
            }
            slowToggle++;
            slowToggleDelay = bot.time.now(TimeUnit.MILLISECONDS) + 50;
        }
    /*
                ______                 _
                |  _  \               | |
                | | | |_ __   __ _  __| |
                | | | | '_ \ / _` |/ _` |
                | |/ /| |_) | (_| | (_| |
                |___/ | .__/ \__,_|\__,_|
                      | |
                      |_|
 */

        if (game1
                .dpad_down) {
            bot.lifty.goDOwn4Inches();

        }


        /*___________________________
        |         |  |
        |~~\|   |~|~~|~/~\|/~\ (~
        |__/ \_/| |  | \_/|   |_)
        ___________________________*/


        if (game1.b) {
            bot.movey.dropItLikeItsHot();
        }
        if (game1.a) {
            bot.movey.grabFoundation();
        }
        if (game1.y) {
            bot.rrBot.setPoseEstimate(new Pose2d());
            bot.rrBot.followTrajectorySync(bot.rrBot.slowTrajectoryBuilder().forward(26 * 25.4)
                    .addMarker(() -> {
                        bot.movey.dropItLikeItsHot();
                        return Unit.INSTANCE;
                    }).build());
        }




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


        double intakepwr = (game2.right_trigger) - (game1.left_trigger + game2.left_trigger);
        bot.intake.intake(intakepwr);
//        if(game2.left_bumper){
//            bot.lifty.goDown();
//
//        }
        if (game2.right_bumper) {
            bot.lifty.goUpAll();
        } else if (Math.abs(game2.right_stick_y) > .5) {
            bot.lifty.moveUpWithStick(-game2.right_stick_y * 0.25);
        }




        /*___________________________
        |         |  |
        |~~\|   |~|~~|~/~\|/~\ (~
        |__/ \_/| |  | \_/|   |_)
        ___________________________*/


        if (game2.b) {
            bot.stackTarget--;
        }
        if (game2.a) {
            bot.stackTarget++;
        }

        if (game2.y) {
            bot.chainbar.grabOpen();
        } else if (game2.x) {
            bot.chainbar.grabClose();
        }
        if (game2.right_stick_button) {
            bot.chainbar.capstoneDepoy();
            undeployed = !undeployed;

        }
        if (game2.start) {
            bot.chainbar.umcapstoneDepoy();

        }
        if (game2.left_bumper) {
            bot.lifty.placeAtLevel();
        }

        /*_______________
        |~~\            |
        |   ||~~\/~~|/~~|
        |__/ |__/\__|\__|
             |
         ________________*/

        if (game2.dpad_down) {

            bot.lifty.goDownAll();


        } else if (game2.dpad_left) {
            bot.chainbar.goUpBit();
        } else if (game2.dpad_up) {
            bot.chainbar.goUpAll();
        } else if (game2.dpad_right) {
            bot.chainbar.holdPosition();
        } else if (game2.left_stick_button) {
            bot.chainbar.reset();
            bot.Motor7.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bot.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bot.Motor7.setTargetPosition(0);

        } else if (Math.abs(game2.left_stick_y) > .5) {
            // bot.Chainbar.wild();
            bot.chainbar.moveUpWithStick(-game2.left_stick_y * 0.25);
        }


    }


}


