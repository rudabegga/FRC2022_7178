package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootSpeed extends CommandBase {
    private final Shooter m_shooterSubsystem;
    private double m_upperShootSpeed;
    private double m_lowerShootSpeed;

    public ShootSpeed(Shooter shooterSubsystem, double upperShootSpeed, double lowerShootSpeed){
        m_shooterSubsystem = shooterSubsystem;
        m_upperShootSpeed = upperShootSpeed;
        m_lowerShootSpeed = lowerShootSpeed;
        addRequirements(m_shooterSubsystem);
    }

    @Override
    public void initialize(){
        m_shooterSubsystem.speedControlShooter(m_upperShootSpeed, m_lowerShootSpeed);
    }

    @Override
    public void end(boolean interrupted){
        m_upperShootSpeed = 0;
        m_lowerShootSpeed = 0;
    }

    @Override
    public void execute(){

    }

    @Override
    public boolean isFinished(){
        if(m_shooterSubsystem.getUpperShooterVelocity() >= m_upperShootSpeed - 100 && m_shooterSubsystem.getLowerShooterVelocity() >= m_lowerShootSpeed -100){
            return true;
        }else{
            return false;
        }
    }

    
}
