package org.team2679.simulator;

import org.team2679.simulator.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Robot {

    private static Elevator elevator = Simulation.elevator;
    private static PrintWriter out;

    public static void robotInit(){
        File dump = new File("dump");
        try {
            out = new PrintWriter(dump);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("dump being saved to: " + dump.getAbsolutePath());
    }

    private final static double kp = 1;
    private final static double kd = 1;

    private static double error = 0;
    private static double lastError = 0;
    private static double lastTime = 0;
    private static double goal = 2;

    public static void autoPeriodic(double time){
        // do stuff with the elevator
        // running in 1hz (1 milliseconds which is impossible in real situations) for 100 simulated seconds
        error = goal - elevator.getPosition();
        double elapseTime = time - lastTime;

        double p = error;
        double d = 0;
        if(elapseTime != 0) {
            d = (error - lastError) / elapseTime;
        }

        lastError = error;
        lastTime = time;

        elevator.set(kp*p + kd*d);

        // write to file
        out.println(time + ", " + elevator.getPosition() + ", " + kp*p + ", " + kd*d);
        out.flush();
    }

    public static void robotDisable(){
        out.close();
    }
}
