package org.team2679.simulator.simulations;

import org.team2679.simulator.simulations.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class simulation {

    public static Elevator elevator;
    private final  static  double simulationTime = 50;
    private final  static  double simulationDelta = 0.02;
    private static Thread thread;

    public static void main(String args[]){
        elevator = new Elevator();
        thread = new Thread(programRunnable);
        thread.start();
    }

    private static Runnable programRunnable = new Runnable() {
        @Override
        public void run() {
            File VelocityDump = new File("VelocityDump");
            File PositionDump = new File("PositionDump");
            PrintWriter VelocityOut = null;
            PrintWriter PositionOut = null;
            try {
                VelocityOut = new PrintWriter(VelocityDump);
                PositionOut = new PrintWriter(PositionDump);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            double currentTime = 0;
            Method method = null;
            try {
                method = elevator.getClass().getDeclaredMethod("update", double.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            method.setAccessible(true);
            System.out.println("velocity dump being saved to: " + VelocityDump.getAbsolutePath());
            System.out.println("Position dump being saved to: " + PositionDump.getAbsolutePath());
            while (currentTime < simulationTime){
                try {
                    Object r = method.invoke(elevator, simulationDelta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                robot.autoPeriodic();
                // write to file
                VelocityOut.println(elevator.getVelocity() + ", " + currentTime);
                PositionOut.println(elevator.getPosition() + ", " + currentTime);

                currentTime += simulationDelta;
                VelocityOut.flush();
                PositionOut.flush();
                try {
                    thread.sleep((long) (simulationDelta*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            VelocityOut.close();
            PositionOut.close();
        }
    };
}
