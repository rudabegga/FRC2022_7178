package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase{
    private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeMotorPort);

    public Intake(){
        m_intakeMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void intakeOn(){
        m_intakeMotor.set(ControlMode.PercentOutput,IntakeConstants.kIntakeMotorSpeed);
    }

    public void intakeOff(){
        m_intakeMotor.set(ControlMode.PercentOutput,0);
    }

    public void intakeReverse(){
        m_intakeMotor.set(ControlMode.PercentOutput,-IntakeConstants.kIntakeMotorSpeed);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake",m_intakeMotor.get());
    }
    
}
