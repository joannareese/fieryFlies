package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.OpModes.Testing.OneStone;
//import org.firstinspires.ftc.teamcode.OpModes.SoloAuto;
//import org.firstinspires.ftc.teamcode.OpModes.TwoStone;
import org.firstinspires.ftc.teamcode.OpModes.BlueSolo;
import org.firstinspires.ftc.teamcode.OpModes.KM_And_Let_Me_See_It;
import org.firstinspires.ftc.teamcode.OpModes.NewTwoStone;
import org.firstinspires.ftc.teamcode.OpModes.VisionBlue;
import org.firstinspires.ftc.teamcode.OpModes.VisionRed;
import org.opencv.core.Core;
import org.opencv.core.Mat;
    import org.opencv.core.MatOfPoint;
    import org.opencv.core.Rect;
    import org.opencv.core.Scalar;
    import org.opencv.imgproc.Imgproc;
    import org.openftc.easyopencv.OpenCvPipeline;

    import java.util.ArrayList;
import java.util.List;

public class Spotter extends OpenCvPipeline {
    private Mat hslThresholdOutput = new Mat();
    private Mat hslThresholdOutputSS = new Mat();
    private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
    private ArrayList<Telemetry.Line> filterLinesOutput = new ArrayList<Telemetry.Line>();
    private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

    public  double best = 0;
    public static double yPos2= 4.25;
    private Mat three;
    private Mat two;
    private Mat one;

    @Override
    public Mat processFrame(Mat source0) {

        Mat hiarchy = new Mat();

        double[] hslThresholdHueSS = {0, 255};
        double[] hslThresholdSaturationSS = {0.0, 255};
        double[] hslThresholdLuminanceSS = {0, 50};
        hslThreshold(source0, hslThresholdHueSS, hslThresholdSaturationSS, hslThresholdLuminanceSS, hslThresholdOutputSS);
        List<MatOfPoint> contoursBlack= new ArrayList<>();
        hslThresholdOutput =hslThresholdOutputSS.submat(RobotValues.row1,RobotValues.row2,RobotValues.col1,RobotValues.col2);

//        three =hslThresholdOutput.submat(RobotValues.row1,RobotValues.row2,RobotValues.col1,RobotValues.col2/3);
//        two =hslThresholdOutput.submat(RobotValues.row1,RobotValues.row2,RobotValues.col1/3,RobotValues.col2*2/3);
//        one =hslThresholdOutput.submat(RobotValues.row1,RobotValues.row2,RobotValues.col1*2/3,RobotValues.col2);
        Imgproc.findContours(hslThresholdOutput, contoursBlack, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(source0, contoursBlack, -1, new Scalar (250, 0, 250), 3);

        Rect bestrect = Imgproc.boundingRect(hslThresholdOutput);
        Rect bestRect = Imgproc.boundingRect(hslThresholdOutput);

        boolean okay = false;
        boolean okayish = false;



        for(MatOfPoint cont : contoursBlack){

            Rect recta = Imgproc.boundingRect(cont);
            Imgproc.rectangle(source0, recta.tl(), recta.br(), new Scalar(255, 255, 0), 2); // Draws on black rectangles with yellow boxes

            if (ratioJudge(recta.height, recta.width, .5)<ratioJudge(bestRect.height, bestRect.width, .5) && recta.height * recta.width > 1200&& recta.height * recta.width < 10000) {
                bestRect = recta;
                okayish = true;
            }
        }

        if(okayish){
            Imgproc.rectangle(source0, bestRect.tl(), bestRect.br(), new Scalar(0, 255, 0), 5);
        }

        Imgproc.drawMarker(source0, bestRect.br(), new Scalar(255, 255, 255));
        best = bestRect.x;
        if(bestRect.x <= 90) {
            KM_And_Let_Me_See_It.skystoneSpot = 3;
            NewTwoStone.skystoneSpot = 3;
            BlueSolo.skystoneSpot = 3;
            VisionRed.skystoneSpot=3;
            VisionBlue.skystoneSpot=3;

    } else if (bestRect.x <= 180){
            KM_And_Let_Me_See_It.skystoneSpot = 2;
            NewTwoStone.skystoneSpot = 2;
            BlueSolo.skystoneSpot = 2;
            VisionRed.skystoneSpot=2;
            VisionBlue.skystoneSpot=2;

       } else {
            KM_And_Let_Me_See_It.skystoneSpot = 1;
            NewTwoStone.skystoneSpot = 1;
            BlueSolo.skystoneSpot = 1;
            VisionRed.skystoneSpot=1;
            VisionBlue.skystoneSpot=1;
        }

        return hslThresholdOutput;}

    public Mat hslThresholdOutput() {
        return hslThresholdOutput;
    }

    private void hslThreshold(Mat input, double[] hue, double[] sat, double[] lum, Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HLS);
        Core.inRange(out, new Scalar(hue[0], lum[0], sat[0]),
                new Scalar(hue[1], lum[1], sat[1]), out);
    }

    public double ratioJudge(double height, double width, double ratioWant) {
        double ratio = height/width;
        return Math.abs(ratioWant-ratio);
    }
}
