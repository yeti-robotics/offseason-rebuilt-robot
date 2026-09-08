package frc.robot.subsystems.singulator;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class IndexerIOTalonFX implements SingulatorIO {
    public final TalonFX indexerRoller;
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

public IndexerIOTalonFX () {
    indexerRoller = new TalonFX(SingulatorConfigs.BETA_ROLLER_ID, Constants.CAN_S1);
    if (Robot.isSimulation()) {
        PhysicsSim.getInstance().addTalonFX(indexerRoller);
    }
}
@override

}
