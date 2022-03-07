package frc.robot.commands.chassis;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ChassisConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Vision;

public class AutoAim extends CommandBase{
    private final Chassis m_chassisSubsystem;
    private final Vision m_visionSubsystem;
    private boolean m_complete = false;

    public AutoAim(Chassis chassisSubsystem, Vision visionSubsystem){
        m_chassisSubsystem = chassisSubsystem;
        m_visionSubsystem = visionSubsystem;
        addRequirements(m_chassisSubsystem, m_visionSubsystem);
    }
    
    @Override
    public void initialize(){
        m_complete = false;
        m_visionSubsystem.setLedMode(true); // turn on led
        m_visionSubsystem.setDriverMode(false); // turn on vision processing
    }

    @Override
    public void execute(){
        if(m_visionSubsystem.getTarget()){
            // we have a target
            double xOffset = m_visionSubsystem.getXOffset(); // store the X Offset of the target
            if(Math.abs(xOffset) < VisionConstants.kInRangeDeg){
                m_chassisSubsystem.drive(0.0, 0.0, 0.0);
                m_complete = true; // we are aimed to center
            }else if(xOffset > 0.0){
                // center target is to the right of center so turn left
                m_chassisSubsystem.drive(0.0, 0.0, -ChassisConstants.kAutoRotationSpeed);

            }else if(xOffset <= 0.0){
                // center of target is to the left of center so turn right
                m_chassisSubsystem.drive(0.0, 0.0, ChassisConstants.kAutoRotationSpeed);
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        m_visionSubsystem.setLedMode(false); // turn off led
        m_visionSubsystem.setDriverMode(true); // turn on drivermode
    }

    @Override
    public boolean isFinished(){
        return m_complete;
    }

}
