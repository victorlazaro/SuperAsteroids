package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/4/2016.
 * This class represents a projectile
 */
public class Projectile extends MovingObject {

    public boolean hitAsteroid;
    public boolean outOfBounds;

    public Projectile(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        hitAsteroid = false;
        outOfBounds = false;
    }

    /**
     * Will update the projectiles by checking for collision, then moving the projectiles
     */
    @Override
    public void update(double elapsedTime) {

        checkCollision(elapsedTime);
        moveProjectile(elapsedTime);

    }

    /**
     * Checks to see if the projectile hit an asteroid. If it did, it calls getHit() on the asteroid, then removes that specific
     * asteroid from the array of asteroids in the cannonObject
     * @param elapsedTime
     */
    private void checkCollision(double elapsedTime) {

        for (AsteroidType asteroid : Asteroids.getInstance().getAsteroidsGame().getCurrentLevel().getAsteroids())
        {
            if (asteroid.getBounds().contains( (int) getPositionCoordinates().x, (int) getPositionCoordinates().y))
            {
                asteroid.getHit();
                Asteroids.getInstance().getAsteroidsGame().getCurrentLevel().getAsteroids().remove(asteroid);
                hitAsteroid = true;
                return;
            }
        }
    }

    /**
     * Moves the projectile, sets its new position and bounds.
     * Checks to see if the projectile went out of bounds. If so, it sets a flag that it went out of bounds, which is used
     * to remove that projectile from the projectile arrayList
     * @param elapsedTime
     */
    private void moveProjectile(double elapsedTime) {

        GraphicsUtils.MoveObjectResult moveObjectResult =
                GraphicsUtils.moveObject(getPositionCoordinates(), new RectF(getBounds()), getSpeed(),
                        getAngle() - Math.PI / 2, elapsedTime);

        setPositionCoordinates(moveObjectResult.getNewObjPosition());
        setBounds(GraphicsUtils.rectFToRect(moveObjectResult.getNewObjBounds()));
        if (!ViewPort.getWorldDimensions().contains(getPositionCoordinates().x, getPositionCoordinates().y))
        {
            outOfBounds = true;
        }
    }

    @Override
    public void draw() {

        PointF viewPortPosition = ViewPort.worldToViewPort(getPositionCoordinates());

        DrawingHelper.drawImage(ContentManager.getInstance().getImageId(getImagePath()),
                viewPortPosition.x, viewPortPosition.y, (float)
                        GraphicsUtils.radiansToDegrees(getAngle()), getScale(), getScale(), 255);
    }


    @Override
    public boolean equals(Object o) {

        if (o == null)
        {
            return false;
        }
        if (o.getClass() != getClass())
        {
            return false;
        }
        return o.hashCode() == hashCode();
    }
    @Override
    public int hashCode()
    {
        return getPositionCoordinates().hashCode();
    }

}
