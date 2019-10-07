package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Mechanum;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.firstinspires.ftc.teamcode.Hardware.Robot.*;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Hardware.Robot.round;


@TeleOp(name = "testing")
public class testing extends OpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Mechanum robot;
    public Telemetry dashboardTelemetry;
    public TelemetryPacket packet1;



    @Override
    public void init() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        Location loc = new Location();
        robot = new Mechanum(loc, telemetry, hardwareMap);


    }

    @Override
    public void loop() {
       // robot.updatePosition();
        robot.updatePosition2();
       //robot.rrBot.update();
        dashboardTelemetry.addData("Odometry    x: ", round((double) robot.pos.getLocation(0)) +"      y: " + round((double)robot.pos.getLocation(2)) + "  rot:   " + round((double)robot.pos.getLocation(3)));
        dashboardTelemetry.addData(" rr odo ", robot.rrBot.getLocalizer().getPoseEstimate().toString());
        dashboardTelemetry.addData(" absolute encder pos", round((double)(robot.bulkData.getMotorCurrentPosition(0)/RobotValues.odoTicksPerRevOddOnesOut))+" "+round((double)(robot.bulkData.getMotorCurrentPosition(1)/720f))+" "+round((double) (robot.bulkData.getMotorCurrentPosition(2)/720f)));
        dashboardTelemetry.update();
        if(gamepad1.a || RobotValues.WTFAREWEEVENDOING==2){
            robot.driveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.pos = new Location();
            robot.driveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}


