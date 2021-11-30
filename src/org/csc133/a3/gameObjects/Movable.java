package org.csc133.a3.gameObjects;

public abstract class Movable extends GameObject{

    private int speed;
    private int heading;

    public Movable(double x, double y, int size, int color) {
        super(x, y, size, color);
    }

    public Movable(double x, double y, int width, int height, int color) {
        super(x, y, width, height, color);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHeading(int heading) { this.heading = heading; }

    public int getSpeed(){
        return speed;
    }

    public int getHeading(){ return heading; }

    public void headLeft(int addHeading){
        heading += addHeading;
    }

    public void headRight(int addHeading){
        heading += addHeading;
    }

    public void addSpeed(int addSpeed){
        speed += addSpeed;
    }


    public void move(){
        double angle;
        double deltaX;
        double deltaY;

        angle = 90 + heading;
        deltaX = -Math.cos(Math.toRadians(angle)) * speed;
        deltaY = -Math.sin(Math.toRadians(angle)) * speed;
        translate(deltaX, deltaY);
        //addLocation(deltaX, deltaY);
    }

}
