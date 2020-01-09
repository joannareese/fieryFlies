package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

public class Lifty {
    Robot mrRoboto;
    boolean lockout = false;
    private int target;

    public Lifty(Robot mrRobotot) {
        mrRoboto = mrRobotot;
    }

    public void goUp4Inches() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        target = (mrRoboto.Motor7.getCurrentPosition() + RobotValues.fourincheslift) <= RobotValues.liftyUp ? (mrRoboto.Motor7.getCurrentPosition() + RobotValues.fourincheslift) : RobotValues.liftyUp;
        mrRoboto.Motor7.setTargetPosition(target);
        mrRoboto.Motor7.setPower(.75);
    }
    public void goDOwn4Inches() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        target = (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) <= RobotValues.liftyDown ? (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) : RobotValues.liftyDown;
        mrRoboto.Motor7.setTargetPosition(target);
        mrRoboto.Motor7.setPower(.75);
    }

    public void goUpAll() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition(RobotValues.liftyUp);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.75);
    }

    public void intoGround() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mrRoboto.Motor7.setTargetPosition((int) RobotValues.groud);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.75);
    }


    public void goDown() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        mrRoboto.Motor7.setTargetPosition(RobotValues.liftyDown);
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setPower(.75);
    }

    public void wild() {
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void quit() {
        mrRoboto.Motor7.setPower(0);
    }


    //locks out encoders incase the encoder has failed
    public void lockOut() {
        if (lockout) {
            lockout = false;
            mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if (!lockout) {
            lockout = true;
            mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void moveUpWithStick(double value) {
        mrRoboto.Motor7.setPower(1);
        mrRoboto.Motor7.setTargetPosition((int) (mrRoboto.Motor7.getTargetPosition() + (value * 200)));

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
}

