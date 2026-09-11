package frc.robot.subsystems.turret;

import edu.wpi.first.units.measure.Angle;
import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {
    @AutoLog
    public static class TurretIOInputs{
        public double position = 0;
    }


    public default void updateInputs(TurretIOInputs inputs){
    }

    public default void applyPower(double percent){
    }

    public default void setPosition(Angle position){
    }
}
