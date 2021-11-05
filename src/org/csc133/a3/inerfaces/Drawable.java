package org.csc133.a3.inerfaces;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface Drawable {
    void draw(Graphics g, Point originContainer, Point originScreen);
}
