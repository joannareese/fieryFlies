package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *TODO  add a stay on for time thing
 */
public class WheelIntake {
    private List<DcMotorEx> motors;
    public WheelIntake(Robot robot){
        robot.Motor5.setDirection(DcMotorEx.Direction.REVERSE);
        motors = Arrays.asList(robot.Motor5, robot.Motor6);
    }

    /**
     * Sets power of motors for intake
     * @param power power to set the intake too
     */
    public void intake(double power){
        for (DcMotorEx motorEx :motors) {
            motorEx.setPower(power/RobotValues.powerDivider);
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
