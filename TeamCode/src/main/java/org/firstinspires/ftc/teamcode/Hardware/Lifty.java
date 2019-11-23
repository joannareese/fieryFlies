package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lifty {
    Robot robo;
    public Lifty(Robot robot ){
        robo = robot;
    }

    public void goUpBit() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setTargetPosition(RobotValues.liftyMid);
        robo.Motor7.setPower(1);
    }

    public void goUpAll() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setTargetPosition(RobotValues.liftyUp);
        robo.Motor7.setPower(1);
    }
    public void goupBalance() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setTargetPosition(RobotValues.liftyMid);
        robo.Motor7.setPower(1);
    }
    public void goDown() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setTargetPosition(RobotValues.liftyDown);
        robo.Motor7.setPower(1);
    }

    public void grabClose(){
        robo.grabby.setPosition(RobotValues.grabClose);
    }

    public void grabOpen(){
        robo.grabby.setPosition(RobotValues.grabOpen);
    }

//    public void vexIn(){
//        robo.collectR.setPower(-0.5);
//        robo.collectL.setPower(-0.5);
//    }
//    public void vexOut(){
//        robo.collectR.setPower(0.5);
//        robo.collectL.setPower(0.5);
//    }
}
