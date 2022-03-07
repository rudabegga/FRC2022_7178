package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{

    NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry m_tx = m_table.getEntry("tx"); // target horizontal offset from crosshair
    NetworkTableEntry m_ty = m_table.getEntry("ty"); // target vertical offset from crosshair
    NetworkTableEntry m_ta = m_table.getEntry("ta"); // target area
    NetworkTableEntry m_tv = m_table.getEntry("tv"); // target available
    NetworkTableEntry m_ledMode = m_table.getEntry("ledMode"); // control the ledMode
    NetworkTableEntry m_camMode = m_table.getEntry("camMode"); // control the cameraMode
    NetworkTableEntry m_pipeline = m_table.getEntry("pipeline");
    NetworkTableEntry m_stream = m_table.getEntry("stream");

    public Vision(){
        m_stream.setNumber(0); // set the default to show both Shoot and Intake Cam when connected
    }

    public void setLedMode(boolean mode){
        if(mode){
            m_ledMode.setNumber(3); // turn on led
        }else{
            m_ledMode.setNumber(1); // turn off led
        }
    }

    public void setDriverMode(boolean mode){
        if(mode){
            m_camMode.setNumber(1); //Turn On Driver Mode No Vision Processing
        }else{
            m_camMode.setNumber(0); //Turn on Vision Processing
        }
    }

    public void setPipeline(int choice){
        m_pipeline.setNumber(choice); //Unless multiple pipelines this should not be called
    }

    public boolean getTarget(){
        if(m_tv.getDouble(0.0) > 0){ // if it has a target then it will be 1
            return true;
        }
        return false;
    }

    public double getXOffset(){
        return m_tx.getDouble(-50.0); // return -50 if no offset availiable.
    }


    @Override
    public void periodic(){
        SmartDashboard.putNumber("Tx",m_tx.getDouble(50.0));
        SmartDashboard.putNumber("Ty",m_ty.getDouble(50.0));
        SmartDashboard.putNumber("Ta",m_ta.getDouble(50.0));
    }
    
}
