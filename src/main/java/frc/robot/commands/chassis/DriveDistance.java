package frc.robot.commands.chassis;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistance extends CommandBase{
    private final Chassis m_drive;
    private double m_distance;
    private double m_speed;
    private double start_encoders;
    private boolean m_complete = false;
    
    public DriveDistance(double inches, double speed, Chassis subsystem){
        m_distance = inches;
        m_speed = speed;
        m_drive = subsystem;
        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_complete = false;
        start_encoders = m_drive.getAverageEncoderDistanceInches();
    }

    @Override
    public void end(boolean interrupted){
        m_drive.drive(0.0, 0.0, 0.0);
    }

    @Override
    public void execute(){
        if(Math.abs(m_drive.getAverageEcoderPosition()-start_encoders)>=m_distance){
            m_drive.drive(0.0,0.0,0.0);
            m_complete = true;
        }else{
            m_drive.drive(0.0,0.0,m_speed);
        }
    }

    @Override
    public boolean isFinished(){
        return m_complete;
    }
}
