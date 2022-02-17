// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.commands.indexer.AutoIndexer;
import frc.robot.commands.indexer.StopIndexer;
import frc.robot.commands.intake.StopIntake;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.traverse.StopTraverse;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Traverse;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_chassisSubsystem = new Chassis();
  private final Intake m_intakeSubsystem = new Intake();
  private final Indexer m_indexerSubsystem = new Indexer();
  private final Traverse m_traverseSubsystem = new Traverse();
  private final Shooter m_shooterSubsystem = new Shooter();

  private final DriveDistance m_autoCommand = new DriveDistance(10,0.5,m_chassisSubsystem);

  XboxController m_driverController = new XboxController(OIConstants.kDriverPort);
  XboxController m_codriverController = new XboxController(OIConstants.kCoDriverPort);

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
  private void configureButtonBindings() {

    /**************************
     * Driver Button Commands *
    ***************************/

    /**************************
     * Co Driver Button Commands *
    ***************************/
    new JoystickButton(m_codriverController, Button.kA.value)
    .whenPressed(
      new AutoIndexer(m_intakeSubsystem, m_indexerSubsystem, m_traverseSubsystem)
    )
    .whenReleased(
      new ParallelCommandGroup(
        new StopIntake(m_intakeSubsystem),
        new StopTraverse(m_traverseSubsystem),
        new StopIndexer(m_indexerSubsystem)
      )
    );


    new JoystickButton(m_codriverController, Button.kX.value)
    .whenPressed(
      new AutoShooter(m_indexerSubsystem,m_shooterSubsystem)
    )
    .whenReleased(
      new ParallelCommandGroup(
        new StopIndexer(m_indexerSubsystem),
        new StopShooter(m_shooterSubsystem)
      )
    );

  }

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
