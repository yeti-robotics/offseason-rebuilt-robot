package frc.robot.subsystems.rollerbed;

import org.littletonrobotics.junction.AutoLog;

public interface RollerbedIO {
    @AutoLog
    public static class RollerbedIOinputs {
        public double rollerSpeed = 0.0;
        public double motortemp = 0.0;
    }
    public default void updateInputs (RollerbedIOinputs inputs) {

    }
    public default void spinRoller (double rps) {

    }
    public default void applyPower (double power) {

    }
    public default void stopMotor () {
    }
}
