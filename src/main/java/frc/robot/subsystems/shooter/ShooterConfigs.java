package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.signals.InvertedValue;

public class ShooterConfigs {
 public static final int FIRST_MOTOR_ID=14;
    public static final int SECOND_MOTOR_ID=100;
    public static final int THIRD_MOTOR_ID=20000;
    static final double ROTOR_TO_SENSOR = 1;
    static final double SENSOR_TO_MECHANISM = 1;

    public static final Slot0Configs SLOT_0_CONFIGS = new Slot0Configs()
            .withKP(8)
            .withKI(0)
            .withKD(0)
            .withKS(4)
            .withKV(0.55)
            .withKA(256);

    public static final Slot1Configs SLOT_1_CONFIGS = new Slot1Configs()
            .withKP(7.9)
            .withKI(1)
            .withKD(0)
            .withKS(5)
            .withKV(1.1)
            .withKA(256);

    public static final MotionMagicConfigs MOTION_MAGIC_CONFIGS = new MotionMagicConfigs()
            .withMotionMagicCruiseVelocity(1)
            .withMotionMagicAcceleration(256)
            .withMotionMagicJerk(0);

    static final TalonFXConfiguration TOP_MOTOR_CONFIGS = new TalonFXConfiguration()
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

}