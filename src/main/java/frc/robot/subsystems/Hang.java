package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CompressorConstants;
import frc.robot.Constants.HangConstants;

public class Hang extends SubsystemBase{
    private final CANSparkMax m_leftHang = new CANSparkMax(HangConstants.kLeftHangMotorPort, MotorType.kBrushless);
    private final CANSparkMax m_rightHang = new CANSparkMax(HangConstants.kRightHangMotorPort, MotorType.kBrushless);
    private final Solenoid m_leftDeploy = new Solenoid(CompressorConstants.kModuleID,PneumaticsModuleType.REVPH,HangConstants.kLeftDeployPort);
    private final Solenoid m_rightDeploy = new Solenoid(CompressorConstants.kModuleID,PneumaticsModuleType.REVPH,HangConstants.kRightDeployPort);

    private RelativeEncoder m_leftHangEncoder = m_leftHang.getEncoder();
    private RelativeEncoder m_rightHangEncoder = m_rightHang.getEncoder();

    public Hang(){
        m_leftHang.restoreFactoryDefaults();
        m_rightHang.restoreFactoryDefaults();
        m_leftHang.setSmartCurrentLimit(HangConstants.kCurrentLimit);
        m_rightHang.setSmartCurrentLimit(HangConstants.kCurrentLimit);

        m_leftHang.setIdleMode(IdleMode.kBrake);
        m_rightHang.setIdleMode(IdleMode.kBrake);

    }

    public void leftHangUp(){
        m_leftHang.set(HangConstants.kHangMotorSpeed);
    }
    public void leftHangDown(){
        m_leftHang.set(-HangConstants.kHangMotorSpeed);
    }
    public void leftHangStop(){
        m_leftHang.set(0.0);
    }
    public void leftHangDeploy(){
        m_leftDeploy.set(true);
    }
    public void leftHangRetract(){
        m_leftDeploy.set(false);
    }

    public double getLeftHangPosition(){
        return m_leftHangEncoder.getPosition();
    }

    public void resetLeftHangPosition(){
        m_leftHangEncoder.setPosition(0.0);
    }

    public void rightHangUp(){
        m_rightHang.set(HangConstants.kHangMotorSpeed);
    }
    public void rightHangDown(){
        m_rightHang.set(-HangConstants.kHangMotorSpeed);
    }
    public void rightHangStop(){
        m_rightHang.set(0.0);
    }
    public void rightHangDeploy(){
        m_rightDeploy.set(true);
    }
    public void rightHangRetract(){
        m_rightDeploy.set(false);
    }

    public double getRightHangPosition(){
        return m_rightHangEncoder.getPosition();
    }

    public void resetRightHangPosition(){
        m_rightHangEncoder.setPosition(0.0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("LHang Enc",getLeftHangPosition());
        SmartDashboard.putNumber("RHang Enc",getRightHangPosition());
        SmartDashboard.putBoolean("LHang Sol",m_leftDeploy.get());
        SmartDashboard.putBoolean("RHang Sol",m_rightDeploy.get());
    }

}
