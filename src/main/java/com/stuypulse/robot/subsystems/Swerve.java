package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.robot.constants.Modules;
import com.stuypulse.robot.subsystems.swerve.Module;
import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    private final AHRS gyro;

    private final Module[] modules;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;

    public Swerve() {
        gyro = new AHRS(Port.kMXP);

        modules = Modules.MODULES;
        
        kinematics = new SwerveDriveKinematics(
            Arrays.stream(modules)
                .map(x -> x.getLocation())
                .toArray(Translation2d[]::new)
        );

        odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d());
    }

    /** MODULE API **/

    public void setStates(Vector2D velocity, double omega) {
        setStates(new ChassisSpeeds(velocity.x, velocity.y, omega));
    }

    public void setStates(ChassisSpeeds robotSpeed) {
        setStates(kinematics.toSwerveModuleStates(robotSpeed));
    }

    public void setStates(SwerveModuleState... states) {
        // check if modules and states aren't same length?


        SwerveDriveKinematics.desaturateWheelSpeeds(states, Units.feetToMeters(17.0));
        
        for (int i = 0; i < states.length; ++i) {
            modules[i].setState(states[i]);
        }
    }

    /** GYRO API */
    
    public Rotation2d getAngle() {
        return gyro.getRotation2d();
    }

    /** ODOMETRY API */

    private void updateOdometry() {
        odometry.update(
            getAngle(), 
    
            Arrays.stream(modules)
                .map(x -> x.getState())
                .toArray(SwerveModuleState[]::new)
        );
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    @Override
    public void periodic() {
        updateOdometry();
    }

}
