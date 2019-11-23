package org.firstinspires.ftc.teamcode.Hardware;

import android.content.Context;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotValues {
    public static Context context;
    public static int WTFAREWEEVENDOING = 0;
    public static double twentyTicksPerRev = 1120;

    public static double odoDiamMM = 50.8;
    public static double odoTicksPerRev = 720;
    public static double odoTicksPerRevOddOnesOut = 800;
    public static double trackWidthmm = 330;
    public static double middleOdoFromMiddleMM = 163;
    public static double heading;
    public static double y;
    public static double x;

    public static double backHeading = 270;

    public static double servo1bottom = .5;
    public static double servo1top = .3;
    public static double servo2bottom = .1;
    public static double servo2top = .2;

    public static double grabOpen= .1;
    public static double grabClose = .5;

    public static int liftyDown = 0;
    public static int liftyUp = 200;
    public static int liftyMid = 400;



}
