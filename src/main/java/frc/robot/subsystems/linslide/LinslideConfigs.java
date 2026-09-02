package frc.robot.subsystems.linslide;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Robot;

public class LinslideConfigs {
    static final int MOTOR_ID = 60;

    private static Slot0Configs SLOT_0_CONFIGS = Robot.isReal()
            ? new Slot0Configs()
                .withKP(0.0)
                .withKI(0)
                .withKD(0)
                .withKA(0)
                .withKV(0)
                .withGravityType(GravityTypeValue.Elevator_Static)
            : new Slot0Configs()
                .withKP(0)
                .withKI(0)
                .withKD(0)
                .withKA(0)
                .withKV(0)
                .withGravityType(GravityTypeValue.Elevator_Static);

    static final TalonFXConfiguration linslideTalonFXConfigurations = new TalonFXConfiguration()
            .withSlot0(SLOT_0_CONFIGS)
            .withMotionMagic(new MotionMagicConfigs()
                    .withMotionMagicAcceleration(2)
                    .withMotionMagicCruiseVelocity(1)
                    .withMotionMagicJerk(0))
            .withMotorOutput(new MotorOutputConfigs()
                    .withInverted(InvertedValue.CounterClockwise_Positive)//Might change later
                    .withNeutralMode(NeutralModeValue.Brake))
            .withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
                    .withReverseSoftLimitThreshold(0)
                    .withReverseSoftLimitEnable(true)
                    .withForwardSoftLimitThreshold(10) //will need to change later
                    .withForwardSoftLimitEnable(true)
            );

}
