package frc.robot.commands.chassis;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistance extends CommandBase{
    private final Chassis m_drive;
    private double m_distance;
    private double m_speed;
    private double m_target;
    
    public DriveDistance(double inches, double speed, Chassis subsystem){
        m_distance = inches;
        m_speed = speed;
        m_drive = subsystem;
    }

    @Override
    public void initialize(){
        m_target = m_distance + m_drive.getLeftFrontEncoder();
    }

    @Override
    public void end(boolean interrupted){
        m_drive.drive(0.0, 0.0, 0.0);
    }

    @Override
    public boolean isFinished(){
        if(m_distance > 0){ // assume a forward target
            if(m_drive.getLeftFrontEncoder() < m_target){
                if(m_speed < 0){
                    m_speed *=(-1.0); // if they put in a negative speed it will never get there so negate it
                }
                m_drive.drive(m_speed,0.0,0.0);
                return false;
            }else{
                return true;
            }
        }else if(m_distance < 0){ // assume a reverse target
            if(m_drive.getLeftFrontEncoder() > m_target){
                if(m_speed > 0){
                    m_speed *=(-1.0); // if they put in a positive speed it will never get there so negate it
                }
                m_drive.drive(m_speed,0.0,0.0);
                return false;
            }else{
                return true;
            }
        }else{
            m_drive.drive(0.0,0.0,0.0);
            return true;
        }
    }
}
