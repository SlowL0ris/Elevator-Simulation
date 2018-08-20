package org.team2679.simulator;

import org.team2679.motion.PID;
import org.team2679.simulator.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Robot {

    private static Elevator elevator = Simulation.elevator;
    private static PrintWriter out;
    private static PID pid;

    public static void robotInit(){
        File dump = new File("dump");
        try {
            out = new PrintWriter(dump);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("dump being saved to: " + dump.getAbsolutePath());

        pid = new PID(4, 0, 1);
    }

    private static double goal = 2 ;

    public static void autoPeriodic(double time){
        // do stuff with the elevator
        // running in 1hz (1 milliseconds which is impossible in real situations) for 100 simulated seconds
        double fix = pid.update(time, elevator.getPosition(), goal);

        elevator.set(fix);

        // write to file
        out.println(time + ", " + elevator.getPosition() + ", " + fix + ", " + fix);
        out.flush();
    }

    public static void robotDisable(){
        out.close();
    }
}
