package frc.robot.subsystems.linslide;

import edu.wpi.first.units.measure.Angle;
import org.littletonrobotics.junction.AutoLog;

public interface LinslideIO {
    @AutoLog
    public static class LinslideIOInputs {
        public boolean isStowed = true;
        public boolean isDeployed = false;
        public double position = 0;
    }

    public default void updateInputs(LinslideIOInputs inputs) {}

    public default void setDeployed(Angle position) {}

    public default void setStowed(Angle position) {}
}
