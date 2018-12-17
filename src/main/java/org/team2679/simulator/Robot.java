package org.team2679.simulator;

import org.team2679.simulator.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Robot implements Controller {

    Elevator elevator = Simulation.elevator;
    private static PrintWriter out;

    @Override
    public void onStart(double time) {
		/**
		this code will run when the elevator starts
		**/
		// ignore this part, it's opening a dump file for our graph
        File dump = new File("dump");
        try {
            out = new PrintWriter(dump);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("dump being saved to: " + dump.getAbsolutePath());
    }

    static double lastError = 0;
    static double lastTime = 0;
    static double integral = 0;

    @Override
    public void onLoop(double time) {
        /**
			This is were our control code goes
			use elevator.getPosition() to get the fake encoder data
			and elevator.set(double value) to chaneg the elevator power
			the elevator.set() values go from -1 to 1
		**/
		
		// you can ignore this part, here we write to the graph
		// values are comma seperated
        out.println(time + ", "  + elevator.getPosition() + ", "+  elevator.getVelocity());
        out.flush();
    }
}
