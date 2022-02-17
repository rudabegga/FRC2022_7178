package frc.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class StopIndexer extends CommandBase{
    private final Indexer m_indexerSubsystem;

    public StopIndexer(Indexer indexerSubsystem){
        m_indexerSubsystem = indexerSubsystem;
        addRequirements(m_indexerSubsystem);
    }

    @Override
    public void initialize(){
        m_indexerSubsystem.indexerOff();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
