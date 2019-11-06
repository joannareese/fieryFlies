package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;
import java.util.Arrays;
/**
 *TODO  add a stay on for time thing
 */
public class WheelIntake {
    private ArrayList<DcMotorEx> motors;
    public WheelIntake(Robot robot){
        motors = (ArrayList<DcMotorEx>) Arrays.asList(robot.Motor5, robot.Motor6);
    }

    /**
     * Sets power of motors for intake
     * @param power power to set the intake too
     */
    public void intake(double power){
        for (DcMotorEx motorEx :motors) {
            motorEx.setPower(power);
        }
    }

    /**
     * zuuuuck sucks in some tasty cubes
     */
    public void zuuuck(){
        intake(.8);

    }

    /**
     * unzuck reverses the motors
     */
    public void unzuck(){
        intake(-.8);
    }

    /**
     * no zuck stops sucking in tasty cubes
     */
    public void nozuck(){
        intake(0);
    }


}
