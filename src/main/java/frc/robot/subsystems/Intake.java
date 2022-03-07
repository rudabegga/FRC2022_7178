package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase{
    private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeMotorPort);
    private final Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);
    private final Solenoid m_intakeDeploy = new Solenoid(PneumaticsModuleType.REVPH,0);

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

    public void intakeDeploy(){
        m_intakeDeploy.set(true);
    }

    public void intakeRetract(){
        m_intakeDeploy.set(false);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake",m_intakeMotor.get());
        SmartDashboard.putNumber("Pressure",m_compressor.getPressure());
    }
    
}
