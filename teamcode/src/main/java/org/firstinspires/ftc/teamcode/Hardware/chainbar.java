package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

public class chainbar {
    Robot mrRoboto;
    boolean lockout = false;
    public chainbar(Robot mrRobotot ){
        mrRoboto = mrRobotot;
    }

    public void goUpBit() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setTargetPosition((int) RobotValues.chainMid);
        mrRoboto.Motor8.setPower(.45);
    }

    public void goUpAll() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.chainUp);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void intoGround() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.grabClose();
        mrRoboto.Motor8.setTargetPosition((int)RobotValues.groud);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void goupBalance() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.chainMid);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void liftKinda() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.chainkinda);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void goDown() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.liftyDown);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void goPlace() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.chainPlace);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }
    public void holdPosition() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition((int) RobotValues.liftyhold);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }public void wild() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void quit(){
        mrRoboto.Motor8.setPower(0);
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
            mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(!lockout){
            lockout = true;
            mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void moveUpWithStick(double value){
            mrRoboto.Motor8.setPower(.45);
            mrRoboto.Motor8.setTargetPosition((int)(mrRoboto.Motor8.getTargetPosition()+(value*100)));

    }
    public void autoHold(){
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor8.setTargetPosition(RobotValues.autoHold);
        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setPower(.45);
    }

    public void trim(float left_stick_y) {

    }
}

