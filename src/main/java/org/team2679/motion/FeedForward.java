package org.team2679.motion;

public class FeedForward {

    private int numberOfPoints;

    private double maxVelocity, maxAcceleration = 0;

    private double pathLength;

    private Segment[] segments;

    public FeedForward(double pathLength, double maxVelocity, double maxAcceleration, int numberOfPoints){
        this.maxAcceleration = maxAcceleration;
        this.maxVelocity = maxVelocity;
        this.numberOfPoints = numberOfPoints;

        this.segments = new Segment[numberOfPoints + 2];
        this.pathLength = pathLength;

        this.calculate();
    }

    private void calculate(){
        // create all segments, with one in the beginning and in the end to make math easier
        segments[0] = new Segment(0,0,0);
        segments[numberOfPoints - 1] = new Segment(0,0,0);
        for (int i = 0; i < segments.length - 1; i++) {
            segments[i + 1] = new Segment(pathLength/numberOfPoints*i,  0, 0);
        }

        // first pass on path to adjust speed according to acceleration
        for(int i = 1; i < numberOfPoints; i++){
            double velocity = Math.min(this.maxAcceleration, calculateVelocityFromAcceleration(segments[i-1].velocity, this.segments[i].x - this.segments[i-1].x, this.maxAcceleration));
            segments[i].velocity = velocity;
        }
        // second pass on path to adjust speed according to acceleration
        for (int i = segments.length - 2; i >= 1; i--) {
            double velocity = Math.min(Math.min(this.maxAcceleration, calculateVelocityFromAcceleration(
                    segments[i+1].velocity, this.segments[i].x - this.segments[i+1].x, -this.maxAcceleration)), segments[i].velocity);
            segments[i].velocity = velocity;
        }

        double sum = 0;
        for (int i = 2; i < segments.length - 1; i++) {
            sum += (2*(segments[i].x-segments[i - 1].x)/(segments[i-1].velocity + segments[i].velocity));
            segments[i].time = segments[i-1].time + 2*(segments[i].x-segments[i - 1].x)/(segments[i-1].velocity + segments[i].velocity);
        }
    }

    private static double  calculateVelocityFromAcceleration(double lastVelocity, double deltaX, double maxAcceleration){
        return Math.sqrt(lastVelocity*lastVelocity + 2*maxAcceleration*deltaX);
    }

    public static class Segment {

        private double x;
        private double velocity = 0;
        private double time = 0;

        public Segment(double x , double velocity, double time){
            this.x = x;
            this.velocity = velocity;
            this.time = time;
        }

        public double getX(){
            return this.x;
        }
        public double getVelocity(){
            return this.velocity;
        }
        public double getTime(){
            return this.time;
        }
    }

    public Segment getSegmentByTime(double time){
        for (Segment segment : segments) {
            if(segment.getTime() >= time){
                return segment;
            }
        }
        return null;
    }
}
