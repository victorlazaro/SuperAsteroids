package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a single ExtraPart object
 */
public class ExtraPartsObject extends AttachedObjects {


    public ExtraPartsObject(String imagePath, int imageHeight, int imageWidth, PointF attachPoint) {
        super(imagePath, imageHeight, imageWidth, attachPoint);
    }

    public void drawShipBuilding() {
        DrawingHelper.drawImage(ContentManager.getInstance().
                getImageId(getImagePath()), getPositionCoordinates().x, getPositionCoordinates().y
                , Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(), getScale(), getScale(), 255);
    }
}
