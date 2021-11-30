package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameObjects.parts.Arc;
import org.csc133.a3.gameObjects.parts.Box;
import org.csc133.a3.gameObjects.parts.Rectangle;

import java.util.ArrayList;

public class Helicopter extends Movable {

    final static int BUBBLE_RADIUS = 125;
    final static int ENGINE_BLOCK_WIDTH = BUBBLE_RADIUS;
    final static int ENGINE_BLOCK_HEIGHT = ENGINE_BLOCK_WIDTH / 2;
    final static int BLADE_WIDTH = BUBBLE_RADIUS / 7;
    final static int BLADE_LENGTH = BUBBLE_RADIUS * 3;
    final static int TAIL_WIDTH = ENGINE_BLOCK_WIDTH / 4;
    final static int TAIl_HEIGHT  = (ENGINE_BLOCK_WIDTH * 2) - 25;
    final static int TRUE_TAIL_HEIGHT = (-ENGINE_BLOCK_HEIGHT/2 - (-TAIl_HEIGHT));

    private static final int FUEL = 30000;
    private final static int MAX_SPEED = 10;
    private final static int MAX_WATER = 1000;
    private final static int SIZE = 30;

    public int fuel;
    public int water;

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloBubble extends Arc {

        public HeloBubble() {
            super(  BUBBLE_RADIUS,
                    BUBBLE_RADIUS,
                    20,
                    (float) (BUBBLE_RADIUS * .65),
                    1,
                    1,
                    135,
                    275,
                    0,
                    ColorUtil.MAGENTA);
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloEngineBlock extends Rectangle {

        public HeloEngineBlock(){
            super(  ENGINE_BLOCK_WIDTH,
                    ENGINE_BLOCK_HEIGHT,
                    20,
                    0,
                    1,
                    1,
                    0,
                    ColorUtil.MAGENTA);
        }
    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloBlade extends Rectangle{
        private final static int MIN_ROTATIONAL_SPEED = 0;
        private final static int MAX_Rotational_SPEED = 100;
        private int rotationalSpeed= 0;
        private int incrementer = 2;
        public HeloBlade(){
            super(  BLADE_WIDTH,
                    BLADE_LENGTH,
                    20,
                    0,
                    1,
                    1,
                    45, ColorUtil.GRAY);
        }

        public void updateLocalTransforms(){
                if(rotationalSpeed >= MIN_ROTATIONAL_SPEED
                && rotationalSpeed < MAX_Rotational_SPEED){
                    rotationalSpeed += incrementer;
                }
            this.rotate(rotationalSpeed);
        }

        public int getRotationalSpeed(){
            return rotationalSpeed;
        }

    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloBladeShaft extends Arc {

        public HeloBladeShaft(){
            super(  2 * BLADE_WIDTH/3,
                    2 * BLADE_WIDTH/3,
                    25,
                    0,
                    1,1,
                    0,
                    360,0,
                    ColorUtil.GRAY);
        }
    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloSkid extends Rectangle {

        public HeloSkid(int tx, int ty){
            super(  ENGINE_BLOCK_WIDTH / 7,
                    ENGINE_BLOCK_HEIGHT * 3,
                    tx,
                    ty,
                    1,1,
                    0,
                    ColorUtil.MAGENTA);
        }
    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloTail extends GameObject {

        public HeloTail(){
            super(  0,
                    0,
                    TAIL_WIDTH,
                    TAIl_HEIGHT,
                    ColorUtil.MAGENTA);

        }

        @Override
        public void localDraw(  Graphics g,
                                Point originParent,
                                Point originScreen) {

        int halfTailHeight = (-TAIl_HEIGHT/ 2 - 20);

        g.drawLine(10,-ENGINE_BLOCK_HEIGHT/2,12,-TAIl_HEIGHT);
        g.drawLine(40,-ENGINE_BLOCK_HEIGHT/2, 38, -TAIl_HEIGHT);

        g.drawLine(10, -ENGINE_BLOCK_HEIGHT/2, 35, halfTailHeight);
        g.drawLine(40, -ENGINE_BLOCK_HEIGHT/2, 10, halfTailHeight);

        g.drawLine(10, halfTailHeight * 2 + 40, 35, halfTailHeight);
        g.drawLine(40, halfTailHeight * 2 + 40, 10, halfTailHeight);

        }
    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloTailShaft extends Box {

        public HeloTailShaft( ) {
            super(  5, TRUE_TAIL_HEIGHT,
                    25,-ENGINE_BLOCK_HEIGHT * 2 - 5,
                    1,1,
                    0,
                    ColorUtil.GRAY
                    );
        }
    }

    //`````````````````````````````````````````````````````````````````````````

    private static class HeloTailEnd extends Box{

        public HeloTailEnd(){
            super(  30,
                    25, -TRUE_TAIL_HEIGHT - ENGINE_BLOCK_HEIGHT + 15,
                    1,1,
                    0,
                    ColorUtil.GRAY);

        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                        Helicopter State Pattern                       //

    HeloState heloState;

    private void changeState(HeloState heloState){
        this.heloState = heloState;
    }

    private abstract class HeloState {

        protected Helicopter getHelo() {
            return Helicopter.this;
        }

        public void startOrStopEngine() {
            if(this instanceof Off){

            }
        }

        public void accelerate() {}
        // It should only be possible for helicopter to land in the off state
        //
        public boolean hasLandedAt() {
            return false;
        }
        // Make it possible for helicopter to move only if in ready state
        //
        public void updateLocalTransforms(){}
    }

    //`````````````````````````````````````````````````````````````````````````
    private class Off extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
        }

        public void updateLocalTransforms(){

        }

        @Override
        public boolean hasLandedAt(){
            //check other requirements
            return true; //some boolean expression for if helicopter is landed
        }
    }
    //`````````````````````````````````````````````````````````````````````````
    private class Starting extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Stopping());
        }

        public void updateLocalTransforms(){
            if(heloBlade.getRotationalSpeed() < 7){
                consumeFuel();
                heloBlade.updateLocalTransforms();
            } else {
                getHelo().changeState(new Ready());
            }
        }
    }
    //`````````````````````````````````````````````````````````````````````````
    private class Stopping extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
        }

        public void updateLocalTransforms(){
            if(heloBlade.getRotationalSpeed() == 0){
                getHelo().changeState(new Off());
            } else {
                heloBlade.updateLocalTransforms();
            }
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private class Ready extends HeloState{
        @Override
        public void startOrStopEngine() {
            if (helicopterLand())
                getHelo().changeState(new Stopping());
        }

        public void updateLocalTransforms(){
            move();
            accelerate();
            consumeFuel();
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ArrayList<GameObject> heloParts;
    private HeloBlade heloBlade;
    private Helipad helipad;
    public Helicopter(Point lz, Helipad helipad){
        super(lz.getX(),lz.getY(),10,ColorUtil.MAGENTA);
        super.setSpeed(0);
        super.setHeading(0);
        this.helipad = helipad;
        fuel = 30000;
        water = 0;
        heloState = new Off();
        heloParts = new ArrayList<>();
        heloParts.add(new HeloBubble());
        heloParts.add(new HeloEngineBlock());
        heloBlade = new HeloBlade();
        heloParts.add(heloBlade);
        heloParts.add(new HeloBladeShaft());
        heloParts.add(new HeloSkid(115,50));
        heloParts.add(new HeloSkid(-70,50));
        heloParts.add(new HeloTail());
        heloParts.add(new HeloTailShaft());
        heloParts.add(new HeloTailEnd());
    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin){
        for(GameObject go : heloParts)
            go.draw(g, parentOrigin, screenOrigin);
    }

    public void headLeft(int heading){
        this.rotate(heading);
    }

    public void headRight(int heading){
        this.rotate(heading);
    }

    public void updateLocalTransforms(){
        heloState.updateLocalTransforms();
    }

    public void startOrStopEngine(){
        heloState.startOrStopEngine();
    }

    public void accelerate(){
        heloState.accelerate();
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
        if (fuel < FUEL) {
            if (this.getX() > helipad.getX()
                    && this.getX() < helipad.getX() + helipad.getSize()
                    && this.getY() > helipad.getY()
                    && this.getY() < helipad.getY() + helipad.getSize()) {
                fuel += 100;
            }
        }
    }

    public boolean helicopterLand() {
        return (this.getX() > helipad.getX()
                && this.getX() < helipad.getX() + helipad.getSize()
                && this.getY() > helipad.getY()
                && this.getY() < helipad.getY() + helipad.getSize());
    }

}

