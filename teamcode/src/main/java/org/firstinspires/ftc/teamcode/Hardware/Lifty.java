package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.concurrent.TimeUnit;

public class Lifty {
    Robot mrRoboto;
    boolean lockout = false;
    private int target;
    private long time;
    private boolean waiiiiitForIt;


    public Lifty(Robot mrRobotot) {
        mrRoboto = mrRobotot;
    }

    public void placeAtLevel() {
        if (mrRoboto.stackTarget <= 4) {
            target = 0;
            mrRoboto.chainbar.goUpAll();

        } else {
            mrRoboto.deployChainbarin500 = true;
            target = (mrRoboto.stackTarget - 4) * RobotValues.fourincheslift;

        }
    }

    public void goDownAll() {
        if (target>100) {
           mrRoboto.chainbar.goDown();
           mrRoboto.chainbar.grabOpen();
           waiiiiitForIt=true;
           time=mrRoboto.time.now(TimeUnit.MILLISECONDS)+500;

        } else {

            mrRoboto.chainbar.goDown();

        }
    }

    public void goDOwn4Inches() {

        target = (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) <= RobotValues.liftyDown ? (mrRoboto.Motor7.getCurrentPosition() - RobotValues.fourincheslift) : RobotValues.liftyDown;

    }

    public void goUpAll() {


        target = RobotValues.liftyUp;
    }


    public void goDown() {

        target = RobotValues.liftyDown;

    }

//

    public void moveUpWithStick(double value) {

        target = (int) (mrRoboto.Motor7.getTargetPosition() + (value * 350));

    }


    public void update() {
        if (target > 2400) {
            target = 2400;
        }
    if (waiiiiitForIt){
        if(mrRoboto.time.now(TimeUnit.MILLISECONDS)>time){
            target=0;
            waiiiiitForIt=false;
        }
    }
        mrRoboto.Motor7.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mrRoboto.Motor7.setTargetPosition(target);
        mrRoboto.Motor7.setPower(.75);
    }
}

