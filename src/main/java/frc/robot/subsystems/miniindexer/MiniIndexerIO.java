package frc.robot.subsystems.miniindexer;

import org.littletonrobotics.junction.AutoLog;

public interface MiniIndexerIO {
    @AutoLog
    public static class MiniIndexerIOInputs {
        public double indexerSpeed = 0.0;
        public double motorTemp = 0.0;
        public double supplyCurrent = 0;
    }

    public default void updateInputs(MiniIndexerIOInputs inputs) {}

    public default void spinVelocity(double rps) {}

    public default void runAtPower(double power) {}

    public default void stopMotor() {}
}
