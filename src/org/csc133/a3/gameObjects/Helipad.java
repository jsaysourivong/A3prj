package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;


public class Helipad extends Fixed {

    private int color;
    private int rectSize;
    private Point center;
    private Dimension mapSize;

    public Helipad(Dimension mapSize) {
        super(  (double) mapSize.getWidth() / 2,
                100,
                150, ColorUtil.GRAY);
        center = new Point(super.getSize() / 2, super.getSize() /2);
        this.mapSize = mapSize;
        setColor();
    }

    private void setColor(){
        this.color = ColorUtil.rgb(153,153,153);
    }

    public int getRectSize(){
        return this.rectSize;
    }

    public void localDraw(Graphics g, Point containerOrigin, Point screenOrigin) {
        g.drawRect(0,
                0,
                getSize(),
                getSize(),
                5);
        g.drawRoundRect(0 + 10,
                0 + 10,
                getSize() - 25,
                getSize() - 25,
                getSize() - 25,
                getSize() - 25);
    }

}
