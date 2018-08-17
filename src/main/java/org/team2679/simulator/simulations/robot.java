package org.team2679.simulator.simulations;

import org.team2679.simulator.simulations.elevator.Elevator;

public class robot {

    private static Elevator elevator = simulation.elevator;

    public static void autoPeriodic(){
        // do stuff with the elevator
        // running in 1hz (1 milliseconds which is impossible in real situations) for 100 simulated seconds
        if(elevator.getPosition() > 5){
            elevator.set(-1);
        }
        else{
            elevator.set(1);
        }
    }
}
