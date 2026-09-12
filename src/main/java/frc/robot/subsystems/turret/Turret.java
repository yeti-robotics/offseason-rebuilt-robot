package frc.robot.subsystems.turret;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Turret extends SubsystemBase {
    private final TurretIO io;
    private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();

    public Turret(TurretIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Turret", inputs);
    }

    public Command setPosition(Angle position) {
        return runOnce(() -> io.setPosition(position));
    }

    public Command applyPower(double percent) {
        return runEnd(() -> io.applyPower(percent), () -> io.applyPower(0));
    }
}
