package frc.robot.subsystems.rollerbed;

import org.littletonrobotics.junction.AutoLog;

public interface RollerBedIO {
    @AutoLog
    public static class RollerBedIOInputs {
        public double rollerSpeed = 0.0;
        public double motortemp = 0.0;
    }

    public default void updateInputs(RollerBedIOInputs inputs) {}

    public default void spinRoller(double rps) {}

    public default void applyPower(double power) {}

    public default void stopMotor() {}
}
