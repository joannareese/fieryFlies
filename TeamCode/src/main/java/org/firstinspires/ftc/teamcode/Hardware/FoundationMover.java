package org.firstinspires.ftc.teamcode.Hardware;

public class FoundationMover {
    Robot robo;
    public FoundationMover(Robot robot ){
        robo = robot;
    }
    public void grabFoundation(){
        robo.left.setPosition(RobotValues.servo1top);
        robo.right.setPosition(RobotValues.servo2top);
    }
    public void dropItLikeItsHot(){
        robo.left.setPosition(RobotValues.servo1bottom);
        robo.right.setPosition(RobotValues.servo2bottom);
    }
    public void releaseFoundation(){
        dropItLikeItsHot();
    }
}
