package frc.robot.subsystems.linslide;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
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

    static final TalonFXConfiguration linslideTalonFXConfigurations = new TalonFXConfiguration().withSlot0(SLOT_0_CONFIGS);
    //Add code on line above still

}
