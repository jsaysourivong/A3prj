package org.csc133.a3.gameObjects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import org.csc133.a3.GameWorld;
import org.csc133.a3.inerfaces.Steerable;

import java.util.ArrayList;

import static com.codename1.ui.CN.*;

/**
 * Helicopter should be faced initially zero degrees (due north) and with
 * an integer speed of zero. As the heading changes the line of the helicopter
 * must change to match the direction of the heading. The size of the
 * helicopter should be derived by the dimensions of the screen.
 * Should pass the coordinates of the center of the helipad into the
 * constructor. Must pass in the center of the helipad's coordinates.
 * When the left and right arrows are pressed the heading should
 * change by 15 degrees respectively.
 */
public class Helicopter extends Movable implements Steerable {

    private final static int MAX_SPEED = 10;
    private final static int MAX_WATER = 1000;
    private final static int SIZE = 30;

    private int color;

    public int fuel;
    public int water;

    public Helicopter(int locationX, int locationY) {
        super(locationX, locationY, SIZE, ColorUtil.YELLOW);
        super.setSpeed(0);
        super.setHeading(0);
        fuel = 30000;
        water = 0;
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin, Point screenOrigin){

        cn1ForwardPrimitiveTranslate(g,getDimension());

        int radius = 55;
        int centerX = SIZE / 2;
        int centerY = SIZE / 2;
        double angle = Math.toRadians(getHeading() + 90);
        double eX = centerX + radius * Math.cos(angle);
        double eY = centerY + radius * Math.sin(angle);

        g.fillArc(  0,
                    0,
                    getWidth(),
                    getHeight(),
                    0,
                    360);

        g.drawLine( centerX,
                    centerY,
                    (int)eX,
                    (int)eY);
        g.drawString(   "F  : " + fuel,
                        0,
                        -55);
        g.drawString(   "W : " + water,
                        0,
                        -80);
        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM));

        cn1ReversePrimitiveTranslate(g, getDimension());


    }

    public void tick(){
        move();
        consumeFuel();
    }


    public void move() {
        super.move();
    }

    public void updateLocation(double deltaX, double deltaY) {
        super.addLocation(deltaX,deltaY);
    }

    public void addSpeed(int addSpeed) {
        if (this.getSpeed() < MAX_SPEED ) {
            super.addSpeed(addSpeed);
        }
    }

    public void headLeft(int addHeading) {
        super.headLeft(addHeading);
    }

    public void headRight(int addHeading) {
        super.headRight(addHeading);
    }

    public void drink(River river) {
        if (river.canDrink(this)) {
            willDrink();
        }
    }

    public void willDrink() {
        if ((this.getSpeed() <= 0
                && this.getSpeed() > -2)
                && (water < MAX_WATER)) {
                    water += 50;
                    water += 50;
        }
    }

    public void fightFire(ArrayList<Fire> fires) {
        for (Fire fire : fires) {
            if ((this.getX() > fire.getX()
                    && this.getX() < fire.getX() + fire.size)
                    && (this.getY() > fire.getY()
                    && this.getY() < fire.getY() + fire.size)){
                if (water > 0) {
                    int totalWater = water;
                    water -= totalWater;
                    fire.size -= totalWater;
                }
            }
        }
    }

    public void consumeFuel() {
        double tempFuel;
        tempFuel = Math.pow(this.getSpeed(), 2) + 5;
        fuel -= tempFuel;
    }

    public void receiveFuel(Helipad helipad) {
        if (fuel < GameWorld.FUEL) {
            if (this.getX() > helipad.getX()
                    && this.getX() < helipad.getX() + helipad.getSize()
                    && this.getY() > helipad.getY()
                    && this.getY() < helipad.getY() + helipad.getSize()) {
                    fuel += 100;
            }
        }
    }

    @Override
    public void steer(int steer) {
        if(steer < 0){
            this.headRight(steer);
        } else {
            this.headLeft(steer);
        }
    }
}