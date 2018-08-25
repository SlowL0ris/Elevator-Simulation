package org.team2679.simulator;

import org.team2679.dashboard.Dashboard;
import org.team2679.motion.FeedForward;
import org.team2679.motion.PID;
import org.team2679.simulator.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FinishedRobot implements Controller {

    private static Elevator elevator = Simulation.elevator;
    private static PrintWriter out;
    private static PID pid;

    private static double goal = 2;
    private static  double tolerance = 0.0125;

    private static FeedForward feedForward = new FeedForward(goal, 14.61, 1, 1000);

    @Override
    public void onStart(double time) {
        File dump = new File("dump");
        try {
            out = new PrintWriter(dump);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("dump being saved to: " + dump.getAbsolutePath());

        pid = new PID(5, 0.1,  0);
    }

    @Override
    public void onLoop(double time) {
        // do stuff with the elevator
        // running in 1hz (1 milliseconds which is impossible in real situations) for 100 simulated seconds

        Dashboard.INSTANCE.putDouble("position", elevator.getPosition());

        FeedForward.Segment neededSegment = feedForward.getSegmentByTime(time);
        double kv = 1;
        double fix = 0;
        if(neededSegment != null) {
            fix = pid.update(time, elevator.getVelocity(), neededSegment.getVelocity()*kv);
        }
        else {
            fix = pid.update(time, elevator.getVelocity(), 0);
        }
        if(elevator.getPosition() > goal - tolerance){
            fix = 0;
        }
        elevator.set(fix);
        System.out.println("posotion: " + elevator.getPosition() + ",  speed: " + elevator.getVelocity());
        // write to file
        out.println(time + ", "  + elevator.getPosition() + ", "+  elevator.getVelocity());
        out.flush();
    }
}
