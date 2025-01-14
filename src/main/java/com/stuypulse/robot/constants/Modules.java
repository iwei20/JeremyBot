package com.stuypulse.robot.constants;

import com.stuypulse.robot.subsystems.modules.Module;
import com.stuypulse.robot.subsystems.modules.implementations.feedforwardback.FFBTurnControl;
import com.stuypulse.robot.subsystems.modules.implementations.statespace.StateSpaceDriveControl;
import com.stuypulse.robot.subsystems.modules.physical.NEODrive;
import com.stuypulse.robot.subsystems.modules.physical.NEOMagTurn;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

/** 
 * This file organizes the creation of swerve modules.
 * 
 * It contains track size and an appropiate max speed, as well
 * as subinterfaces for each module for module specific information.
 * 
 * Exposes all the modules in an array.
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public interface Modules {
    double WIDTH = Units.feetToMeters(29.0);
    double HEIGHT = Units.feetToMeters(29.0);

    double MAX_SPEED = NEOModule.MAX_SPEED;
    double MAX_ACCEL = 2.5;

    double MAX_ANGULAR_SPEED = MAX_SPEED / Math.hypot(WIDTH/2.0, HEIGHT/2.0);
    double MAX_ANGULAR_ACCEL = 2.5;

    public interface TopRight {
        String ID = "Top Right";
        Translation2d LOCATION = new Translation2d(WIDTH * +0.5, HEIGHT * -0.5);

        int DRIVE_PORT = 1;
        int TURN_PORT = 2;

        int ENCODER_PORT = 3;

        // 45 + 90 * (quadrant - 1)
        Rotation2d OFFSET = Rotation2d.fromDegrees(45.0);

        Module MODULE = new Module(
            ID, LOCATION, 
            new StateSpaceDriveControl(
                new NEODrive(DRIVE_PORT),
                NEOModule.Drive.StateSpace.getControlLoop()
            ), 
            new FFBTurnControl(
                new NEOMagTurn(TURN_PORT, ENCODER_PORT, OFFSET), 
                NEOModule.Turn.Feedback.getController()
            )
        );
    }

    public interface TopLeft {
        String ID = "Top Left";
        Translation2d LOCATION = new Translation2d(WIDTH * +0.5, HEIGHT * +0.5);

        int DRIVE_PORT = 3;
        int TURN_PORT = 4;

        int ENCODER_PORT = 1;

        Rotation2d OFFSET = Rotation2d.fromDegrees(135.0);

        Module MODULE = new Module(
            ID, LOCATION, 
            new StateSpaceDriveControl(
                new NEODrive(DRIVE_PORT),
                NEOModule.Drive.StateSpace.getControlLoop()
            ),  
            new FFBTurnControl(
                new NEOMagTurn(TURN_PORT, ENCODER_PORT, OFFSET), 
                NEOModule.Turn.Feedback.getController()
            )
        );
    }

    public interface BottomLeft {
        String ID = "Bottom Left";
        Translation2d LOCATION = new Translation2d(WIDTH * -0.5, HEIGHT * +0.5);
        
        int DRIVE_PORT = 5;
        int TURN_PORT = 6;

        int ENCODER_PORT = 0;

        Rotation2d OFFSET = Rotation2d.fromDegrees(225.0);

        Module MODULE = new Module(
            ID, LOCATION, 
            new StateSpaceDriveControl(
                new NEODrive(DRIVE_PORT),
                NEOModule.Drive.StateSpace.getControlLoop()
            ), 
            new FFBTurnControl(
                new NEOMagTurn(TURN_PORT, ENCODER_PORT, OFFSET), 
                NEOModule.Turn.Feedback.getController()
            )
        );
    }

    public interface BottomRight {
        String ID = "Bottom Right";
        Translation2d LOCATION = new Translation2d(WIDTH * -0.5, HEIGHT * -0.5);

        int DRIVE_PORT = 7;
        int TURN_PORT = 8;

        int ENCODER_PORT = 2;

        Rotation2d OFFSET = Rotation2d.fromDegrees(315.0);

        Module MODULE = new Module(
            ID, LOCATION, 
            new StateSpaceDriveControl(
                new NEODrive(DRIVE_PORT),
                NEOModule.Drive.StateSpace.getControlLoop()
            ), 
            new FFBTurnControl(
                new NEOMagTurn(TURN_PORT, ENCODER_PORT, OFFSET), 
                NEOModule.Turn.Feedback.getController()
            )
        );
    }

    Module[] MODULES = {
            TopRight.MODULE,
            TopLeft.MODULE,
            BottomLeft.MODULE,
            BottomRight.MODULE
    };
}
