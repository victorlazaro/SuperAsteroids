package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by Victor on 2/4/2016.
 * This class is shared by all objects that are somehow positioned.
 */
public abstract class PositionedObject extends Visible {

    private Rect bounds;
    private PointF positionCoordinates;

    protected PositionedObject(String imagePath, int imageHeight, int imageWidth)
    {
        super(imagePath, imageHeight, imageWidth);
        positionCoordinates = new PointF(0,0);
        bounds = new Rect(Math.round(positionCoordinates.x - getImageWidth()*getScale()/2),
                Math.round(positionCoordinates.y-getImageHeight()*getScale()/2),
                Math.round(getImageWidth()*getScale()/2 + positionCoordinates.x),
                Math.round(getImageHeight()*getScale()/2 + positionCoordinates.y));
    }

    public Rect getBounds() {
        return bounds;
    }

    /**
     * This function sets the bounds of the object based on its current position.
     */
    public void setBounds() {

        bounds = new Rect(Math.round(positionCoordinates.x - getImageWidth()*getScale()/2),
                Math.round(positionCoordinates.y-getImageHeight()*getScale()/2),
                Math.round(getImageWidth()*getScale()/2 + positionCoordinates.x),
                Math.round(getImageHeight()*getScale()/2 + positionCoordinates.y));
    }
    public void setPositionCoordinates(PointF positionCoordinates) {
        this.positionCoordinates = positionCoordinates;
    }

    public PointF getPositionCoordinates() {
        return positionCoordinates;
    }

    /**
     * This function sets the object's bounds to the bounds passed into the function
     * @param bounds the bounding rectangle
     */
    public void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

}
