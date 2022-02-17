// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_chassisSubsystem = new Chassis();

  private final DriveDistance m_autoCommand = new DriveDistance(10,0.5,m_chassisSubsystem);

  XboxController m_driverController = new XboxController(OIConstants.kDriverPort);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_chassisSubsystem.setDefaultCommand(
      new DefaultDrive(m_chassisSubsystem,
      () -> m_driverController.getLeftY(),
      () -> m_driverController.getLeftX(),
      () -> m_driverController.getRightX()));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
