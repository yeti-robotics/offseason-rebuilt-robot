package frc.robot.subsystems.miniindexer;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class MiniIndexerConfigs {
    // Might need to change IDs for the motor
    static final int MINI_INDEXER_ID = 2;
    public static final double MINI_INDEXER_SPEED = 1.0;
    public static final Slot0Configs SLOT_0_CONFIGS =
            new Slot0Configs().withKP(0).withKI(0).withKD(0).withKV(0).withKA(0).withKS(0);
    static final TalonFXConfiguration TALON_FX_CONFIGS = new TalonFXConfiguration()
            .withSlot0(SLOT_0_CONFIGS)
            .withMotionMagic(new MotionMagicConfigs()
                    .withMotionMagicAcceleration(1)
                    .withMotionMagicCruiseVelocity(2)
                    .withMotionMagicJerk(0));
}
