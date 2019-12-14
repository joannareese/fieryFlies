package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

public class Lifty {
    Robot mrRoboto;
    boolean lockout = false;
    public Lifty(Robot mrRobotot ){
        mrRoboto = mrRobotot;
    }

    public void goUpBit() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyMid);
        mrRoboto.Motor7.setPower(.45);
    }

    public void goUpAll() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyUp);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void intoGround() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.grabClose();
        mrRoboto.Motor7.setTargetPosition((int)RobotValues.groud);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void goupBalance() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyMid);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void liftKinda() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftykinda);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void goDown() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyDown);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void goPlace() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyPlace);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }
    public void holdPosition() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition((int) RobotValues.liftyhold);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }public void wild() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void quit(){
        mrRoboto.Motor7.setPower(0);
    }
    public void grabFull(){
        mrRoboto.grabby.setPosition(RobotValues.fullGrab);
    }
    public void grabmega(){
        mrRoboto.grabby.setPosition(RobotValues.grabmegaOpen);
    }
    public void grabClose(){

        mrRoboto.grabby.setPosition(RobotValues.grabClose);
    mrRoboto.intake.intake(-0.1);}


    public void grabOpen(){
        mrRoboto.grabby.setPosition(RobotValues.grabOpen);
    }

    //locks out encoders incase the encoder has failed
    public void lockOut() {
        if(lockout){
            lockout = false;
            mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(!lockout){
            lockout = true;
            mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void moveUpWithStick(double value){
        if(!lockout){
            mrRoboto.Motor7.setPower(.45);
            mrRoboto.Motor7.setTargetPosition((int)(mrRoboto.Motor7.getTargetPosition()+(value*100)));}
        else{
            mrRoboto.Motor7.setPower(value);
        }
    }
    public void autoHold(){
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition(RobotValues.autoHold);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.45);
    }

    public void trim(float left_stick_y) {
        RobotValues.liftyDown+=left_stick_y;
        RobotValues.liftyhold+=left_stick_y;
        RobotValues.liftyMid+=left_stick_y;
        RobotValues.liftyUp+=left_stick_y;
    }
}

