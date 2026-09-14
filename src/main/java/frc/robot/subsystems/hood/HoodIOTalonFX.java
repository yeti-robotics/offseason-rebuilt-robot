package frc.robot.subsystems.hood;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class HoodIOTalonFX implements HoodIO {
    private TalonFX hoodMotor;
    private PositionVoltage positionVoltage;

    public HoodIOTalonFX() {
        hoodMotor = new TalonFX(HoodConfigs.MOTOR_ID, Constants.CAN_S3);
        hoodMotor.getConfigurator().apply(HoodConfigs.HOOD_MOTOR_CONFIGS);
        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(hoodMotor);
        }
        positionVoltage = new PositionVoltage(0);
    }

    @Override
    public void updateInputs(HoodIOInputs inputs) {
        inputs.position = hoodMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void setPosition(double position) {
        hoodMotor.setControl(positionVoltage.withPosition(position));
    }
}
