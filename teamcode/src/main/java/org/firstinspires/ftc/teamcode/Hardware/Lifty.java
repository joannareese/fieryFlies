package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

public class Lifty {
    Robot mrRoboto;
    boolean lockout = false;
    private int target;


    public Lifty(Robot mrRobotot) {
        mrRoboto = mrRobotot;
    }
    public void placeAtLevel(){
    if(mrRoboto.stackTarget<=4){
        target = 0;
        mrRoboto.chainbar.goUpAll();

    }
    else {
        mrRoboto.deployChainbarin500 = true;
        target=(mrRoboto.stackTarget-4)*RobotValues.fourincheslift;

    }
    }
//    public void goUp4Inches() {
//
//        target = (mrRoboto.Motor7.getCurrentPosition() + RobotValues.fourincheslift) <= RobotValues.liftyUp ? (mrRoboto.Motor7.getCurrentPosition() + RobotValues.fourincheslift) : RobotValues.liftyUp;
//
//    }
    public void goDOwn4Inches() {

        target = (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) <= RobotValues.liftyDown ? (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) : RobotValues.liftyDown;

    }

    public void goUpAll() {


        target = RobotValues.liftyUp;
    }

    public void intoGround() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor7.setTargetPosition((int) RobotValues.groud);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(1);
    }


    public void goDown() {

        target = RobotValues.liftyDown;

    }

//

    public void moveUpWithStick(double value) {

        target=(int) (mrRoboto.Motor7.getTargetPosition() + (value *300));

    }

    public void autoHold() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition(RobotValues.autoHold);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.75);
    }

    public void trim(float left_stick_y) {
        RobotValues.liftyDown += left_stick_y;
        RobotValues.liftyUp += left_stick_y;
    }
    public void update(){
        if(target>2300){
            target=2300;
        }
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setTargetPosition(target);
        mrRoboto.Motor7.setPower(.75);
    }
}

