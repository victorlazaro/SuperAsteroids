package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a single EngineObject
 */
public class EngineObject extends AttachedObjects {

    private int baseSpeed;
    private int baseTurnRate;

    public EngineObject(String imagePath, int imageHeight, int imageWidth, PointF attachPoint, int baseSpeed, int baseTurnRate) {
        super(imagePath, imageHeight, imageWidth, attachPoint);
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }
    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void drawShipBuilding() {

        DrawingHelper.drawImage(ContentManager.getInstance().
                getImageId(getImagePath()), getPositionCoordinates().x, getPositionCoordinates().y
                , Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(), getScale(), getScale(), 255);

    }



}
