package org.csc133.a3.gameObjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameObjects.Movable;

public class Rectangle extends Movable {
    public Rectangle( int width, int height,
                      float tx, float ty,
                      float sx, float sy,
                      float degrees,
                      int color){

        super(0,0, width, height, color);

        translate(tx, ty);
        scale(sx, sy);
        rotate(degrees);
    }

    @Override
    public void localDraw(Graphics g, Point originParent, Point originScreen) {
        cn1ForwardPrimitiveTranslate(g, getDimension());
        g.drawRect( 0,
                    0,
                    getWidth(),
                    getHeight());
        cn1ReversePrimitiveTranslate(g, getDimension());

    }
}
