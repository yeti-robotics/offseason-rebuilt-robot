package frc.robot.subsystems.linslide;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;

public enum LinslidePosition {
    STOWED(0),
    DEPLOYED(9.517090);

    private Angle position;

    LinslidePosition(double position) {
        this.position = Units.Rotations.of(position);
    }

    public Angle getPosition() {
        return this.position;
    }
}
