package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.interpolation.InterpolatingTreeMap;
import edu.wpi.first.math.interpolation.InverseInterpolator;
import edu.wpi.first.units.Units;
import frc.robot.util.ShooterStateData;

public class ShooterConfigs {
    public static final int FIRST_MOTOR_ID = 14;
    public static final int SECOND_MOTOR_ID = 10;
    static final double ROTOR_TO_SENSOR = 1;
    static final double SENSOR_TO_MECHANISM = 1;

    public static final Slot0Configs SLOT_0_CONFIGS =
            new Slot0Configs().withKP(0).withKI(0).withKD(0).withKS(0).withKV(0).withKA(0);

    public static final Slot1Configs SLOT_1_CONFIGS =
            new Slot1Configs().withKP(0).withKI(0).withKD(0).withKS(0).withKV(0).withKA(0);

    public static final MotionMagicConfigs MOTION_MAGIC_CONFIGS = new MotionMagicConfigs()
            .withMotionMagicCruiseVelocity(1)
            .withMotionMagicAcceleration(2)
            .withMotionMagicJerk(0);

    public static final TalonFXConfiguration TOP_MOTOR_CONFIGS = new TalonFXConfiguration()
            .withFeedback(new FeedbackConfigs()
                    .withSensorToMechanismRatio(SENSOR_TO_MECHANISM)
                    .withRotorToSensorRatio(ROTOR_TO_SENSOR))
            .withSlot0(SLOT_0_CONFIGS)
            .withSlot1(SLOT_1_CONFIGS)
            .withMotionMagic(MOTION_MAGIC_CONFIGS)
            .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.CounterClockwise_Positive));

    static final TalonFXConfiguration BOTTOM_MOTOR_CONFIGS = new TalonFXConfiguration()
            .withFeedback(new FeedbackConfigs()
                    .withSensorToMechanismRatio(SENSOR_TO_MECHANISM)
                    .withRotorToSensorRatio(ROTOR_TO_SENSOR))
            .withSlot0(SLOT_0_CONFIGS)
            .withMotionMagic(MOTION_MAGIC_CONFIGS);

    public static final InterpolatingTreeMap<Double, ShooterStateData> SHOOTER_MAP =
            new InterpolatingTreeMap<>(InverseInterpolator.forDouble(), ShooterStateData.interpolator);

    static {
        SHOOTER_MAP.put(0.0, new ShooterStateData(Units.Rotations.of(0), 0, 0.0));
    }
}
