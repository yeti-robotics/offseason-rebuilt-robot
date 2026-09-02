package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

public class IntakeConfigs {
    // Need to change IDs for motors
    static final int RIGHT_INTAKE_MOTOR_ID = 13;
    static final int LEFT_INTAKE_MOTOR_ID = 12;
    // Need to change values for voltage
    static final double INTAKE_VOLTAGE = 7.0;
    static final double OUTTAKE_VOLTAGE = 8.0;
    // Need to change values for roll in
    public static double ROLL_IN_SPEED = 3.0;
    public static double ROLL_IN_SLOWER = 2.0;

    public static double ROLLER_SPEED = -1.0;
    public static double INNER_ROLLER_SPEED = -2.0;

    static TalonFXConfiguration RIGHT_TALONFX_CONFIGS = new TalonFXConfiguration()
            .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive))
            .withCurrentLimits(new CurrentLimitsConfigs()
                    .withStatorCurrentLimit(0)
                    .withSupplyCurrentLimit(0)
                    .withStatorCurrentLimitEnable(true)
                    .withSupplyCurrentLimitEnable(true)
                    .withSupplyCurrentLowerLimit(0)
                    .withSupplyCurrentLowerTime(0));
}


