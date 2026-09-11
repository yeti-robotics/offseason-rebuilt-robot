package frc.robot.subsystems.turret;


import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

import static edu.wpi.first.units.Units.Degrees;

public class Turret extends SubsystemBase {
    private final TurretIO io;
    private final TurretIOTalonFXAutoLogged inputs = new TurretIOTalonFXAutoLogged();
    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Turret", inputs);
    }

    public Turret(TurretIO io){
        this.io = io;
    }

    public Command setPosition(Angle position){
        return runEnd(() -> io.setPosition(position), () -> io.setPosition(Degrees.of(0)));
    }

    public Command applyPower(double percent) {
        return runEnd(() -> io.applyPower(percent), () -> io.applyPower(0));
    }


}
