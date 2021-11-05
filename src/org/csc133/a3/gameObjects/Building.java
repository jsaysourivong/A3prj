package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;
import java.util.Random;

public class Building extends Fixed{

    private Random r;
    private int value;
    private int thisTotalFireArea;
    private int buildingArea;
    private int maxDamage;
    private int largetValue;

    private double damage;

    public Building(int xPosition, int yPosition, int width, int height){
        super(xPosition,yPosition, width, height ,ColorUtil.rgb(255,0,0));
        this.value = 1000;
        setBuildingArea();
    }

    public void setFireInBuilding(Fire fire){
        double x = getRandomX();
        double y = getRandomY();
        fire.setLocation(x, y);
    }

    /**
     * 1) Building damage would be monitored and updated by checking the total
     * area of the fires
     * 2) dividing the total area of the building by the fire
     * area.
     * 3) Then updating damage attribute in building class and displaying
     * that number at the bottom right of the building.
     */
    public void setBuildingDamage(ArrayList<GameObject> gameObjects){
        int temp = 0;
        for(GameObject go : gameObjects){
            if(go instanceof Fire){
                Fire fire = (Fire)go;
                if(fireInBuilding(fire)){
                    temp += fire.getFireArea();
                }
            }
        }
        if(thisTotalFireArea == 0){
            thisTotalFireArea = temp;
        }
        else if (temp > thisTotalFireArea){
            thisTotalFireArea = temp;
        }
        damage =  100 * thisTotalFireArea / buildingArea;
        if(maxDamage == 0){
            maxDamage = (int) damage;
        } else if(damage > maxDamage){
            maxDamage = (int) damage;
        }
    }

    private boolean fireInBuilding(Fire fire){
        boolean b = fire.getX() > super.getX()
                && fire.getX() < (super.getX() + getWidth())
                && fire.getY() > super.getY()
                && fire.getY() < (super.getY() + getHeight());
        return b;
    }

    private void setBuildingArea(){
        this.buildingArea = getWidth() * getHeight();
    }

    private double getRandomX(){
        r = new Random();
        return (getLowerLeftInLocalSpace().getX() +
                (getLowerLeftInLocalSpace().getX() + getWidth()
                        - getLowerLeftInLocalSpace().getX()) * r.nextDouble());
    }
    private double getRandomY(){
        r = new Random();
        return (getLowerLeftInLocalSpace().getY() +
                (getLowerLeftInLocalSpace().getY() + getHeight()
                - getLowerLeftInLocalSpace().getY()) * r.nextDouble());
    }

    public void updateBuildingValue(ArrayList<GameObject> gameObjects){
        for(GameObject go : gameObjects){
            if(go instanceof Fire){
                Fire fire = (Fire)go;
                if(fireInBuilding(fire)){
                    value = (int) (value - (this.damage / 100000));
                    if(largetValue == 0){
                        largetValue = value;
                    } else if (value < largetValue){
                        largetValue = value;
                    }
                }
            }
        }
    }
    public int getBuildingArea(){
        return this.buildingArea;
    }

    public int getBuildingDamage(){
        return (int) damage;
    }

    public int getBuildingValue(){
        return this.largetValue;
    }

    @Override
    public void localDraw(Graphics g, Point originParent, Point originScreen) {
        cn1ForwardPrimitiveTranslate(g, getDimension());
        g.drawRect( 0,0,10,10);
        g.drawRect( 100,100,20,20);
        g.drawRect( 0,
                    0,
                    getWidth(),
                    getHeight());
        g.drawString(   "V : " + this.getBuildingValue(),
                        getWidth() + 20,
                        0);
        g.drawString(   "D : " + maxDamage + "%",
                        getWidth() + 20,
                        30);

        cn1ReversePrimitiveTranslate(g, getDimension());
        restoreOriginalTransforms(g);
    }
}
