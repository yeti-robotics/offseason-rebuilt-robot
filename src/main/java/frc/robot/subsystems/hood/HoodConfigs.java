package frc.robot.subsystems.hood;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Robot;

public class HoodConfigs {
    static final int MOTOR_ID = 62;
    public static final int ENCODER_ID = 54;
    public static final int SENSOR_TO_MECHANISM_RATIO = 0;
    public static final int MOTOR_TO_SENSOR_RATIO = 0;

    private static final Slot0Configs SLOT_0_CONFIGS = Robot.isReal()
            ? new Slot0Configs()
                    .withKP(0)
                    .withKI(0)
                    .withKD(0)
                    .withKA(0)
                    .withKS(0)
                    .withGravityType(GravityTypeValue.Elevator_Static)
            : new Slot0Configs()
                    .withKP(1)
                    .withKI(0)
                    .withKD(0)
                    .withKA(0)
                    .withKS(0)
                    .withGravityType(GravityTypeValue.Elevator_Static);

    static final TalonFXConfiguration HOOD_MOTOR_CONFIGS = new TalonFXConfiguration()
            .withSlot0(SLOT_0_CONFIGS)
            .withMotorOutput(new MotorOutputConfigs()
                    .withInverted(InvertedValue.Clockwise_Positive)
                    .withNeutralMode(NeutralModeValue.Brake))
            .withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
                    .withForwardSoftLimitEnable(true)
                    .withForwardSoftLimitThreshold(10)
                    .withReverseSoftLimitEnable(true)
                    .withReverseSoftLimitThreshold(0))
            .withFeedback(new FeedbackConfigs()
                    .withFeedbackRemoteSensorID(ENCODER_ID)
                    .withFeedbackSensorSource(FeedbackSensorSourceValue.FusedCANcoder)
                    .withSensorToMechanismRatio(SENSOR_TO_MECHANISM_RATIO)
                    .withRotorToSensorRatio(MOTOR_TO_SENSOR_RATIO));
}
