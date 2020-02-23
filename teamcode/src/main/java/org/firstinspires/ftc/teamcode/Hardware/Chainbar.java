package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

public class Chainbar {
    Robot mrRoboto;
    boolean lockout = false;
    private int target;

    public Chainbar(Robot mrRobotot) {
        mrRoboto = mrRobotot;
    }

    public void goUpBit() {

        target = ((int) (RobotValues.chainMid - AutonomousValues.autoChainbarOffset));
    }

    public void goUpAll() {

        target = ((int) (RobotValues.chainUp - AutonomousValues.autoChainbarOffset));

    }

    public void intoGround() {
        target = ((int) (RobotValues.groud - AutonomousValues.autoChainbarOffset));

    }

    public void goUpBalance() {

        target = ((int) (RobotValues.chainMid - AutonomousValues.autoChainbarOffset));

    }

    public void liftKinda() {

        target = ((int) (RobotValues.chainkinda - AutonomousValues.autoChainbarOffset));

    }

    public void goDown() {

        target = ((int) (RobotValues.liftyDown - AutonomousValues.autoChainbarOffset));

    }
    public void goGrab() {

        target = ((int) (RobotValues.liftyDown - AutonomousValues.autoChainbarOffset));

    }


    public void goPlace() {

        target = ((int) (RobotValues.chainPlace - AutonomousValues.autoChainbarOffset));

    }

    public void holdPosition() {


        target = ((int) (RobotValues.chainhold - AutonomousValues.autoChainbarOffset));

    }


    public void reset() {
        mrRoboto.Motor8.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        target=0;
    }

    public void grabClose() {

        mrRoboto.grabby.setPosition(RobotValues.grabClose);
    }


    public void grabOpen() {
        mrRoboto.grabby.setPosition(RobotValues.grabOpen);
    }

    public void capstoneDepoy() {
        mrRoboto.capstone.setPosition(RobotValues.capstoneDeploy);
    }

    public void umcapstoneDepoy() {
        mrRoboto.capstone.setPosition(RobotValues.capstoneRetract);
    }

    public void moveUpWithStick(double value) {

        target = ((int) (mrRoboto.Motor8.getTargetPosition() + (value * 200)));

    }

    public void autoHold() {
        target = (int) (RobotValues.autoHold - AutonomousValues.autoChainbarOffset);
    }

    public void update() {


        mrRoboto.Motor8.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor8.setTargetPosition(target);
        mrRoboto.Motor8.setPower(1);
    }
}

