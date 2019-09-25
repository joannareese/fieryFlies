package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Mechanum;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import static java.lang.Thread.sleep;


@TeleOp(name = "testing")
public class testing extends OpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Mechanum robot;



    @Override
    public void init() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();
        Location loc = new Location();
        robot = new Mechanum(loc, telemetry, hardwareMap);


    }

    @Override
    public void loop() {
        robot.updatePosition();
        telemetry.addData("","stuff");
        telemetry.addData("", robot.robot.getLocation(0) + "   " + robot.robot.getLocation(2) + " " + robot.robot.getLocation(3));
        telemetry.addData("", robot.bulkData.getMotorCurrentPosition(0)+" "+robot.bulkData.getMotorCurrentPosition(1)+" "+robot.bulkData.getMotorCurrentPosition(2));
        telemetry.update();
        if(gamepad1.a || RobotValues.WTFAREWEEVENDOING==2){
            robot.driveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.robot=new Location();
            robot.driveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}


