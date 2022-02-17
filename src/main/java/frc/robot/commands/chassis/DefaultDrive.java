package frc.robot.commands.chassis;

import frc.robot.subsystems.Chassis;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final Chassis m_drive;
    private final DoubleSupplier m_ySpeed;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zSpeed;
    
    public DefaultDrive(Chassis sybsystem, DoubleSupplier ySpeed, DoubleSupplier xSpeed, DoubleSupplier zSpeed){
        m_drive = sybsystem;
        m_ySpeed = ySpeed;
        m_xSpeed = xSpeed;
        m_zSpeed = xSpeed;
        addRequirements(m_drive);
    }

    @Override
    public void execute(){
        m_drive.drive(m_ySpeed.getAsDouble(), m_xSpeed.getAsDouble(), m_zSpeed.getAsDouble());
    }

}
