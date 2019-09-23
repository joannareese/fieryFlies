package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Mechanum;
import org.firstinspires.ftc.teamcode.Movement.Location;

@Disabled
@TeleOp(name = "testing")
public class testing extends OpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Mechanum robot;

    @Override
    public void init() {
        Location loc = new Location();
        Mechanum robot = new Mechanum(loc, telemetry, hardwareMap);

    }

    @Override
    public void loop() {
        robot.updatePosition();
        telemetry.addData("", robot.robot.getLocation(0) + "" + robot.robot.getLocation(1) + "" + robot.robot.getLocation(2));
    }
}


