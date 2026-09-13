package frc.robot.subsystems.singulator;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class SingulatorIOTalonFX implements SingulatorIO {
    public final TalonFX indexerRoller;
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

public SingulatorIOTalonFX() {
    indexerRoller = new TalonFX(SingulatorConfigs.ROLLER_ID, Constants.CAN_S1);
    if (Robot.isSimulation()) {
        PhysicsSim.getInstance().addTalonFX(indexerRoller);
    }
}
@Override
public void updateInputs(SingulatorIOInputs inputs) {
    inputs.rollerSpeed = indexerRoller.getVelocity().getValueAsDouble();
    inputs.supplyCurrent = indexerRoller.getDeviceTemp().getValueAsDouble();
}
@Override
    public void usePower(double power) {
    indexerRoller.setControl(dutyCycleOut.withOutput(power));
}
@Override
    public void closeMotors() {
    indexerRoller.disable();
}
}
