package org.team2679.simulator.elevator;

public class Elevator {

    private double power = 0;
    private ElevatorEngine engine;

    public Elevator(){
        this.engine = new ElevatorEngine();
    }

    public void set(double power){
        this.power = power;
        this.engine.setVoltage(power*12);
    }

    public double getPosition(){
        return this.engine.getPosition();
    }

    public double getVelocity(){
        return this.engine.getVelocity();
    }

    private void update(double kDt){
        this.engine.simulateTime(kDt);
    }
}
