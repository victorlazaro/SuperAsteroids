package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a single MainBody object
 */
public class MainBodyObject extends MovingObject {

    /**Point on the main body where the cannon is attached*/
    private PointF cannonAttach;
    /**Point on the main body where the engine is attached*/
    private PointF engineAttach;
    /**Point on the main body where the extraPart is attached*/
    private PointF extraAttach;



    public MainBodyObject(String imagePath, int imageHeight, int imageWidth, PointF cannonAttach, PointF engineAttach,
                        PointF extraAttach) {
        super(imagePath, imageHeight, imageWidth);
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
    }
    public PointF getCannonAttach() {
        return cannonAttach;
    }

    public PointF getEngineAttach() {
        return engineAttach;
    }

    public PointF getExtraAttach() {
        return extraAttach;
    }

    public void drawShipBuilding() {
        DrawingHelper.drawImage(ContentManager.getInstance().
                getImageId(getImagePath()), getPositionCoordinates().x, getPositionCoordinates().y,
                Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(), getScale(), getScale(), 255);
    }
}
