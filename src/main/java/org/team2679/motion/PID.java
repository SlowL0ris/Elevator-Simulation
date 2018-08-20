package org.team2679.motion;

public class PID {

    private double kp;
    private double ki;
    private double kd;

    private double integral = 0;

    private double lastTime;
    private double lastError;

    public PID(double kp, double ki, double kd){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public double update(double time, double currentPoint, double setPoint){
        double elapseTime = time - lastTime;
        double error = setPoint-currentPoint;

        double p = error;
        double d = 0;
        if(elapseTime != 0) {
            d = (error - lastError) / elapseTime;
        }

        lastError = error;
        lastTime = time;

        return kp*p + ki*integral + kd*d;
    }
}
