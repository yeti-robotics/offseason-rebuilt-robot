package frc.robot.subsystems.linslide;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

public class Linslide extends SubsystemBase {
    private LinslideIO io;
    private LinslideIOInputsAutoLogged inputs = new LinslideIOInputsAutoLogged();

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Linslide", inputs);
    }

    public Command deploy() {
        return runOnce(() -> io.setDeployed(LinslidePosition.DEPLOYED.getPosition()));
    }

    public Command stow() {
        return runOnce(() -> io.setStowed(LinslidePosition.STOWED.getPosition()));
    }

}
