package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;

/**
 * Represents water source for helicopter to obtain more water from.
 * This river should take the form of a "simple open blue rectangle".
 * This object should provide the helicopter with a resource it can
 * obtain by being over the river and traveling at a speed of less
 * than 2 or slower at a rate of 100 at a tick.
 */
public class River extends Fixed {

    Point lowerLeftInLocalSpace;

    public River(Dimension mapSize){
        super(  0,
                0,
                mapSize.getWidth(),
                mapSize.getHeight() / 8,
                ColorUtil.BLUE);
        this.mapSize = mapSize;
        this.location = new Point2D(0, mapSize.getHeight());
        this.dimension = new Dimension( mapSize.getWidth(),
                                  mapSize.getHeight() / 8);
        translation.translate( mapSize.getWidth() / 2,
                               mapSize.getHeight() / 2 + 300);
    }

    public boolean canDrink(Helicopter helicopter) {
        return (helicopter.getX() > this.getX()
                && helicopter.getX() < mapSize.getWidth()
                && helicopter.getY() > this.getY()
                && helicopter.getY() < this.getY() + this.getSize());
    }
    @Override
    public void localDraw(Graphics g, Point originContainer, Point originScreen) {

        cn1ForwardPrimitiveTranslate(g,getDimension());

        g.drawRect( 0,0,10,10);
        g.drawRect( 100,100,20,20);
        g.drawRect( 0,
                    0,
                    dimension.getWidth(),
                    dimension.getHeight());
        cn1ReversePrimitiveTranslate(g,getDimension());
        restoreOriginalTransforms(g);
    }

}
