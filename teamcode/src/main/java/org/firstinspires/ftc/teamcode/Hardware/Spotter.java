package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpModes.Aitonomois;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class Spotter implements OpenCvPipeline {
    private Mat hslThresholdOutput = new Mat();
    private Mat hslThresholdOutputSS = new Mat();
    private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
    private ArrayList<Telemetry.Line> filterLinesOutput = new ArrayList<Telemetry.Line>();
    private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

    public static double yPos1 =    25.0;
    public static double yPos2= 4.25;

    @Override
    public Mat processFrame(Mat source0) {
        double[] hslThresholdHue = {60, 100};
        double[] hslThresholdSaturation = {80, 255};
        double[] hslThresholdLuminance = {25, 200.0};
        hslThreshold(source0, hslThresholdHue, hslThresholdSaturation, hslThresholdLuminance, hslThresholdOutput);
        List<MatOfPoint> contoursYellow= new ArrayList<>();
        Mat hiarchy = new Mat();

        double[] hslThresholdHueSS = {0, 255};
        double[] hslThresholdSaturationSS = {0.0, 255};
        double[] hslThresholdLuminanceSS = {0, 40};
        hslThreshold(source0, hslThresholdHueSS, hslThresholdSaturationSS, hslThresholdLuminanceSS, hslThresholdOutputSS);
        List<MatOfPoint> contoursBlack= new ArrayList<>();

        Imgproc.findContours(hslThresholdOutput, contoursYellow, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(hslThresholdOutputSS, contoursBlack, hiarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(source0, contoursYellow, -1, new Scalar(230, 0, 0), 2);
        Imgproc.drawContours(source0, contoursBlack, -1, new Scalar (250, 0, 250), 1);

        Rect bestrect = Imgproc.boundingRect(hslThresholdOutput);
        Rect bestRect = Imgproc.boundingRect(hslThresholdOutput);

        boolean okay = false;
        boolean okayish = false;

        for(MatOfPoint cont : contoursYellow) {

            Rect rect = Imgproc.boundingRect(cont);

            Imgproc.rectangle(source0, rect.tl(), rect.br(), new Scalar(70, 0, 70), 1); // Draws on yellow rectangles with porple boxes

            if (ratioJudge(rect.height, rect.width, .5)<ratioJudge(bestrect.height, bestrect.width, .5) && rect.height * rect.width > 9000 && rect.height * rect.width < 15000) {
                bestrect = rect;
                okay = true;
            }
        }

        for(MatOfPoint cont : contoursBlack){

            Rect recta = Imgproc.boundingRect(cont);
            Imgproc.rectangle(source0, recta.tl(), recta.br(), new Scalar(255, 255, 0), 1); // Draws on black rectangles with yellow boxes

            if (ratioJudge(recta.height, recta.width, .5)<ratioJudge(bestRect.height, bestRect.width, .5) && recta.height * recta.width > 2000 && recta.height * recta.width < 25000) {
                bestRect = recta;
                okayish = true;
            }
        }

        if(okayish){
            Imgproc.rectangle(source0, bestRect.tl(), bestRect.br(), new Scalar(0, 0, 0), 5);
        }

        Imgproc.drawMarker(source0, bestRect.br(), new Scalar(255, 255, 255));

        if(bestRect.x > 130) {
            Aitonomois.skystoneSpot = 3;
            yPos1 = -18.0;
            yPos2 = -4;
        } else if (bestRect.x >= 40) {
            Aitonomois.skystoneSpot = 2;
            yPos1 = -25;
            yPos2 = -4;
        } else {
            Aitonomois.skystoneSpot = 1;
            yPos1 = -25;
            yPos2 = -2;
        }

        return source0;
    }

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
