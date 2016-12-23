package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/4/2016.
 * This class is shared by all objects that move
 */
public abstract class MovingObject extends PositionedObject {

    /** speed of the moving object */
    private int speed;
    /** angle it is being moved in */
    private float angle;


    protected MovingObject(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        angle = 0;
        speed = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


    @Override
    public void draw()
    {
        PointF viewPortPosition = ViewPort.worldToViewPort(new PointF(getPositionCoordinates().x, getPositionCoordinates().y));
        DrawingHelper.drawImage(ContentManager.getInstance().getImageId(getImagePath()),
                viewPortPosition.x, viewPortPosition.y,
                Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(),
                getScale(), getScale(), 255);
    }

    @Override
    public void update(double elapsedTime)
    {

    }
}
