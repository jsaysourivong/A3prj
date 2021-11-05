package org.csc133.a3.gameObjects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.inerfaces.Drawable;


public abstract class GameObject implements Drawable{

    protected Transform translation, rotation, scale;
    protected Transform gOrigXform;

    private int color;
    public Point2D location;
    public Dimension dimension;
    public Dimension mapSize;
    private Point lowerLeftInLocalSpace;

    public GameObject(double x, double y, int width, int height, int color){
        translation = Transform.makeIdentity();
        rotation = Transform.makeIdentity();
        scale = Transform.makeIdentity();
        //pretty useless rn
        lowerLeftInLocalSpace = new Point(    (int)x - width/2,
                                              (int)y - height/2);
        translation.translate((float)x,(float)y);
        dimension = new Dimension(width,height);
        location = new Point2D(x,y);
        this.color = color;
    }

    public GameObject(double x, double y, int size, int color){
        translation = Transform.makeIdentity();
        rotation = Transform.makeIdentity();
        scale = Transform.makeIdentity();
        //pretty useless rn
        lowerLeftInLocalSpace = new Point(    (int)x -size/2,
                                              (int)y - size/2);
        translation.translate((float)x,(float)y);
        dimension = new Dimension(size,size);
        location = new Point2D(x,y);
        this.color = color;
    }

    public void rotate(double degrees){
        rotation.rotate((float)Math.toRadians(degrees), 0, 0);
    }

    public void scale(double sx, double sy){
        scale.scale((float) sx, (float) sy);
    }

    public void translate(double tx, double ty){
        translation.translate((float)tx, (float)ty);
    }

    public void calculateLowerLeftInLocalSpace(){
        lowerLeftInLocalSpace = new Point(-this.dimension.getWidth(),
                                            -this.dimension.getHeight());
    }

    protected Transform preLTTransforms(Graphics g, Point originParent,
                                        Point originScreen){
        Transform gXform = Transform.makeIdentity();
        // get the current transform and save it
        //

        g.getTransform(gXform);
        gOrigXform = gXform.copy();

        // move the drawing coordinates back
        //
        gXform.translate(-originParent.getX(), -originParent.getY());
        gXform.translate(originScreen.getX(), originScreen.getY());
        return gXform;
    }

    protected void localTransforms(Transform gXform) {
        // append Objects's LTs to the graphics object's transform
        //
        gXform.translate(translation.getTranslateX(), translation.getTranslateY());
        gXform.concatenate(rotation);
        gXform.scale(scale.getScaleX(), scale.getScaleY());
    }

    protected void postLTTransform(Graphics g, Point originParent,
                                   Transform gXform, Point originScreen) {
        // move the drawing coordinates so that the local origin coincides with the screen origin
        //
        gXform.translate(-originScreen.getX(),-originScreen.getY());
        gXform.translate(originParent.getX(), originParent.getY());
        g.setTransform(gXform);
    }


    protected void restoreOriginalTransforms(Graphics g) {
        // restore the original xform
        //
        g.setTransform(gOrigXform);
    }

    // makes the center of the object the origin
    protected void cn1ForwardPrimitiveTranslate(Graphics g, Dimension pDim){
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(-pDim.getWidth()/2, -pDim.getHeight()/2);
        g.setTransform(gxForm);
    }

    protected void cn1ReversePrimitiveTranslate(Graphics g, Dimension pDim){
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(pDim.getWidth()/2,pDim.getHeight()/2);
        g.setTransform(gxForm);
    }

    abstract public void localDraw(Graphics g, Point originParent, Point originScreen);

    public void draw(Graphics g, Point originParent, Point originScreen) {
        g.setColor(color);
       Transform gXform = preLTTransforms(g, originParent, originScreen);
        localTransforms(gXform);
        postLTTransform(g, originParent, gXform, originScreen);
        localDraw(g,originParent,originScreen);
        // restore the original xform
        //
        restoreOriginalTransforms(g);
    }

    public void tick(){}

    private void setX(double x){
        translation.setTranslation((float)x,(float)getY());
    }

    private void setY(double y){
        translation.setTranslation((float)getX(),(float)y);
    }

    public void setLocation(double setX, double setY){
        //this.location.setX(setX);
        //this.location.setY(setY);
        this.setX(setX);
        this.setY(setY);
    }

    public Point getLowerLeftInLocalSpace(){
        return this.lowerLeftInLocalSpace;
    }

    public int getWidth(){ return dimension.getWidth(); }

    public int getHeight(){
        return dimension.getHeight();
    }

    public double getX(){
        return translation.getTranslateX();
    }

    public double getY() {
        return translation.getTranslateY();
    }

    public int getSize(){
        return this.dimension.getWidth();
    }

    public Dimension getDimension(){return this.dimension;}

    public void updateLocation(double deltaX, double deltaY){
        setLocation(this.getX() + deltaX, this.getY() + deltaY);

    }

}