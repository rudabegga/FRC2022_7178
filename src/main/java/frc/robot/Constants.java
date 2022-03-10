package frc.robot;

public final class Constants {

    public static final class OIConstants{
        public static final int kDriverPort = 0;
        public static final int kCoDriverPort = 1;
    }

    public static final class ChassisConstants{
        public static final int kLeftFrontPort = 1;
        public static final int kRightFrontPort = 2;
        public static final int kRightRearPort = 3;
        public static final int kLeftRearPort = 4;

        public static final int kCurrentLimit = 40; // 40A current limit for motors eventhough it could be higher but not higher than 50A as the rating is 40A @80%

        public static final double kAutoRotationSpeed = 0.2; // speed to rotate for auto aim
        public static final double kEncoderConversionFactor = 6 * Math.PI / 9.52;
    }

    public static final class IntakeConstants{
        public static final int kIntakeMotorPort = 10;
        public static final double kIntakeMotorSpeed = 0.75;
        public static final int kIntakeDeployPort = 0;
    }

    public static final class TraverseConstants{
        public static final int kTraverseMotorPort = 5;
        public static final double kTraverseMotorSpeed = 0.75;
        public static final int kTraverseSensorPort = 1;
    }

    public static final class IndexerConstants{
        public static final int kIndexerMotorPort = 7;
        public static final double kIndexerMotorSpeed = 0.5;
        public static final int kIndexerSensorPort = 0;
    }

    public static final class ShooterConstants{
        public static final int kUpperShooterMotorPort = 13;
        public static final double kUpperShooterMotorSpeed = 0.17;
        public static final int kLowerShooterMotorPort = 14;
        public static final double kLowerShooterMotorSpeed = 0.90;
        public static final int kAssistMotorPort = 8;
        public static final double kAssistMotorSpeed = 0.8;

        public static final double kUpperShooterSpeed = 2000;
        public static final double kLowerShooterSpeed = 17000;
        public static final long kMaxIndexTimeMS = 2000; // time to determine we have no more balls in the indexer
        public static final long kMaxRampTimeMS = 2000; // time to deterine we have a stable speed
        public static final long kMaxReleaseTimeMS = 100; // time to recoginze we released a ball from the indexer
    }

    public static final class HangConstants{
        public static final int kRightHangMotorPort = 16;
        public static final int kLeftHangMotorPort = 15;
        public static final double kHangMotorSpeed = .5;
        public static final int kLeftDeployPort = 2;
        public static final int kRightDeployPort = 1;
        public static final int kCurrentLimit = 40; // 40A current limit for motors eventhough it could be higher but not higher than 50A as the rating is 40A @80%
    }

    public static final class LEDConstants{
        public static final int kLEDCount = 100;
        public static final int kLEDPWMPort = 0;
    }

    public static final class VisionConstants{
        public static final double kInRangeDeg = 5.0;  // if within 5 degrees of center than call it good

    }

    public static final class CompressorConstants{
        public static final int kModuleID = 17;
    }

}
