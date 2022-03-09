package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase{
    private final WPI_TalonSRX m_indexerMotor = new WPI_TalonSRX(IndexerConstants.kIndexerMotorPort);
    private final DigitalInput m_indexerSensor = new DigitalInput(IndexerConstants.kIndexerSensorPort);

    public Indexer(){
        m_indexerMotor.setInverted(true);
        m_indexerMotor.setNeutralMode(NeutralMode.Brake);

    }

    public void indexerOn(){
        m_indexerMotor.set(ControlMode.PercentOutput,IndexerConstants.kIndexerMotorSpeed);
    }

    public void indexerOff(){
        m_indexerMotor.set(ControlMode.PercentOutput,0);
    }

    public void indexerReverse(){
        m_indexerMotor.set(ControlMode.PercentOutput,-IndexerConstants.kIndexerMotorSpeed);
    }

    public boolean getIndexerSensor(){
        return m_indexerSensor.get();
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Indexer",m_indexerMotor.get());
        SmartDashboard.putBoolean("Ind_Sensor", m_indexerSensor.get());
    }

}
