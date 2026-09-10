package frc.robot.subsystems.hood;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Robot;

public class HoodConfigs {
    static final int MOTOR_ID = 62;

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
                     .withInverted(InvertedValue.Clockwise_Positive).
                     withNeutralMode(NeutralModeValue.Brake))
             .withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
                     .withForwardSoftLimitEnable(true)
                     .withForwardSoftLimitThreshold(10)
                     .withReverseSoftLimitEnable(true)
                     .withReverseSoftLimitThreshold(0));

}
