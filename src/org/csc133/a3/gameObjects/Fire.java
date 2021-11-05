package org.csc133.a3.gameObjects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import java.util.Random;

import static com.codename1.ui.CN.*;

public class Fire extends Fixed{

    public int size;
    private int area;
    private Random r = new Random();

    public Fire(){
        super(0,0,0, ColorUtil.MAGENTA);
        getRandomSize();
        setFireArea();
    }

    public Fire(int xPosition, int yPosition) {
        super(xPosition, yPosition, 300, ColorUtil.MAGENTA);
        getRandomSize();
    }

    private void setFireArea(){
        area = (int) (Math.PI * (size/2) * (size/2));
    }

    private void updateFireArea(){
        area = (int) (Math.PI * (size/2) * (size/2));
    }

    public int getFireArea(){
        updateFireArea();
        return this.area;
    }

    void getRandomSize() {
        size = r.nextInt((100 - 50) + 50);
    }


    public void localDraw(Graphics g, Point containerOrigin, Point screenOrigin) {
        cn1ForwardPrimitiveTranslate(g, getDimension());

        g.fillRoundRect(    0,
                            0,
                            size,
                            size,
                            size,
                            size);
        g.drawString(String.valueOf(    size),
                                        size + 10,
                                        size + 10);

        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM));

        cn1ReversePrimitiveTranslate(g, getDimension());
    }

    private void updateSize(int increaseSize){
        this.size += increaseSize;
    }

    public void randomFireGrowth() {
        int increaseSize;
        increaseSize = r.nextInt(10);
        updateSize(increaseSize);
    }
}
