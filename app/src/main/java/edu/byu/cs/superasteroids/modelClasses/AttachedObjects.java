package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by Victor on 2/4/2016.
 * This class represents all objects that are contain an attachPoint.
 */
public abstract class AttachedObjects extends MovingObject {


    /** The point in which the object will be attached */
    private PointF attachPoint;

    public AttachedObjects(String imagePath, int imageHeight, int imageWidth, PointF attachPoint) {
        super(imagePath, imageHeight, imageWidth);
        this.attachPoint = attachPoint;

    }
    public PointF getAttachPoint() {
        return attachPoint;
    }


    /**
     * Rotates an object based on the rotation angle passed into the function, setting its center to the correct point
     * based on the respective attach point on the main body
     * @param rotationAngle the ship's rotation angle in radians
     * @param mainBodyAttachPoint That specific part attach point on the main body
     */
    public void rotate(double rotationAngle, PointF mainBodyAttachPoint) {

        PointF rotatePartOffset = GraphicsUtils.rotate(getPartOffset(mainBodyAttachPoint), rotationAngle);

        setPositionCoordinates(GraphicsUtils.add(Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getPositionCoordinates(),
                rotatePartOffset));

    }

    /**
     * Gets the offset from the part attach point to the mainBody attach point
     * @param bodyAttachPoint the main body attach point of that specific part
     * @return The offset point
     */
    private PointF getPartOffset(PointF bodyAttachPoint) {

        PointF bodyOffset = GraphicsUtils.subtract(bodyAttachPoint,
                new PointF(Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getMainBody().getImageWidth() / 2,
                        Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getMainBody().getImageHeight() / 2));

        PointF partOffset = GraphicsUtils.subtract(new PointF(getImageWidth() / 2,
                getImageHeight() / 2), getAttachPoint());

        return GraphicsUtils.scale(GraphicsUtils.add(bodyOffset, partOffset), getScale());
    }

    public void updateCenter(PointF mainBodyAttach) {


        setPositionCoordinates(GraphicsUtils.add(Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getPositionCoordinates(),
                getPartOffset(mainBodyAttach)));
    }
}
