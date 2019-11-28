package org.firstinspires.ftc.teamcode.Hardware;

import android.content.Context;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotValues {
    public static Context context;
    public static int WTFAREWEEVENDOING = 0;
    public static double twentyTicksPerRev = 0;
    public static double robtTrackWidth = 320;
    public static double odoDiamMM = 50.8;
    public static double odoTicksPerRev = 800;
    public static double odoTicksPerRevOddOnesOut = 800;
    public static double trackWidthmm = 349.7;
    public static double middleOdoFromMiddleMM = 50;
    public static double heading = 0;
    public static double y = -13;
    public static double x = -20;
    public static double back = 26;

    public static double servo1bottom = .8;
    public static double servo1top = .3;
    public static double servo2bottom = 0;
    public static double servo2top = .22;

    public static double grabOpen = 0;
    public static double grabClose = 1;

    public static int liftyDown = 0;
    public static int liftyUp = 200;
    public static int liftyMid = 400;

    public static double backHeading = 90;
    public static double powerDivider = 1;
    public static double stickMultiplyier =  2;
}
