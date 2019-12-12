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
        robo.Motor7.setTargetPosition((int) RobotValues.liftyMid);
        robo.Motor7.setPower(.45);
    }

    public void goUpAll() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition((int) RobotValues.liftyUp);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void intoGround() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.grabClose();
        robo.Motor7.setTargetPosition(RobotValues.groud);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void goupBalance() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition((int) RobotValues.liftyMid);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void goDown() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition((int) RobotValues.liftyDown);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void goPlace() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition((int) RobotValues.liftyPlace);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }
    public void holdPosition() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robo.Motor7.setTargetPosition(RobotValues.liftyhold);
        robo.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robo.Motor7.setPower(.45);
    }public void wild() {
        robo.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
            robo.Motor7.setPower(.45);
            robo.Motor7.setTargetPosition((int)(robo.Motor7.getTargetPosition()+(value*100)));}
        else{
            robo.Motor7.setPower(value);
        }
    }

    public void trim(float left_stick_y) {
        RobotValues.liftyDown+=left_stick_y;
        RobotValues.liftyhold+=left_stick_y;
        RobotValues.liftyMid+=left_stick_y;
        RobotValues.liftyUp+=left_stick_y;
    }
}

