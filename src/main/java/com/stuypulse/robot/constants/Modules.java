package com.stuypulse.robot.constants;

import com.stuypulse.robot.subsystems.swerve.Module;
import com.stuypulse.robot.subsystems.swerve.neo.NEODriveControl;
import com.stuypulse.robot.subsystems.swerve.neo.NEOTurnControl;

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
        Translation2d LOCATION = new Translation2d(WIDTH * +0.5, HEIGHT * +0.5);

        int DRIVE_PORT = 1;
        int TURN_PORT = 2;

        Module MODULE = new Module(
            ID, LOCATION, 
            new NEODriveControl(DRIVE_PORT), 
            new NEOTurnControl(TURN_PORT)
        );
    }

    public interface TopLeft {
        String ID = "Top Left";
        Translation2d LOCATION = new Translation2d(WIDTH * -0.5, HEIGHT * +0.5);

        int DRIVE_PORT = 3;
        int TURN_PORT = 4;

        Module MODULE = new Module(
            ID, LOCATION, 
            new NEODriveControl(DRIVE_PORT), 
            new NEOTurnControl(TURN_PORT)
        );
    }

    public interface BottomLeft {
        String ID = "Bottom Left";
        Translation2d LOCATION = new Translation2d(WIDTH * -0.5, HEIGHT * -0.5);
        
        int DRIVE_PORT = 5;
        int TURN_PORT = 6;

        Module MODULE = new Module(
            ID, LOCATION, 
            new NEODriveControl(DRIVE_PORT), 
            new NEOTurnControl(TURN_PORT)
        );
    }

    public interface BottomRight {
        String ID = "Bottom Right";
        Translation2d LOCATION = new Translation2d(WIDTH * +0.5, HEIGHT * -0.5);

        int DRIVE_PORT = 7;
        int TURN_PORT = 8;

        Module MODULE = new Module(
            ID, LOCATION, 
            new NEODriveControl(DRIVE_PORT), 
            new NEOTurnControl(TURN_PORT)
        );
    }

    Module[] MODULES = {
            TopRight.MODULE,
            TopLeft.MODULE,
            BottomLeft.MODULE,
            BottomRight.MODULE
    };
}