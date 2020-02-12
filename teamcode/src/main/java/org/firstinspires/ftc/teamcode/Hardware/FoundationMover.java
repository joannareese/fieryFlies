package org.firstinspires.ftc.teamcode.Hardware;

public class FoundationMover {
    Robot robo;
    public FoundationMover(Robot robot ){
        robo = robot;
    }
    public void grabFoundation(){
        robo.foundation.setPosition(RobotValues.foundationGrab);
    }
    public void dropItLikeItsHot(){
        robo.foundation.setPosition(RobotValues.foundationletgo);
    }
    public void releaseFoundation(){
    }
}
