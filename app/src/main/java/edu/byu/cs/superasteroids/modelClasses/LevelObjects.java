package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/1/2016.
 */
public class LevelObjects extends PositionedObject {


    private float scale;

    public LevelObjects(String imagePath, PointF position, float scale) {
        super(imagePath, 0, 0);
        setPositionCoordinates(position);
        this.scale = scale;
        setBounds();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {

        PointF viewPortPosition = ViewPort.worldToViewPort(getPositionCoordinates());
        DrawingHelper.drawImage(ContentManager.getInstance().getImageId(getImagePath()), viewPortPosition.x, viewPortPosition.y, 0, scale, scale, 255);

    }
}
