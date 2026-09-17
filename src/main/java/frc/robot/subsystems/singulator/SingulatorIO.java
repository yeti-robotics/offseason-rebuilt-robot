package frc.robot.subsystems.singulator;

import org.littletonrobotics.junction.AutoLog;

public interface SingulatorIO  {

    @AutoLog
    public static class SingulatorIOInputs {
        public double rollerSpeed = 0.0;
        public double supplyCurrent = 0.0;
    }

    public default void rollerSpin(double rps){}
    public default void updateInputs(SingulatorIOInputs inputs){}
    public default void usePower(double power){}
    public default void closeMotors(){}



}