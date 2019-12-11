package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;

import static org.firstinspires.ftc.teamcode.Hardware.RobotValues.back;
@Autonomous(name="simple park")
public class parkOnly extends LinearOpMode {
    Robot r;
    private DcMotorEx Motor1;
    private DcMotorEx Motor2;
    private DcMotorEx Motor3;
    private DcMotorEx Motor4;

    @Override
    public void runOpMode() throws InterruptedException {
        Motor1 = (DcMotorEx) hardwareMap.dcMotor.get("frontLeft");
        Motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor2 = (DcMotorEx) hardwareMap.dcMotor.get("backLeft");
        Motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor3 = (DcMotorEx) hardwareMap.dcMotor.get("frontRight");
        Motor4 = (DcMotorEx) hardwareMap.dcMotor.get("backRight");

        waitForStart();
        Motor1.setPower(.3);
        Motor2.setPower(.3);
        Motor3.setPower(.3);
        Motor4.setPower(.3);
        sleep(1000);

        Motor1.setPower(0);
        Motor2.setPower(0);
        Motor3.setPower(0);
        Motor4.setPower(0);
    }
}
