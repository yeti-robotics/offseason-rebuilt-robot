package frc.robot.subsystems.hood;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import org.littletonrobotics.junction.AutoLog;

public interface HoodIO {

    @AutoLog
    public static class HoodIOInputs {
        public double position = 0;
    }

    public default void updateInputs(HoodIOInputs inputs) {}

    public default void setPosition(Angle position) {}

    public default void top() {}
    public default void mid() {}
    public default void low() {}
}
