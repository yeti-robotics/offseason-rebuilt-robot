package frc.robot.subsystems.miniindexer;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class MiniIndexer extends SubsystemBase {

    private MiniIndexerIO io;
    private MiniIndexerIOInputsAutoLogged inputs = new MiniIndexerIOInputsAutoLogged();

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("MiniIndexer", inputs);
    }

    public MiniIndexer(MiniIndexerIO io) {
        this.io = io;
    }

    public Command spinMiniIndexerRPS(double rps) {
        return runEnd(() -> io.spinVelocity(rps), () -> io.stopMotor());
    }

    public Command spinMiniIndexerRaw(double power) {
        return runEnd(() -> io.runAtPower(power), () -> io.stopMotor());
    }

    public Command applyPower(double percent) {
        return runEnd(() -> io.runAtPower(percent), () -> io.runAtPower(0));
    }
}
