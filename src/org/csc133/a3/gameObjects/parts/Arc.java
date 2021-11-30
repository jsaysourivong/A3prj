package org.csc133.a3.gameObjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameObjects.GameObject;
import org.csc133.a3.gameObjects.Movable;

public class Arc extends Movable {

    private final int startAngle;
    private final int arcAngle;

    public Arc( int width, int height,
                int startAngle, int arcAngle,
                int color){
        this(   width,
                height,
                0,
                0,
                0,
                0,
                startAngle,
                arcAngle,
                0,
                color);
    }
    public Arc( int width, int height,
                float tx, float ty,
                float sx, float sy,
                int startAngle, int arcAngle,
                float degreesRotation,
                int color){

        super(0,0, width, height, color);
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;

        translate(tx, ty);
        scale(sx, sy);
        rotate(degreesRotation);

    }

    @Override
    public void localDraw(Graphics g, Point originParent, Point originScreen) {
        cn1ForwardPrimitiveTranslate(g, getDimension());
        g.drawArc(  0,
                    0,
                    getWidth(),
                    getHeight(),
                    startAngle,
                    arcAngle);
        cn1ReversePrimitiveTranslate(g, getDimension());
    }

}
