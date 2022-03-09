package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class AutoShooter extends CommandBase{

    private final Indexer m_indexerSubsystem;
    private final Shooter m_shooterSubsystem;
    public boolean m_complete = false;
    private long m_sysTime;

    public enum ShooterStage {
        INDEXING,   // no ball present so run the indexer
        BALL_READY, // a ball is present at the Indexer
        RAMP_SHOOTER, // stage to ramp shooter to speed target
        SHOOTING, // stage to index the ball into the shooter
        EMPTY, // if NO BALL for xx time of indxing
    }

    public ShooterStage m_shooterStage = ShooterStage.BALL_READY; // assume we only shoot if we have a ball ready


    public AutoShooter(Indexer indexerSubsystem, Shooter shooterSubsystem){
        m_indexerSubsystem = indexerSubsystem;
        m_shooterSubsystem = shooterSubsystem;
        addRequirements(m_indexerSubsystem,m_shooterSubsystem);
    }

    @Override
    public void initialize(){
        if(!m_indexerSubsystem.getIndexerSensor()){
            m_shooterStage = ShooterStage.BALL_READY;
            SmartDashboard.putString("Shooter State","BALL READY");
        }else{
            m_shooterStage = ShooterStage.INDEXING;
            SmartDashboard.putString("Shooter State","INDEXING");
        }
        m_sysTime = System.currentTimeMillis();
        m_complete = false;
    }

    @Override
    public void execute(){
        switch(m_shooterStage) {
            case INDEXING: // Stage where the Ball should move up to the Index Sensor Also if no ball for xxxx ms then assume All Balls have shot
                if(!m_indexerSubsystem.getIndexerSensor()){
                    m_indexerSubsystem.indexerOff();
                    m_shooterStage = ShooterStage.BALL_READY;
                    SmartDashboard.putString("Shooter State","INDEXING");
                }else if((System.currentTimeMillis() - m_sysTime) > ShooterConstants.kMaxIndexTimeMS){ // if we haven't seen a ball for 1000 ms then set the system as complete.
                    m_shooterStage = ShooterStage.EMPTY;
                    SmartDashboard.putString("Shooter State","EMPTY");
                }else{
                    m_indexerSubsystem.indexerOn();
                }
                break;
            case BALL_READY: // Pass through Stage to start the Shooter and assist motors
                m_indexerSubsystem.indexerOff(); // turn off the Indexer
                m_shooterSubsystem.assistOn(); // turn on the assist
                m_shooterSubsystem.shooterOn(); // if we wanted to use percentage to control the shooter speed
                // m_shooterSubsystem.speedControlShooter(ShooterConstants.kUpperShooterMotorSpeed, ShooterConstants.kLowerShooterMotorSpeed); // this is a speed controlled shooter stage
                m_shooterStage = ShooterStage.RAMP_SHOOTER;
                SmartDashboard.putString("Shooter State","RAMPING");
                m_sysTime = System.currentTimeMillis(); // set a time stamp to wait at least xxx ms stable
                break;
            case RAMP_SHOOTER: // Stage that verifies 
                if(m_shooterSubsystem.getLowerShooterVelocity() >= ShooterConstants.kLowerShooterSpeed){
                    m_shooterStage = ShooterStage.SHOOTING;
                    m_sysTime = System.currentTimeMillis();
                    SmartDashboard.putString("Shooter State","SHOOTING");
                }else{
                    // here we can set an arbritrary time.
                    if(System.currentTimeMillis() - m_sysTime > ShooterConstants.kMaxRampTimeMS){ // this 250 ms timing needs to be tested
                        m_shooterStage = ShooterStage.SHOOTING; // if it is taking more than xxx ms to ramp up speed move to SHOOTING
                        m_sysTime = System.currentTimeMillis();
                        SmartDashboard.putString("Shooter State","SHOOTING");
                    }
                }
                break;
            case SHOOTING:
                // bassically we want to keep indexing until we see a ball
                if(m_indexerSubsystem.getIndexerSensor() && (System.currentTimeMillis() - m_sysTime) > ShooterConstants.kMaxReleaseTimeMS){// if we no longer see a ball for more than 100 ms then advance back to INDEXING
                    m_indexerSubsystem.indexerOff();
                    m_shooterStage = ShooterStage.INDEXING; // reset the system to process another ball
                    m_sysTime = System.currentTimeMillis();
                    SmartDashboard.putString("Shooter State","INDEXING");
                }else if(!m_indexerSubsystem.getIndexerSensor()){ // if we still have the ball in the indexer
                    m_indexerSubsystem.indexerOn();
                    m_sysTime = System.currentTimeMillis(); // reset the time latch
                }
                break;
            case EMPTY:
                m_indexerSubsystem.indexerOff();
                m_shooterSubsystem.shooterOff();
                m_shooterSubsystem.assistOff();
                m_complete = true;
                break;
        }
    }
    @Override
    public boolean isFinished(){
        return m_complete;
    }
}
