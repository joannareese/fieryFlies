package org.firstinspires.ftc.teamcode.Hardware;

import android.annotation.SuppressLint;
import android.content.Context;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotValues {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static int WTFAREWEEVENDOING = 0;
    public static double twentyTicksPerRev = 0;
    public static double robtTrackWidth = 640;
    static double odoDiamMM = 50.8;
    static double odoTicksPerRev = 800;
    public static double odoTicksPerRevOddOnesOut = 800;
    static double trackWidthmm = 349.7;
    static double middleOdoFromMiddleMM = 50;
    public static double heading;
    public static double y;
    public static double x;
    static double servo1bottom = .5;
    static double servo1top = .3;
    static double servo2bottom = .1;
    static double servo2top = .2;
    static double backHeading = 270;
}
