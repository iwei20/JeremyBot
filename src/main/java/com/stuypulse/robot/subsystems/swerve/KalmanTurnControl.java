package com.stuypulse.robot.subsystems.swerve;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class KalmanTurnControl extends SubsystemBase {

    private final LinearSystem<N2, N1, N1> model;
    private final KalmanFilter<N2, N1, N1> observer;
    private final LinearQuadraticRegulator<N2, N1, N1> controller;
    private final LinearSystemLoop<N2, N1, N1> loop;
  
    public KalmanTurnControl(double kv, double ka) {

        model = LinearSystemId.identifyPositionSystem(kv, ka); 

        observer = new KalmanFilter<>(
            Nat.N2(),
            Nat.N1(),
            model,
            VecBuilder.fill(3.0, 3.0), // How accurate we think our model is
            VecBuilder.fill(0.01), // How accurate we think our encoder data is
            0.020
        );

        controller = new LinearQuadraticRegulator<>(
            model,
            VecBuilder.fill(4.0, 4.0), // qelms. Velocity error tolerance, in radians per second. Decrease
            // this to more heavily penalize state excursion, or make the controller behave
            // more
            // aggressively.
            VecBuilder.fill(12.0), // relms. Control effort (voltage) tolerance. Decrease this to more
            // heavily penalize control effort, or make the controller less aggressive. 12
            // is a good
            // starting point because that is the (approximate) maximum voltage of a
            // battery.
            0.020);

        loop = new LinearSystemLoop<>(
            model,
            controller,
            observer,
            12.0,
            0.020
        );

    }

    public void setAngle(Rotation2d target) {
        loop.setNextR(target.getRadians());
    }

    public abstract Rotation2d getAngle();

    protected abstract void setVoltage(double voltage); 

    protected abstract void reset();

    @Override
    public void periodic() {
        loop.correct(VecBuilder.fill(getAngle().getRadians()));
        loop.predict(0.020);

        double outputVolts = loop.getU(0);

        setVoltage(outputVolts);
    }
}
