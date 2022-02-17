package frc.robot.commands.traverse;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Traverse;

public class StopTraverse extends CommandBase{
    private final Traverse m_traverseSubsystem;

    public StopTraverse(Traverse traverseSubsystem){
        m_traverseSubsystem = traverseSubsystem;
        addRequirements(m_traverseSubsystem);
    }

    @Override
    public void initialize(){
        m_traverseSubsystem.traverseOff();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
