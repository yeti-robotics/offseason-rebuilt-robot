package frc.robot.subsystems.miniindexer;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class MiniIndexerIOTalonFX implements MiniIndexerIO {
    public final TalonFX miniIndexer;
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private final MotionMagicVelocityTorqueCurrentFOC velocityRequest = new MotionMagicVelocityTorqueCurrentFOC(0);

    public MiniIndexerIOTalonFX() {
        miniIndexer = new TalonFX(MiniIndexerConfigs.MINI_INDEXER_ID, Constants.CAN_S1);
        miniIndexer.getConfigurator().apply(MiniIndexerConfigs.TALON_FX_CONFIGS);
        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(miniIndexer);
        }
    }

    @Override
    public void updateInputs(MiniIndexerIOInputs inputs) {
        inputs.indexerSpeed = miniIndexer.getVelocity().getValueAsDouble();
        inputs.motorTemp = miniIndexer.getDeviceTemp().getValueAsDouble();
        inputs.supplyCurrent = miniIndexer.getSupplyCurrent().getValueAsDouble();
    }

    @Override
    public void spinVelocity(double rps) {
        miniIndexer.setControl(velocityRequest.withVelocity(rps));
    }

    @Override
    public void runAtPower(double percent) {
        miniIndexer.setControl(dutyCycleOut.withOutput(percent));
    }

    @Override
    public void stopMotor() {
        miniIndexer.stopMotor();
    }
}
