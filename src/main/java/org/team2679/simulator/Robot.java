package org.team2679.simulator;

import org.team2679.simulator.elevator.Elevator;

public class Robot implements Controller {

    Elevator elevator = Simulation.elevator;

    @Override
    public void onStart(double time) {

    }

    @Override
    public void onLoop(double time) {
        elevator.set(1);
        System.out.println(elevator.getVelocity());
    }
}
