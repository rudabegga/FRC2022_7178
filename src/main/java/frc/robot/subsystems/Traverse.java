package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TraverseConstants;

public class Traverse extends SubsystemBase{
    private final WPI_TalonSRX m_traverseMotor = new WPI_TalonSRX(TraverseConstants.kTraverseMotorPort);
    private final DigitalInput m_traverseSensor = new DigitalInput(TraverseConstants.kTraverseSensorPort);

    public Traverse(){
        m_traverseMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void traverseOn(){
        m_traverseMotor.set(ControlMode.PercentOutput,TraverseConstants.kTraverseMotorSpeed);
    }

    public void traverseOff(){
        m_traverseMotor.set(ControlMode.PercentOutput,0);
    }

    public void traverseReverse(){
        m_traverseMotor.set(ControlMode.PercentOutput,-TraverseConstants.kTraverseMotorSpeed);
    }

    public boolean getTraverseSensor(){
        return m_traverseSensor.get();
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Traverse",m_traverseMotor.get());
        SmartDashboard.putBoolean("Tra_Sensor", m_traverseSensor.get());
    }

}
