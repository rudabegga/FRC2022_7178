package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class StopShooter extends CommandBase{
    private final Shooter m_shooterSubsystem;

    public StopShooter(Shooter shooterSubsystem){
        m_shooterSubsystem = shooterSubsystem;
        addRequirements(m_shooterSubsystem);
    }

    @Override
    public void initialize(){
        m_shooterSubsystem.assistOff();
        m_shooterSubsystem.shooterOff();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
