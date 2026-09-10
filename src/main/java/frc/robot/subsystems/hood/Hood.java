package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class Hood extends SubsystemBase {
    private HoodIO io;
    private LoggableIOInputsAutoLogged inputs;

    public Hood(HoodIO io) {
        this.io = io;
        this.inputs = new LoggableIOInputsAutoLogged();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Hood", inputs);
    }

    public Command setPosition(double position) {
        return(runOnce(()-> io.setPosition(position)));
    }

}
