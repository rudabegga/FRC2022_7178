package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.subsystems.Chassis;

public class AutonTaxiOnly extends SequentialCommandGroup{
    public AutonTaxiOnly(Chassis chassis){
        addCommands(
            new DriveDistance(75,0.45,chassis)
        );
    }
}
