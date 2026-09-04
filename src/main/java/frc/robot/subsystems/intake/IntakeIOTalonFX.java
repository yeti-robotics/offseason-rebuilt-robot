package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class IntakeIOTalonFX implements IntakeIO {
    private final TalonFX rightIntakeMotor;
    private final TalonFX leftIntakeMotor;

    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private final VoltageOut voltageRequest = new VoltageOut(0);

    public IntakeIOTalonFX() {
        rightIntakeMotor = new TalonFX(IntakeConfigs.RIGHT_INTAKE_MOTOR_ID, Constants.CAN_S1);
        leftIntakeMotor = new TalonFX(IntakeConfigs.LEFT_INTAKE_MOTOR_ID, Constants.CAN_S1);
        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(rightIntakeMotor);
            PhysicsSim.getInstance().addTalonFX(leftIntakeMotor);
        }

        rightIntakeMotor.getConfigurator().apply(IntakeConfigs.RIGHT_TALONFX_CONFIGS);
        leftIntakeMotor.getConfigurator().apply(IntakeConfigs.RIGHT_TALONFX_CONFIGS);
        leftIntakeMotor.setControl(new Follower(IntakeConfigs.RIGHT_INTAKE_MOTOR_ID, MotorAlignmentValue.Opposed));
    }
    @Override
    public void updateInputs(IntakeIO.IntakeIOInputs inputs){
        inputs.primaryMotorRPM = rightIntakeMotor.getVelocity().getValueAsDouble();
        inputs.primaryMotorVoltage = rightIntakeMotor.getMotorVoltage().getValueAsDouble();
        inputs.secondaryMotorRPM = leftIntakeMotor.getVelocity().getValueAsDouble();
        inputs.secondaryMotorVoltage = leftIntakeMotor.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void setIntakeMotor(double volts){
        rightIntakeMotor.setControl(voltageRequest.withOutput(volts));
    }

    @Override
    public void applyPower(double percent){
        rightIntakeMotor.setControl(dutyCycleOut.withOutput(percent));
    }
}
