package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.firstinspires.ftc.teamcode.Movement.Location;
@Autonomous(name = "foundation")

public class Foundation extends LinearOpMode {
    public boolean isRed = true;
    public int sidemult = 1;
    public boolean byWall;
    private Robot r;
    @Override
    public void runOpMode() throws InterruptedException {
        r = new Robot(telemetry, new Location(), hardwareMap);

        while (!isStarted() && !isStopRequested() && (!gamepad1.x)) {
            telemetry.addData("press a to togeel side", "press b to togell where to parl and x to save and move on");
            telemetry.addData("Side:", isRed ? "red" : "blue");
            telemetry.addData("where to park", byWall ? "by wall" : "far");
            if (gamepad1.a) {
                isRed = !isRed;
            }
            if (gamepad1.b) {
                byWall = !byWall;
            }
            sidemult = isRed ? 1 : -1;
            telemetry.update();
        }
        waitForStart();
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(24 * 25.4).build());
        if (isRed){
            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeLeft(6*25.4).build());
        }
        else{
            r.rrBot.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(6*25.4).build());
        }
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(11 * 25.4).build());
        r.movey.grabFoundation();
        sleep(500);
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(25 * 25.4).build());

        r.turnSync(Math.toRadians(-98 * sidemult));
        r.movey.dropItLikeItsHot();
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().back(21 * 25.4).build());
        if (isRed) {
            if (byWall) {
                r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeLeft(RobotValues.foundationStrafe * 25.4).strafeRight(2 * 25.4).build());

            } else {
                r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(14 * 25.4).build());
            }
        } else {
            if (byWall) {
                r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeRight(RobotValues.foundationStrafe * 25.4).strafeLeft(2 * 25.4).build());

            } else {
                r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().strafeLeft(10 * 25.4).build());
            }
        }
        r.followTrajectorySync(r.rrBot.fastTrajectoryBuilder().forward(48* 25.4).build());

    }
}
