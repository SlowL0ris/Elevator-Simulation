package org.team2679.simulator.simulations.elevator;

public class ElevatorEngine {

    private double kStallTorque = 2.402;
    private double kStallCurrent = 126.145;
    private double kFreeSpeed = 5015.562;
    private double kFreeCurrent = 1.170;

    private double kMass = 20.0;

    private int kNumMotors = 2;

    private double kResistance = 12/kStallCurrent;

    private double Kv = ((kFreeSpeed / 60 * 2.0 * Math.PI) / (12 - kResistance*kFreeCurrent));

    private double Kt = (kNumMotors*kStallTorque)/kStallCurrent;

    private double kG = 72/12*22/16;

    private double kr = 0.25*0.0254*22.0*Math.PI/2;

    private double kDt = 0.01;

    private double velocity = 0;
    private double position = 0;
    private double voltage = 0;

    private double getAcceleration(double voltage){
        return -Kt*kG*kG / (Kv * kResistance * kr * kr * kMass) * velocity + kG*Kt / (kResistance*kr*kMass) * voltage;
    }

    public void simulateTime(double time){
        double deltaTime = 0.0001;
        double currentTime = 0;
        if(voltage > 12){
            voltage = 12;
        }
        else if (voltage < -12){
            voltage = -12;
        }
        while (currentTime < time){
            position += velocity*deltaTime;
            velocity += getAcceleration(voltage)*deltaTime;
            currentTime+=deltaTime;
        }
    }

    public void setVoltage(double voltage){
        this.voltage = voltage;
    }

    public double getkDt(){
        return this.kDt;
    }

    public double getPosition(){
        return this.position;
    }

    public double getVelocity(){
        return this.velocity;
    }
}
