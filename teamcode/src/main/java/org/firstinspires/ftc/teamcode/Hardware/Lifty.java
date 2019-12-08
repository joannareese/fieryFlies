package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lifty {
    Robot robo;
    boolean lockout = false;
    public Lifty(Robot robot ){
        robo = robot;
    }

    public void goUpBit() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setTargetPosition(RobotValues.liftyMid);
        robo.Motor7.setPower(.45);
    }

    public void goUpAll() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition(RobotValues.liftyUp);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void goupBalance() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition(RobotValues.liftyMid);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void goDown() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition(RobotValues.liftyDown);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }

    public void quit(){
        robo.Motor7.setPower(0);
    }

    public void grabClose(){
        robo.grabby.setPosition(RobotValues.grabClose);
        robo.intake.intake(-0.1);}

    public void grabOpen(){
        robo.grabby.setPosition(RobotValues.grabOpen);
    }

    //locks out encoders incase the encoder has failed
    public void lockOut() {
        if(lockout){
            lockout = false;
            robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(!lockout){
            lockout = true;
            robo.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void moveUpWithStick(double value){
        if(!lockout){
            robo.Motor7.setTargetPosition((int)(robo.Motor7.getCurrentPosition()+value*RobotValues.stickMultiplyier));}
        else{
            robo.Motor7.setPower(value);
        }
    }
}

