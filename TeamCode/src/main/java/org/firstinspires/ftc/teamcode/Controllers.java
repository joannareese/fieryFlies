package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Controllers {

    /**
     * The instance fields: <code>gamepadMode1</code> and <code>gamepadMode2</code> determine which controller is using the alternative control scheme.
     */
    public int gamepadMode1 = 0, gamepadMode2 = 0;

    /**
     *  <code>isStartXPressable</code> is used for the toggle of start-x, it is used to make switching between the two
     *  modes easier to accomplish.
     */
    private boolean isStartXPressable = true;

    /*
     *  Left Stick Controls
     *  X, Y & Buttons
     */
    public double leftstick1_y;
    public double leftstick2_y;


    public double leftstick1_x;
    public double leftstick2_x;

    public boolean leftstick1_press;
    public boolean leftstick2_press;


    /*
     *  Right Stick Controls
     *  X, Y & Buttons
     */
    public double right_stick1_y;
    public double right_stick2_y;

    public double right_stick1_x;
    public double right_stick2_x;

    public boolean right_stick1_press;
    public boolean right_stick2_press;


    /*
     * Bumpers
     */
    public boolean left_bumper1;
    public boolean right_bumper1;

    public boolean left_bumper2;
    public boolean right_bumper2;


    /*
     * Triggers
     */
    public boolean left_trigger1;
    public boolean right_trigger1;

    public boolean left_trigger2;
    public boolean right_trigger2;


    /*
     * Face Buttons
     * X, Y, A & B
     */
    public boolean b_button1;
    public boolean a_button1;
    public boolean y_button1;
    public boolean x_button1;

    public boolean b_button2;
    public boolean a_button2;
    public boolean y_button2;
    public boolean x_button2;

    public boolean b_button3;
    public boolean a_button3;
    public boolean y_button3;
    public boolean x_button3;

    /*
     *  Directional Pad Buttons
     */
    public boolean dup1;
    public boolean ddown1;
    public boolean dleft1;
    public boolean dright1;

    public boolean dup2;
    public boolean ddown2;
    public boolean dleft2;
    public boolean dright2;

    /*
     * Menu Buttons
     * Start, Back & Home
     */
    public boolean home1;
    public boolean back1;
    public boolean start1;

    public boolean home2;
    public boolean back2;
    public boolean start2;

    public void update(Gamepad gamepad1, Gamepad gamepad2) {
        leftstick1_y        = gamepadMode1 == 0 ? gamepad1.left_stick_y : 0; //Basicly an if statement that
        right_stick1_y      = gamepadMode1 == 0 ? gamepad1.right_stick_y : 0;
        leftstick1_x        = gamepadMode1 == 0 ? gamepad1.left_stick_x : 0;
        right_stick1_x       = gamepadMode1 == 0 ? gamepad1.right_stick_x : 0;
        leftstick1_press    = gamepad1.left_stick_button || gamepad1.right_stick_button;

        //Bumpers and Triggers
        left_bumper1        = gamepad1.left_bumper  && gamepadMode1 == 0;
        right_bumper1       = gamepad1.right_bumper && gamepadMode1 == 0;
        left_trigger1       = gamepad1.left_trigger  > 0.3 && gamepadMode1 == 0;//unused
        right_trigger1      = gamepad1.right_trigger > 0.3 && gamepadMode1 == 0;

        //Button inputs
        b_button1           = gamepad1.b && gamepadMode1 == 0;
        a_button1           = gamepad1.a && gamepadMode1 == 0;
        y_button1           = gamepad1.y && gamepadMode1 == 0;
        x_button1           = gamepad1.x && gamepadMode1 == 0;

        //Directional Pad Inputs
        dup1                = gamepad1.dpad_up    && gamepadMode1 == 0;
        ddown1              = gamepad1.dpad_down  && gamepadMode1 == 0;
        dleft1              = gamepad1.dpad_left  && gamepadMode1 == 0;
        dright1             = gamepad1.dpad_right && gamepadMode1 == 0;

        //Auxillary Inputs
        leftstick1_press    = gamepad1.right_stick_button && gamepadMode1 == 0;
        right_stick1_press  = gamepad1.left_stick_button  && gamepadMode1 == 0;
        home1                = gamepad1.guide && gamepadMode1 == 0;
        back1                = gamepad1.back  && gamepadMode1 == 0;
        start1               = gamepad1.start && gamepadMode1 == 0;

        /*
         *   Gamepad 2
         */
        //Joystick Inputs
        leftstick2_y       = gamepadMode2 == 0 ? gamepad2.left_stick_y : 0;
        right_stick2_y      = gamepadMode2 == 0 ? gamepad2.right_stick_y : 0;
        leftstick2_x       = gamepadMode2 == 0 ? gamepad2.left_stick_x : 0;
        right_stick2_x     = gamepadMode2 == 0 ? gamepad2.right_stick_x : 0;

        //Bumpers and Triggers
        left_bumper2         = gamepad2.left_bumper  && gamepadMode2 == 0;
        right_bumper2        = gamepad2.right_bumper && gamepadMode2 == 0;
        left_trigger2        = gamepad2.left_trigger > 0.3  && gamepadMode2 == 0;
        right_trigger2       = gamepad2.right_trigger > 0.3 && gamepadMode2 == 0;

        //Button inputs
        back2                = gamepad2.back && gamepadMode2 == 0;
        b_button2            = gamepad2.b && gamepadMode2 == 0;
        a_button2            = gamepad2.a && gamepadMode2 == 0;
        y_button2            = gamepad2.y && gamepadMode2 == 0;
        x_button2            = gamepad2.x && gamepadMode2 == 0;

        //Directional Pad Inputs
        dup2                 = gamepad2.dpad_up    && gamepadMode2 == 0;
        ddown2               = gamepad2.dpad_down  && gamepadMode2 == 0;
        dleft2               = gamepad2.dpad_left  && gamepadMode2 == 0;
        dright2              = gamepad2.dpad_right && gamepadMode2 == 0;

        //Auxillary Inputs
        leftstick2_press    = gamepad2.right_stick_button && gamepadMode2 == 0;
        right_stick2_press   = gamepad2.left_stick_button  && gamepadMode2 == 0;
        home2                = gamepad2.guide  && gamepadMode2 == 0;
        start2               = gamepad2.start && gamepadMode2 == 0;

                /*//
            Switch the controller mode
            mapped to Start-x
         */
        if (x_button1 && start1 && isStartXPressable) {
            gamepadMode1 = 1;
            gamepadMode2 = 0;
            isStartXPressable = false;
        }else if (x_button2 && start2 && isStartXPressable) {
            gamepadMode1 = 0;
            gamepadMode2 = 1;
            isStartXPressable = false;
        }
        isStartXPressable = !((x_button1 && start1) || (x_button2 && start2));

    }

}
