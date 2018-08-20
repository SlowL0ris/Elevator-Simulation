package org.team2679.simulator;

import org.team2679.simulator.elevator.Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class Simulation {

    public static Elevator elevator;
    private final  static  double simulationTime = 50;
    private final  static  double simulationDelta = 0.02;

    public static void main(String args[]){
        elevator = new Elevator();
        run();
    }

    private static void run(){
        Robot.robotInit();

        double currentTime = 0;
        Method method = null;
        try {
            method = elevator.getClass().getDeclaredMethod("update", double.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        while (currentTime < simulationTime){
            try {
                Object r = method.invoke(elevator, simulationDelta);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Robot.autoPeriodic(currentTime);

            try {
            currentTime += simulationDelta;
                Thread.currentThread().sleep((long) (simulationDelta*1000));
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
        Robot.robotDisable();
    }
}
