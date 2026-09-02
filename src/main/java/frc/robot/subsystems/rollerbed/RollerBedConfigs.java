package frc.robot.subsystems.rollerbed;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;

public class RollerBedConfigs {
    static final int ROLLER_BED_ID = 1;
    public static final double ROLLER_BED_SPEED = 0;

    public static final Slot0Configs SLOT_0_CONFIGS = new Slot0Configs()
            .withKP(0)
            .withKI(0)
            .withKD(0)
            .withKG(0)
            .withKV(0)
            .withKA(0)
            .withKS(0)
            .withGravityType(GravityTypeValue.Elevator_Static);
    static final TalonFXConfiguration TALON_FX_CONFIGS = new TalonFXConfiguration()
            .withSlot0(SLOT_0_CONFIGS)
            .withMotionMagic(new MotionMagicConfigs()
                    .withMotionMagicAcceleration(1)
                    .withMotionMagicCruiseVelocity(2)
                    .withMotionMagicJerk(0));

}