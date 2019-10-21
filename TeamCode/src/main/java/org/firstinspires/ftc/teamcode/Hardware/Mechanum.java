package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.firstinspires.ftc.teamcode.Movement.WayPoint;
import org.firstinspires.ftc.teamcode.Utils.LineSegment;
import org.firstinspires.ftc.teamcode.Utils.MathFunctions;

import org.opencv.core.Point;

import java.util.ArrayList;

import static java.lang.StrictMath.abs;

public class Mechanum extends Robot {

    /**
     * Constructor 1
     *
     * @param loc
     */
    public Mechanum(Location loc, Telemetry telemetry, HardwareMap hw) {
        super(telemetry, loc, hw);
    }

    public void forwardInches(double distance) throws UnsupportedOperationException {

        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setPower(0);
            motorEx.setTargetPosition(motorEx.getTargetPosition() + (int) (distance / (4 * Math.PI) * RobotValues.twentyTicksPerRev));
        }
        for (DcMotorEx motorEx : driveMotors) {
            motorEx.setPower(.5);
        }
    }

    public void drive(double forward, double sideways, double rotation) {

        //adds all the inputs together to get the number to scale it by
        double scale = abs(rotation) + abs(forward) + abs(sideways);

        //scales the inputs when needed
        if (scale > 1) {
            forward /= scale;
            rotation /= scale;
            sideways /= scale;
        }
        //setting the motor powers to move
        Motor1.setPower(forward - rotation - sideways);
        Motor2.setPower(forward - rotation + sideways);
        Motor3.setPower(forward + rotation + sideways);
        Motor4.setPower(forward + rotation - sideways);
        updatePosition();
    }

    private double distanceAlongPath(Point location,Point p) {

        return Math.sqrt(Math.pow(location.x - p.x, 2) + Math.pow(location.y - p.y, 2));

    }

    private double[] goalPoint(int iRel, ArrayList<WayPoint> p, double lookAheadDistance) {

        try {
            double x1 = p.get(iRel - 1).x - robot.getLocation(0);
            double y1 = p.get(iRel - 1).y - robot.getLocation(1);
            double x2 = p.get(iRel).x - robot.getLocation(0);
            double y2 = p.get(iRel).y - robot.getLocation(1);
            double dx = x2 - x1;
            double dy = y2 - y1;
            double dr = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            double D = (x1 * y2) - (x2 * y1);
            int sgn = 1;
            if (dy < 0) {
                sgn = -1;
            }
            //if the last segment is selected and there is no goal on that segment go to the end of the path
            if (iRel == p.size() - 2 && Math.sqrt(Math.pow(y2, 2) + Math.pow(x2, 2)) <= lookAheadDistance) {
                return goalPoint(iRel, p, lookAheadDistance - 1);
            }
            double radicand = Math.pow(lookAheadDistance, 2) * Math.pow(dr, 2) - Math.pow(D, 2);
            //if no points on the line intersect the circle where the robot is looking move to the next line 
            if (radicand < 0) {

                return goalPoint(iRel - 1, p, lookAheadDistance);
            }

            //finding the 2 intersections
            double sqrt = Math.sqrt(radicand);
            double g1x = (D * dy + sgn * dx * sqrt) / Math.pow(dr, 2);
            double g2x = (D * dy - sgn * dx * sqrt) / Math.pow(dr, 2);
            double g1y = (-D * dx + Math.abs(dy) * sqrt) / Math.pow(dr, 2);
            double g2y = (-D * dx - Math.abs(dy) * sqrt) / Math.pow(dr, 2);
            double xLower = x1;
            double xUpper = x2;
            if (x2 < x1) {
                xLower = x2;
                xUpper = x1;
            }

            //determines which of the points is further along the path then for that intersection if it falls on the line segment that is the goal point otherwise move to the next line segment
            if (distanceAlongPath(new Point(g1x + robot.getLocation(0), g1y + robot.getLocation(1)), new Point(p.get(iRel).x, p.get(iRel).y)) < distanceAlongPath(new Point(g2x + robot.getLocation(0), g2y + robot.getLocation(1)), new Point(p.get(iRel).x, p.get(iRel).y))) {

                if (g1x > xLower && g1x < xUpper) {

                    return new double[]{g1x + robot.getLocation(0), g1y + robot.getLocation(1), iRel};

                }
                return goalPoint(iRel - 1, p, lookAheadDistance);
            } else {


                if (g2x > xLower && g2x < xUpper) {

                    return new double[]{g2x + robot.getLocation(0), g2y + robot.getLocation(1), iRel};

                }

                return goalPoint(iRel - 1, p, lookAheadDistance);
            }

        }
        //if there are no intersections on any line go to the first point on the path or if the last segment is reached move directly to the endpoint
        catch (IndexOutOfBoundsException e) {

            return new double[]{p.get(iRel).x, p.get(iRel).y, iRel};

        }


    }

    public void goToPurePoint( Point goalPoint,  ArrayList<WayPoint> p, int iRel) {

        double distanceToTarget = Math.hypot(goalPoint.x - robot.getLocation(0), goalPoint.y - robot.getLocation(1));
        double absoluteAngleToTarget = Math.atan2(goalPoint.y - robot.getLocation(1), goalPoint.x - robot.getLocation(0));
        double relativeAngleToPoint = absoluteAngleToTarget - (MathFunctions.angleWrap(robot.getLocation(3) - Math.PI / 2));
        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;
        double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint / (Math.abs(relativeYToPoint) + Math.abs(relativeXToPoint));
        double movementTurn = 0;
        relativeAngleToPoint = absoluteAngleToTarget - (MathFunctions.angleWrap(robot.getLocation(3)));


        double turnPowerToGoal = 0;
        if (iRel < p.size() - 2) {
            try {


                double absoluteAngleToSegment = Math.atan2(p.get(iRel).y - robot.getLocation(1), p.get(iRel).x - robot.getLocation(0));
                double relativeAngleToSegment = absoluteAngleToSegment - (MathFunctions.angleWrap(Math.toRadians(robot.getLocation(3))));
                while (relativeAngleToSegment > Math.PI) {
                    relativeAngleToSegment -= 2 * Math.PI;
                }
                while (relativeAngleToSegment < -Math.PI) {
                    relativeAngleToSegment += 2 * Math.PI;
                }
                if (relativeAngleToSegment == 0) {
                    relativeAngleToSegment = .000001;
                }
                turnPowerToGoal = (relativeAngleToSegment) / Math.sqrt(Math.abs(relativeAngleToSegment));


                LineSegment nextSegment = new LineSegment(p.get(iRel).x, p.get(iRel).y, p.get(iRel + 1).x, p.get(iRel + 1).y);
                double distanceToPoint = distanceAlongPath(new Point(robot.getLocation(0), robot.getLocation(1)), new Point(p.get(iRel).x, p.get(iRel).y));
                LineSegment relevantSegment = new LineSegment(p.get(iRel - 1).x, p.get(iRel - 1).y, p.get(iRel).x, p.get(iRel).y);
                double goalPercentage = distanceToPoint / (relevantSegment.getMagnitude()) + p.get(iRel).goalTurnPercentageBias;
                if (goalPercentage > 1) {
                    goalPercentage = 1;
                } else if (goalPercentage < 0) {
                    goalPercentage = 0;
                }
                double turnPowerAbsolute;
                if (iRel == p.size() - 1) {

                    turnPowerAbsolute = turnPowerToGoal;

                } else {
                    double nextAngle = nextSegment.getAngle();
                    while (nextAngle > Math.PI) {
                        nextAngle -= Math.PI * 2;
                    }
                    while (nextAngle < -Math.PI) {
                        nextAngle += Math.PI * 2;
                    }

                    double turnPowerToNextPoint = (nextAngle - robot.getLocation(3)) / Math.sqrt(Math.abs(nextAngle - robot.getLocation(3)));
                    turnPowerAbsolute = turnPowerToGoal * goalPercentage + turnPowerToNextPoint * (1 - goalPercentage);
                }
            } catch (Exception e) {

                movementTurn = turnPowerToGoal;

            }
        } else {

            movementTurn = (relativeAngleToPoint) / Math.sqrt(Math.abs(relativeAngleToPoint));

        }
        drive(movementYPower, movementXPower, movementTurn);

    }

    //follow path with pure pursuit
    public void followPath(ArrayList<WayPoint> p, double lookAheadDistance) {

        p.add(p.get(p.size() - 1));
        double[] d = goalPoint(p.size() - 2, p, lookAheadDistance);
        Point g = new Point(d[0], d[1]);
        goToPurePoint(g, p, (int) d[2]);


    }

}
