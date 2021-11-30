package org.csc133.a3.views;

import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameObjects.GameObject;
import org.csc133.a3.gameObjects.Helicopter;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;


public class MapView extends Container {
    private GameWorld gw;
    Helicopter helicopter;

    public MapView(GameWorld gw){
        this.gw = gw;
/*        init();*/
        gw.setDimension(new Dimension (this.getWidth(), this.getHeight()));
    }

/*    public void init(){
        helicopter = new Helicopter(new Point(0,0));
    }*/

    @Override
    public void laidOut(){
        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
        gw.init();
    }

    @Deprecated
    public void displayTransform(Graphics g){
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);

        //move the drawing coordinates back
        //
        gXform.translate(getAbsoluteX(),getAbsoluteY());

        //apply display mapping
        //
        gXform.translate(0, getHeight());
        gXform.scale(1, -1);

        //move the drawing coordinates as part of the "local origin"
        //transformation
        //
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    // set up the world to ND transform
    //
    private Transform buildWorldToNDXform(float winWidth, float winHeight,
                                          float winLeft, float winBottom){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale((1/winWidth), (1/winHeight));
        tmpXform.translate(-winLeft,-winBottom);
        return tmpXform;
    }

    // set up the ND to screen transform
    //
    private Transform buildNDToDisplayXform(float displayWidth,
                                            float displayHeight){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0, displayHeight);
        tmpXform.scale(displayWidth, -displayHeight);
        return tmpXform;
    }

    private void setupVTM(Graphics g){
        Transform worldToND, ndToDisplay, theVTM;
        float winLeft, winRight,  winTop, winBottom;

        winLeft = winBottom = 0;
        winRight = this.getWidth();
        winTop = this.getHeight();

        float winHeight = winTop - winBottom;
        float winWidth = winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBottom);
        ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(0,-getY());
        gXform.translate(getAbsoluteX(), getAbsoluteY());
        gXform.concatenate(theVTM);
        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
        g.setTransform(gXform);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        setupVTM(g);

        Point originScreen = new Point(getAbsoluteX(),getAbsoluteY());
        Point originParent = new Point(getX(), getY());

        for(GameObject go : gw.getGameObjects()){;
            go.draw(g,originParent, originScreen);
        }
        g.resetAffine();
        //updateLocalTransforms();

    }

    /*
    public void updateLocalTransforms(){
        helicopter.updateLocalTransforms();
    }

   public void accelerate(){

    }

    public void break(){

    }
    public void left(){

    }

    public void right(){

    }

    public void startAndStopEngine(){

    }

    public void accelerate() {

    }*/
}
