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
        if(power<0){
        for (DcMotorEx motorEx :motors) {
            motorEx.setPower(power/2);
        }}
        else{
            for (DcMotorEx motorEx :motors) {
                motorEx.setPower(power/RobotValues.powerDivider);
            }
        }
    }
    public void turbo(){
        motors.get(0).setPower(-1);
        motors.get(1).setPower(-1);

    }

    /**
     * zuuuuck sucks in some tasty cubes
     */
    public void zuuuck(){
        intake(.5);

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
