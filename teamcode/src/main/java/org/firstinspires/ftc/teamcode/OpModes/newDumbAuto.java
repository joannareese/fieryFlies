package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Spotter;
import org.firstinspires.ftc.teamcode.Movement.Location;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class newDumbAuto extends LinearOpMode {
    private Robot r;
    private boolean isRed;
    private String webcamName;
    private int sidemult;
    private OpenCvCamera webcam;
    private Spotter spot;
    private Object skystoneSpot;

    @Override
    public void runOpMode() throws InterruptedException {
        msStuckDetectStop=8000;
        r = new Robot(telemetry, new Location(), hardwareMap);
        while ( !isStopRequested()&&  !gamepad1.x ) {
            telemetry.addData("press a to toggle side", " x to save and move on");
            telemetry.addData("Side:", isRed ? "red" : "blue");
            if (gamepad1.a) {
                isRed = !isRed;
            }
            webcamName= isRed ? "Webcam 1" : "Webcam 2";
            telemetry.update();
        }
        if (isStopRequested()){
            stop();
        }
        if(isRed){
            sidemult=1;
        }
        else{
            sidemult=-1;
        }
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        webcam.openCameraDevice();
        spot = new Spotter();
        webcam.setPipeline(spot);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        while (!isStarted()&&!isStopRequested()) {
            if(isStopRequested()){
                webcam.closeCameraDevice();
                break;
            }
            telemetry.addData("Skystone Spot: ", skystoneSpot);
            telemetry.addData("skystone x", spot.best);
            telemetry.update();
        }
        webcam.closeCameraDevice();
    }
}
