package org.firstinspires.ftc.teamcode.Hardware


import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.qualcomm.hardware.motors.NeveRest20Gearmotor
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType

/*
 * Constants shared between multiple drive types.
 */
@Config
object DriveConstants {

    /*
     * TODO: Tune or adjust the following constants to fit your pos. Note that the non-final
     * fields may also be edited through the dashboard (connect to the pos's WiFi network and
     * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
     * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
     */
    private val MOTOR_CONFIG = MotorConfigurationType.getMotorType(NeveRest20Gearmotor::class.java)
    private val TICKS_PER_REV = MOTOR_CONFIG.ticksPerRev

    /*
     * These are physical constants that can be determined from your pos (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience.
     */
    var WHEEL_RADIUS = 50.8
    var GEAR_RATIO = 2.0 // output (wheel) speed / input (motor) speed
    var TRACK_WIDTH = 406.4

    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    var kV = 1.0 / rpmToVelocity(maxRpm)
    var kA = 0.0
    var kStatic = 0.0

    /*
     * These values are used to generate the trajectories for you pos. To ensure proper operation,
     * the constraints should never exceed ~80% of the pos's actual capabilities. While Road
     * Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
     * small and gradually increase them later after everything is working. The velocity and
     * acceleration values are required, and the jerk values are optional (setting a jerk of 0.0
     * forces acceleration-limited profiling).
     */
    var BASE_CONSTRAINTS = DriveConstraints(
            762.0, (762 / 2).toDouble(), 0.0,
            Math.toRadians(180.0), Math.toRadians(180.0), 0.0
    )

    val maxRpm: Double
        get() = MOTOR_CONFIG.maxRPM


    fun encoderTicksToInches(ticks: Int): Double {
        return WHEEL_RADIUS * 2.0 * Math.PI * GEAR_RATIO * ticks.toDouble() / TICKS_PER_REV
    }

    fun rpmToVelocity(rpm: Double): Double {
        return rpm * GEAR_RATIO * 2.0 * Math.PI * WHEEL_RADIUS / 60.0
    }
}