package frc.robot.subsystems.linslide;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class LinslideIOTalonFX implements LinslideIO {
    public final TalonFX linslideMotor;
    public final TalonFX secondaryLinslideMotor;
    public MotionMagicTorqueCurrentFOC motionMagic = new MotionMagicTorqueCurrentFOC(0);
    public DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

    public LinslideIOTalonFX() {
        linslideMotor = new TalonFX(LinslideConfigs.MOTOR_ID, Constants.CAN_S1);
        secondaryLinslideMotor = new TalonFX(LinslideConfigs.SECONDARY_MOTOR_ID, Constants.CAN_S1);

        linslideMotor.getConfigurator().apply(LinslideConfigs.linslideTalonFXConfigurations);
        secondaryLinslideMotor.setControl(new Follower(LinslideConfigs.MOTOR_ID, true));

        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(linslideMotor);
        }
    }

    @Override
    public void updateInputs(LinslideIOInputs inputs) {
        inputs.isStowed = linslideMotor.getPosition().getValueAsDouble() <= 0.2;
        inputs.isDeployed = linslideMotor.getPosition().getValueAsDouble() >= 9.8;
        inputs.position = linslideMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void applyPower(double percent) {
        linslideMotor.setControl(dutyCycleOut.withOutput(percent));
    }

    @Override
    public void setDeployed(Angle position) {
        linslideMotor.setControl(motionMagic.withPosition(position));
    }

    @Override
    public void setStowed(Angle position) {
        linslideMotor.setControl(motionMagic.withPosition(position));
    }

}
