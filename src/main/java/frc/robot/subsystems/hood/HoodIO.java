package frc.robot.subsystems.hood;

import edu.wpi.first.units.measure.Angle;
import org.littletonrobotics.junction.AutoLog;

public interface HoodIO {

    @AutoLog
    public static class HoodIOInputs {
        public double position = 0;
    }

    public default void updateInputs(HoodIOInputs inputs) {}

    public default void setPosition(Angle position) {}

    public default void applyPower(double power) {}
}
