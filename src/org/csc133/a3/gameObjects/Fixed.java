package org.csc133.a3.gameObjects;

/**
 *  This class should contain immutable objects. Does not contain
 *  attributes.
 */

public abstract class Fixed extends GameObject {

    public Fixed(double x, double y, int size, int color) {
        super(x, y, size, color);
    }

    public Fixed(double x, double y, int width, int height, int color) {
        super(x, y, width, height, color);
    }


    @Override
    public void updateLocation(double deltaX, double deltaY){}

    /*
    @Override
    public void setLocation(double setX, double setY){}

    /**
     *
     * @param x this is the x coordinate of the object.
     *          It should not be modified

    @Override
    public void setX(double x){
    }

    /**
     *
     * @param y this is the y coordinate of the object.
     *          It should not be modified

    @Override
    public void setY(double y){}

     */

}
