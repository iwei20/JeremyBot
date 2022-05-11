/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.*;
import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.commands.autos.*;
import com.stuypulse.robot.constants.Modules.*;
import com.stuypulse.robot.subsystems.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // Subsystems
    public final Swerve drivetrain = new Swerve();

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Constants.Controls.Ports.DRIVER);
    
    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    public RobotContainer() {
        // Disable telementry to reduce lag
        LiveWindow.disableAllTelemetry();
        DriverStation.silenceJoystickConnectionWarning(true);

        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driver));
    }

    private void configureButtonBindings() {
        driver.getTopButton().whileHeld(new ResetModule(drivetrain, drivetrain.getModule(TopRight.ID), driver));
        driver.getLeftButton().whileHeld(new ResetModule(drivetrain, drivetrain.getModule(TopLeft.ID), driver));
        driver.getBottomButton().whileHeld(new ResetModule(drivetrain, drivetrain.getModule(BottomLeft.ID), driver));
        driver.getRightButton().whileHeld(new ResetModule(drivetrain, drivetrain.getModule(BottomRight.ID), driver));
    }

    public void configureAutons() {
        autonChooser.addOption("Do Nothing", new DoNothingAuto());
        SmartDashboard.putData("Autonomous", autonChooser);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autonChooser.getSelected();
    }

}