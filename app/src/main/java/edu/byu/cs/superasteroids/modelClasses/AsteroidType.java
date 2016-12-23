package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This abstract class represents a type of Asteroid, which is split up into RegularAsteroid, GrowingAsteroid and OcteroidAsteroid
 */
public abstract class AsteroidType extends MovingObject {



    private String asteroidName;
    private String type;
    private boolean isChild;
    private int splitsInto;
    private float growingRate;
    private double immuneTime;
    private boolean collided;

    /**
     * The constructor for an AsteroidType sets:
     *  scale: .5
     *  random angle from 0 to PI
     *  random speed from 200 to 400
     *  boolean isChild to false, boolean collided to false, splitsInto to 0, growingRate to 0
     *  (ship) immuneTime after it hits an asteroid to 3 seconds
     *
     *
     * @param imagePath asteroid image path
     * @param imageHeight image height
     * @param imageWidth image width
     * @param asteroidName asteroid name
     * @param type asteroid type
     */
    public AsteroidType(String imagePath, int imageHeight, int imageWidth, String asteroidName, String type) {
        super(imagePath, imageHeight, imageWidth);
        this.asteroidName = asteroidName;
        this.type = type;
        setScale(.5f);
        Random random = new Random();
        setAngle(random.nextInt(3) + 1);
        setSpeed(random.nextInt(200) + 200);
        isChild = false;
        splitsInto = 0;
        growingRate = 0;
        immuneTime = 3;
        collided = false;
    }


    public String getAsteroidName() {
        return asteroidName;
    }

    public String getType() {
        return type;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSplitsInto(int splitsInto) {
        this.splitsInto = splitsInto;
    }
    private void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }
    private float getGrowingRate() {
        return growingRate;
    }

    public void setGrowingRate(float growingRate) {
        this.growingRate = growingRate;
    }

    /**
     * Gets called when a projectile hits an asteroid. If the asteroid is a child, it sets the flag collided to true and returns.
     * Otherwise it splits into however many asteroids it is supposed to.
     */
    public void getHit()
    {
        if (isChild || Asteroids.getInstance().getAsteroidsGame().getCompleteShip().isImmune())
        {
            collided = true;
            return;
        }
        for (int i = 0; i < splitsInto; i++)
        {
            AsteroidType asteroid = new AsteroidType(getImagePath(), getImageWidth(), getImageWidth(), asteroidName, type) {};
            asteroid.setPositionCoordinates(getPositionCoordinates());
            asteroid.setScale(getScale() / splitsInto);
            asteroid.setIsChild(true);
            asteroid.setGrowingRate(growingRate);
            Asteroids.getInstance().getAsteroidsGame().getCurrentLevel().insertAsteroid(asteroid);
        }
        collided = true;
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
        return asteroidName.hashCode() + getPositionCoordinates().hashCode();
    }

    /**
     * If this asteroid is a growing, it will increase the size by growingRate*1/60 every update.
     *
     * If the ship is not immune and it hit an asteroid, it will call the function hitAsteroid on the ship.
     * Else, it decrements the elapsed time from the immune time. Once immuneTime reaches 0, the ship is not immune
     * anymore, and immuneTime is reset to 3 seconds.
     * @param elapsedTime 1/60 of a second
     */
    @Override
    public void update(double elapsedTime)
    {

        if (type.equals("growing"))
        {
            setScale((float)(getGrowingRate()*elapsedTime + getScale()));
        }
        GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject(getPositionCoordinates(), new RectF(getBounds()), getSpeed(), getAngle(), elapsedTime);
        setPositionCoordinates(moveObjectResult.getNewObjPosition());

        setBounds();

        GraphicsUtils.RicochetObjectResult ricochetObjectResult = GraphicsUtils.ricochetObject
                (getPositionCoordinates(), new RectF(getBounds()), getAngle(), ViewPort.getWorldDimensions().width(),
                ViewPort.getWorldDimensions().height());

        setPositionCoordinates(ricochetObjectResult.getNewObjPosition());

        setBounds();

        setAngle((float) ricochetObjectResult.getNewAngleRadians());

        if (!Asteroids.getInstance().getAsteroidsGame().getCompleteShip().isImmune()) {
            if (getBounds().intersect(Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getBounds())) {
                getHit();
                Asteroids.getInstance().getAsteroidsGame().getCompleteShip().hitAsteroid();
            }
        }
        else
        {
            immuneTime -= elapsedTime;
            if (immuneTime <= 0)
            {
                Asteroids.getInstance().getAsteroidsGame().getCompleteShip().setIsImmune(false);
                immuneTime = 3;
            }
        }

    }
    @Override
    public void draw()
    {
        PointF viewPortTranslation = ViewPort.worldToViewPort(getPositionCoordinates());
        DrawingHelper.drawImage(ContentManager.getInstance().getImageId(getImagePath()), viewPortTranslation.x,
                viewPortTranslation.y, 0, getScale(), getScale(), 255);
    }





}

