package frc.robot.subsystems.turret;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Robot;

public class TurretConfigs {

    // Need to change IDs
    static final int TURRET_MOTOR_ID = 78;
    static final int MOTOR_TO_SENSOR_RATIO = 89;
    static final int SENSOR_TO_MEHCANISM_RATIO = 64;

    public static final Translation2d turretOffset =
            new Translation2d(Units.inchesToMeters(10.5625), Units.inchesToMeters(4.66145));

    // Need to change IDs
    static final Slot0Configs SLOT_0_CONFIGS = Robot.isReal()
            ? new Slot0Configs()
                    .withKP(0)
                    .withKI(0)
                    .withKD(0)
                    .withKV(0)
                    .withKA(0)
                    .withKS(0)
                    .withGravityType(GravityTypeValue.Elevator_Static)
            : new Slot0Configs()
                    .withKP(0)
                    .withKI(0)
                    .withKD(0)
                    .withKV(0)
                    .withKA(0)
                    .withKS(1)
                    .withGravityType(GravityTypeValue.Elevator_Static);

    static final TalonFXConfiguration TURRET_CONFIGS = new TalonFXConfiguration()
            .withSlot0(SLOT_0_CONFIGS)
            .withMotionMagic(new MotionMagicConfigs()
                    .withMotionMagicAcceleration(2)
                    .withMotionMagicCruiseVelocity(1)
                    .withMotionMagicJerk(0))
            .withMotorOutput(new MotorOutputConfigs()
                    .withInverted(InvertedValue.Clockwise_Positive)
                    .withNeutralMode(NeutralModeValue.Brake))
            .withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
                    .withReverseSoftLimitThreshold(0)
                    .withReverseSoftLimitEnable(true)
                    .withForwardSoftLimitThreshold(0)
                    .withForwardSoftLimitEnable(true));
}
