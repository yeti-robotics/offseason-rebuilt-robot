package frc.robot.subsystems.shooter;//*nithya was here //*


import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;


import static org.littletonrobotics.junction.Logger.processInputs;

public class Shooter extends SubsystemBase {
    private ShooterIO io;
    public ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    public Shooter(ShooterIO io) {
        this.io = io;
    }

    public Command shoot(double velocity) {
        return runOnce(() -> this.targetSpeed = velocity);
    }

    public AngularVelocity getVelocity() {
        return Units.RotationsPerSecond.of(inputs.topMotorRPM);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);


    }

}
