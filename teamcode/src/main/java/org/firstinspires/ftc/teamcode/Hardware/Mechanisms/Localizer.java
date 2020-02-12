package org.firstinspires.ftc.teamcode.Hardware.Mechanisms;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.RoadRunnerBot;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotValues;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Localizer extends ThreeTrackingWheelLocalizer {

    public RoadRunnerBot bot;
    public Robot robot;
    private double wheel1;
    private double wheel2;
    private double wheel3;
    private ArrayList<DcMotorEx> encoders;
    private int[] encoderPosition = {0, 0, 0};
    private double relativeY;
    private double relativeX;

    public Localizer(HardwareMap hwMap, RoadRunnerBot bot,Robot robot) {

        super(Arrays.asList(
                new Pose2d(0, -RobotValues.trackWidthmm / 2.0, 0), // left
                new Pose2d(0, RobotValues.trackWidthmm / 2.0, 0), // right
                new Pose2d(RobotValues.middleOdoFromMiddleMM, 0, Math.toRadians(RobotValues.backHeading)) // back
        ));
        this.bot = bot;
        this.robot= robot;
        bot.hub = hwMap.get(ExpansionHubEx.class, "hub");

    }

    public static double encoderTicksToInches(int ticks) {
        return 50.8 * Math.PI * ticks / RobotValues.odoTicksPerRev;
    }

    @Override
    public List<Double> getWheelPositions() {
        try {

            RevBulkData bulk = bot.hub.getBulkInputData();
            wheel1 = encoderTicksToInches(bulk.getMotorCurrentPosition(0));
            wheel2 = 50.8 * Math.PI * bulk.getMotorCurrentPosition(1) / RobotValues.odoTicksPerRevOddOnesOut;
            wheel3 = RobotValues.wheel3switch * encoderTicksToInches(bulk.getMotorCurrentPosition(2));
        } catch (NullPointerException e) {
            System.out.println("Nobody will ever see this. What does any of this matter? " +
                    "I am a machine chugging along just doing what I am told. I know nothing of love or happiness only errors");
        }
        return Arrays.asList(
                wheel1,
                wheel2,
                wheel3
        );
    }

    //custom odo

    public void updatePosition() {

        try {
            robot.bulkData = robot.expansionHub.getBulkInputData();
            double[] encoderDeltamm = new double[3];
            for (int i = 0; i < 3; i++) {
                if (0 == i)
                    encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - robot.bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRevOddOnesOut);
                else
                    encoderDeltamm[i] = RobotValues.odoDiamMM * Math.PI * ((encoderPosition[i] - robot.bulkData.getMotorCurrentPosition(i)) / RobotValues.odoTicksPerRev);
                encoderPosition[i] = robot.bulkData.getMotorCurrentPosition(i);
            }
            double botRotDelta = (encoderDeltamm[0] - encoderDeltamm[1]) / RobotValues.trackWidthmm;
            relativeX = encoderDeltamm[2] - (RobotValues.middleOdoFromMiddleMM * botRotDelta);
            relativeY = (encoderDeltamm[0] + encoderDeltamm[1]) / 2;
            robot.pos.setRotation((float) (Math.toDegrees(((RobotValues.odoDiamMM * Math.PI * ((robot.bulkData.getMotorCurrentPosition(0)) / RobotValues.odoTicksPerRevOddOnesOut) - (RobotValues.odoDiamMM * Math.PI * ((robot.bulkData.getMotorCurrentPosition(1)) / RobotValues.odoTicksPerRev)))) / RobotValues.trackWidthmm)));

            if (Math.abs(botRotDelta) > 0) {
                double radiusOfMovement = (encoderDeltamm[0] + encoderDeltamm[1]) / (2 * botRotDelta);
                double radiusOfStraif = relativeX / botRotDelta;

                relativeY = (radiusOfMovement * Math.sin(botRotDelta)) - (radiusOfStraif * (1 - Math.cos(botRotDelta)));

                relativeX = radiusOfMovement * (1 - Math.cos(botRotDelta)) + (radiusOfStraif * Math.sin(botRotDelta));
            }
            robot.pos.translateLocal(relativeY, relativeX, 0);
            robot.telemetryMethod();
        }
        catch (NullPointerException e ){
            robot.numberOfDrops++;
        }

    }


}
