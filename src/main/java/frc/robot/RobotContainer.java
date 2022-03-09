// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.chassis.AutoAim;
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.commands.indexer.AutoIndexer;
import frc.robot.commands.indexer.StopIndexer;
import frc.robot.commands.intake.StopIntake;
import frc.robot.commands.shooter.AutoShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.traverse.StopTraverse;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Hang;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Traverse;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_chassisSubsystem = new Chassis();
  private final Intake m_intakeSubsystem = new Intake();
  private final Indexer m_indexerSubsystem = new Indexer();
  private final Traverse m_traverseSubsystem = new Traverse();
  private final Shooter m_shooterSubsystem = new Shooter();
  private final Vision m_visionSubsystem = new Vision();
  private final Hang m_hangSubsystem = new Hang();
  private final LED m_ledSubsystem = new LED();

  private final DriveDistance m_autoCommand = new DriveDistance(10,0.5,m_chassisSubsystem);

  XboxController m_driverController = new XboxController(OIConstants.kDriverPort);
  XboxController m_codriverController = new XboxController(OIConstants.kCoDriverPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_chassisSubsystem.setDefaultCommand(
      new DefaultDrive(m_chassisSubsystem,
      () -> -m_driverController.getRightX(),
      () -> m_driverController.getRightY(),
      () -> m_driverController.getLeftY()));
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
    // Right Bumper Button Auto AIM 
    new JoystickButton(m_driverController,Button.kRightBumper.value)
    .whenPressed(
      new SequentialCommandGroup(
        new InstantCommand(m_ledSubsystem::red,m_ledSubsystem),
        new AutoAim(m_chassisSubsystem, m_visionSubsystem),
        new InstantCommand(m_ledSubsystem::green,m_ledSubsystem)
      )
    )
    .whenReleased(
      new InstantCommand(m_ledSubsystem::green,m_ledSubsystem)
    );

    // A Button Left Hang Arm Up
    new JoystickButton(m_driverController,Button.kA.value)
    .whenPressed(
        new InstantCommand(m_hangSubsystem::leftHangUp,m_hangSubsystem)
    )
    .whenReleased(
      new InstantCommand(m_hangSubsystem::leftHangStop,m_hangSubsystem)
    );

    // B Button Left Hang Arm Down
    new JoystickButton(m_driverController,Button.kB.value)
    .whenPressed(
        new InstantCommand(m_hangSubsystem::leftHangDown,m_hangSubsystem)
    )
    .whenReleased(
      new InstantCommand(m_hangSubsystem::leftHangStop,m_hangSubsystem)
    );

    // X Button Right Hang Arm Up
    new JoystickButton(m_driverController,Button.kX.value)
    .whenPressed(
        new InstantCommand(m_hangSubsystem::rightHangUp,m_hangSubsystem)
    )
    .whenReleased(
      new InstantCommand(m_hangSubsystem::rightHangStop,m_hangSubsystem)
    );

    // Y Button Right Hang Arm Down
    new JoystickButton(m_driverController,Button.kY.value)
    .whenPressed(
        new InstantCommand(m_hangSubsystem::rightHangDown,m_hangSubsystem)
    )
    .whenReleased(
      new InstantCommand(m_hangSubsystem::rightHangStop,m_hangSubsystem)
    );

    /**************************
     * Co Driver Button Commands *
    ***************************/
    // A Button Auto Intake
    new JoystickButton(m_codriverController, Button.kA.value)
    .whenPressed(
      new SequentialCommandGroup(
        new InstantCommand(m_ledSubsystem::red,m_ledSubsystem),
        new AutoIndexer(m_intakeSubsystem, m_indexerSubsystem, m_traverseSubsystem),
        new InstantCommand(m_ledSubsystem::green,m_ledSubsystem)
      )
    )
    .whenReleased(
      new ParallelCommandGroup(
        new InstantCommand(m_ledSubsystem::green,m_ledSubsystem),
        new StopIntake(m_intakeSubsystem),
        new StopTraverse(m_traverseSubsystem),
        new StopIndexer(m_indexerSubsystem)
      )
    );

    // X Button Auto Shoot
    new JoystickButton(m_codriverController, Button.kX.value)
    .whenPressed(
      new SequentialCommandGroup(
        new InstantCommand(m_ledSubsystem::red,m_ledSubsystem),
        new AutoShooter(m_indexerSubsystem,m_shooterSubsystem),
        new InstantCommand(m_ledSubsystem::green,m_ledSubsystem)
      )
    )
    .whenReleased(
      new ParallelCommandGroup(
        new InstantCommand(m_ledSubsystem::blue,m_ledSubsystem),
        new StopIndexer(m_indexerSubsystem),
        new StopShooter(m_shooterSubsystem)
      )
    );

    // Y Button Reverse intake / traverse / index system
    new JoystickButton(m_codriverController, Button.kY.value)
    .whenPressed(
      new ParallelCommandGroup(
        new InstantCommand(m_intakeSubsystem::intakeReverse,m_intakeSubsystem),
        new InstantCommand(m_traverseSubsystem::traverseReverse,m_traverseSubsystem),
        new InstantCommand(m_indexerSubsystem::indexerReverse,m_indexerSubsystem)
      )
    )
    .whenReleased(
      new ParallelCommandGroup(
        new InstantCommand(m_intakeSubsystem::intakeOff,m_intakeSubsystem),
        new InstantCommand(m_traverseSubsystem::traverseOff,m_traverseSubsystem),
        new InstantCommand(m_indexerSubsystem::indexerOff,m_indexerSubsystem)
      )
    );

    // B Button Manual Runn intake / traverse / index system
    new JoystickButton(m_codriverController, Button.kB.value)
    .whenPressed(
      new ParallelCommandGroup(
        new InstantCommand(m_intakeSubsystem::intakeOn,m_intakeSubsystem),
        new InstantCommand(m_traverseSubsystem::traverseOn,m_traverseSubsystem),
        new InstantCommand(m_indexerSubsystem::indexerOn,m_indexerSubsystem)
      )
    )
    .whenReleased(
      new ParallelCommandGroup(
        new InstantCommand(m_intakeSubsystem::intakeOff,m_intakeSubsystem),
        new InstantCommand(m_traverseSubsystem::traverseOff,m_traverseSubsystem),
        new InstantCommand(m_indexerSubsystem::indexerOff,m_indexerSubsystem)
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
