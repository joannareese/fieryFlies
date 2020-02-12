package org.firstinspires.ftc.teamcode.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathFunctions
{

    public static double angleWrap(double angle)
    {

        while(angle>=2*Math.PI)
        {angle-=2*Math.PI;}

        while(angle<0)
        {angle+=2*Math.PI;}

        return angle;

    }
    public static double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

}