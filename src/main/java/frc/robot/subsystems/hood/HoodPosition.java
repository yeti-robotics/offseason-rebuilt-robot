package frc.robot.subsystems.hood;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;

public enum HoodPosition {
    BOTTOM(0),
    MID(5),
    TOP(10);

    private Angle position;

    HoodPosition(double position) {
        this.position = Units.Rotations.of(position);
    }

    public Angle getAngle() {
        return this.position;
    }
}
