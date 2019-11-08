package org.firstinspires.ftc.teamcode.Hardware;

public class FoundationMover {
    Robot robo;
    public FoundationMover(Robot robot ){
        robo = robot;
    }
    public void grabFoundation(){
        robo.left.setPosition(.9);
        robo.right.setPosition(.9);
    }
    public void dropItLikeItsHot(){
        robo.left.setPosition(.1);
        robo.right.setPosition(.1);
    }
    public void releaseFoundation(){
        dropItLikeItsHot();
    }
}
