package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class StopIntake extends CommandBase{
    private final Intake m_intakeSubsystem;
    
    public StopIntake(Intake intakeSubsystem){
        m_intakeSubsystem = intakeSubsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize(){
        m_intakeSubsystem.intakeOff();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
