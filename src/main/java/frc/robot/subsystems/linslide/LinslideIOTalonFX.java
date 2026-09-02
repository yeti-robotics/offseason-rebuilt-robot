package frc.robot.subsystems.linslide;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

public class LinslideIOTalonFX implements LinslideIO{
    public final TalonFX linSlideMotor;
    public MotionMagicTorqueCurrentFOC motionMagic = new MotionMagicTorqueCurrentFOC(0);

    public LinslideIOTalonFX() {
        linSlideMotor = new TalonFX(LinslideConfigs.MOTOR_ID, Constants.CAN_S1);
        linSlideMotor.getConfigurator().apply(LinslideConfigs.linslideTalonFXConfigurations);
        if(Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(linSlideMotor);
        }
    }

    @Override
    public void updateInputs(LinslideIOInputs inputs) {
        isStowed = Math.abs(LinslidePosition.STOWED-linSlideMotor.getPosition())>0.2;
        isDeployed = false;
        inputs.position = linSlideMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void setDeployed(Angle position) {
        linSlideMotor.setControl(motionMagic.withPosition(position));
    }

    @Override
    public void setStowed(Angle position) {
        linSlideMotor.setControl(motionMagic.withPosition(position));
    }


}
