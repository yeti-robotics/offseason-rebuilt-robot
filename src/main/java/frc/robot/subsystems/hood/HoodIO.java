package frc.robot.subsystems.hood;

public interface HoodIO {
    public static class HoodIOInputs{
        public double position = 0;
    }

    public default void updateInputs(HoodIOInputs inputs) {}
    public default void setPosition(double position) {}
}
