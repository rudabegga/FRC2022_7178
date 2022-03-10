package frc.robot.commands.indexer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Traverse;

public class AutoIndexer extends CommandBase {

    public enum IndexerStage {
        EMPTY,
        BALL_PRESENT,
        BALL_INDEXED,
        FULL
    }

    private final Intake m_intakeSubsystem;
    private final Indexer m_indexerSubsystem;
    private final Traverse m_traverseSubsystem;
    public IndexerStage m_loadingStage = IndexerStage.EMPTY;
    public boolean m_complete = false; // used to stop the command

    public AutoIndexer(Intake intakeSubsystem, Indexer indexerSubsystem, Traverse traverseSubsystem){
        m_intakeSubsystem = intakeSubsystem;
        m_indexerSubsystem = indexerSubsystem;
        m_traverseSubsystem = traverseSubsystem;
        addRequirements(m_intakeSubsystem, m_indexerSubsystem, m_traverseSubsystem);
    }

    @Override
    public void initialize(){
        m_intakeSubsystem.intakeDeploy();
        // first we need to determine what state to start at.
        if(!m_indexerSubsystem.getIndexerSensor() && !m_traverseSubsystem.getTraverseSensor()){
            // if both sensors detect then we are full
            m_loadingStage = IndexerStage.FULL;
            m_intakeSubsystem.intakeOff();;
            m_indexerSubsystem.indexerOff();
            m_traverseSubsystem.traverseOff();
            SmartDashboard.putString("Index State","FULL");
        }else if(m_indexerSubsystem.getIndexerSensor() && !m_traverseSubsystem.getTraverseSensor()){
            // we only see a ball at the traverser
            m_loadingStage = IndexerStage.BALL_PRESENT;
            SmartDashboard.putString("Index State","BALL PRESENT");
        }else if(!m_indexerSubsystem.getIndexerSensor() && m_traverseSubsystem.getTraverseSensor()){
            // we only see a ball at the Indexer
            m_loadingStage = IndexerStage.BALL_INDEXED;
            SmartDashboard.putString("Index State","BALL INDEXED");
        }else{
            // no sensors so we must be empty
            m_loadingStage = IndexerStage.EMPTY;
            SmartDashboard.putString("Index State","EMPTY");
        }
        m_complete = false;
    }

    @Override
    public void execute(){

        switch(m_loadingStage){
            case EMPTY:
                if(!m_traverseSubsystem.getTraverseSensor()){
                    m_loadingStage = IndexerStage.BALL_PRESENT;
                    SmartDashboard.putString("Index State","BALL PRESENT");
                }
                m_intakeSubsystem.intakeOn();
                m_traverseSubsystem.traverseOn();
                break;
            case BALL_PRESENT:
                if(!m_indexerSubsystem.getIndexerSensor()){
                    m_loadingStage = IndexerStage.BALL_INDEXED;
                    m_indexerSubsystem.indexerOff();
                    SmartDashboard.putString("Index State","BALL INDEXED");
                }else{
                    m_indexerSubsystem.indexerOn();
                    m_intakeSubsystem.intakeOn();
                    m_traverseSubsystem.traverseOn();
                }
                break;
            case BALL_INDEXED:
                if(!m_indexerSubsystem.getIndexerSensor() && !m_traverseSubsystem.getTraverseSensor()){
                    m_loadingStage = IndexerStage.FULL;
                    m_indexerSubsystem.indexerOff();
                    m_intakeSubsystem.intakeOff();
                    m_traverseSubsystem.traverseOff();
                    SmartDashboard.putString("Index State","FULL");
                }else{
                    m_intakeSubsystem.intakeOn();
                    m_traverseSubsystem.traverseOn();
                }
                break;
            case FULL:
                m_indexerSubsystem.indexerOff();
                m_intakeSubsystem.intakeOff();
                m_traverseSubsystem.traverseOff();
                m_complete = true;
                break;
        }        
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }
}
