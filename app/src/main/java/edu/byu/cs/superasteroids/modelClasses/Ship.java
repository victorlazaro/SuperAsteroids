package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by Victor on 2/4/2016.
 * This class represents a complete ship. It will combine all parts (mainBody, cannon, engine, extraParts and powerCore)
 */
public class Ship extends MovingObject {

    private int lives;
    private int cannonAttackSoundId;
    private CannonObject cannon;
    private EngineObject engine;
    private PowerCoreObject powerCore;
    private MainBodyObject mainBody;
    private ExtraPartsObject extraParts;
    private boolean isShipBuilding;
    private boolean isImmune;


    public Ship(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        cannon = null;
        engine = null;
        powerCore = null;
        mainBody = null;
        extraParts = null;
        isShipBuilding = false;
        isImmune = false;
    }

    /**
     * Updates the ship. This function grabs movePoint and firePressed, and checks to see if it is null.
     * If movePoint is not null, the ship is moved and rotated. The viewPort also moves.
     * If firePressed is not null, the cannon shoots a projectile
     */
    @Override
    public void update(double elapsedTime) {


        PointF movePoint = InputManager.movePoint;
        boolean firePressed = InputManager.firePressed;
        if (movePoint != null) {
            PointF variation = GraphicsUtils.subtract(movePoint, ViewPort.worldToViewPort(getPositionCoordinates()));

            double angleRadians = getRotationAngle(variation);

            moveShip(angleRadians, elapsedTime);

            rotateShip(angleRadians);

            ViewPort.moveViewPort();
        }
        if (firePressed) {

            cannon.shootProjectile(getAngle());

            ContentManager.getInstance().playSound(cannonAttackSoundId, .5f, 1);
        }
        cannon.update(elapsedTime);
    }

    /**
     * Moves the ship.
     * @param angle the angle set between the ship and the user input in radians
     * @param elapsedTime 1/60th of a second
     */
    private void moveShip(double angle, double elapsedTime) {

        GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject
                (getPositionCoordinates(), new RectF(getBounds()), engine.getBaseSpeed(), angle, elapsedTime);
        setPositionCoordinates(moveObjectResult.getNewObjPosition());
        mainBody.setPositionCoordinates(getPositionCoordinates());
        setBounds(GraphicsUtils.rectFToRect(moveObjectResult.getNewObjBounds()));
    }


    /**
     * Rotates the ship.
     * @param rotationAngle the angle set between the ship and the user input in radians
     */
    private void rotateShip(double rotationAngle)
    {
        setAngle((float) GraphicsUtils.radiansToDegrees(rotationAngle + Math.PI / 2));

        cannon.rotate(rotationAngle + Math.PI / 2, mainBody.getCannonAttach());
        engine.rotate(rotationAngle + Math.PI / 2, mainBody.getEngineAttach());
        extraParts.rotate(rotationAngle + Math.PI / 2, mainBody.getExtraAttach());

    }

    public CannonObject getCannon() {
        return cannon;
    }
    public int getLives() {
        return lives;
    }

    /**
     * sets a cannon, as well as loads the attack sound and the attack image
     * @param cannon a cannon object
     */
    public void setCannon(CannonObject cannon) {
        this.cannon = cannon;
        try {
            cannonAttackSoundId = ContentManager.getInstance().loadSound(cannon.getAttackSound());
            ContentManager.getInstance().loadImage(cannon.getAttackImage());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void setEngine(EngineObject engine) {
        this.engine = engine;
    }

    public void setPowerCore(PowerCoreObject powerCore) {
        this.powerCore = powerCore;
    }

    public MainBodyObject getMainBody() {
        return mainBody;
    }

    public void setMainBody(MainBodyObject mainBody) {
        this.mainBody = mainBody;
    }

    public void setExtraParts(ExtraPartsObject extraParts) {
        this.extraParts = extraParts;
    }

    /**
     * Draws the ship.
     * First, it checks if the mainBody is null. If it is not, it draws the mainBody, and calls draw on the parts.
     */
    public void draw() {

            if (mainBody != null) {

                drawMainBody();
                drawExtraParts();
                drawCannon();
                drawEngine();
            }

    }

    /**
     * Draws the engine.
     * If it is null, it returns.
     */
    private void drawEngine() {
        if (engine == null)
        {
            return;
        }
            if (getAngle() == 0) {
                engine.updateCenter(mainBody.getEngineAttach());
            }

            engine.setBounds();
            getBounds().union(engine.getBounds());
            if (isShipBuilding) {
                engine.drawShipBuilding();
            }
            else
            {
                engine.draw();
            }
    }

    /**
     * Draws the cannon.
     * If it is null, it returns.
     */
    private void drawCannon() {
        if (cannon == null)
        {
            return;
        }
        if (getAngle() == 0) {
            cannon.updateCenter(mainBody.getCannonAttach());
        }

        cannon.setBounds();
        getBounds().union(cannon.getBounds());
        if(isShipBuilding) {
            cannon.drawShipBuilding();
        }
        else
        {
            cannon.draw();
        }
    }

    /**
     * Draws the extraParts.
     * If it is null, it returns.
     */
    private void drawExtraParts() {
        if (extraParts == null)
        {
            return;
        }
        if (getAngle() == 0){
            extraParts.updateCenter(mainBody.getExtraAttach());
        }
        extraParts.setBounds();
        getBounds().union(extraParts.getBounds());
        if (isShipBuilding) {
            extraParts.drawShipBuilding();
        }
        else
        {
            extraParts.draw();
        }
    }

    /**
     * Draws the mainBody, sets its bounds to be the ship's, sets its position to be the ship's.
     * If it is null, it returns.
     */
    private void drawMainBody() {
        mainBody.setPositionCoordinates(getPositionCoordinates());
        mainBody.setBounds();
        setBounds(mainBody.getBounds());
        if (isShipBuilding) {
            mainBody.drawShipBuilding();
        }
        else
        {
            mainBody.draw();
        }
    }


    /**
     * This function checks to see if the ship is complete.
     * @return true if no parts are null, false otherwise.
     */
    public boolean isComplete() {
        return !(engine == null || mainBody == null || cannon == null || extraParts == null || powerCore == null);
    }

    /**
     * Gets the angle set between the ship and the user input
     * @param variation the offset between the ship and the user input
     * @return the rotation angle in radians
     */
    private double getRotationAngle(PointF variation) {
        double rotationAngle = 0;
        if (variation.x != 0 && variation.y != 0)
        {
            rotationAngle = Math.atan2(variation.y, variation.x);
        }
        else if (variation.x != 0)
        {
            rotationAngle = ((variation.x > 0) ? 0 : Math.PI);
        }
        else if (variation.y != 0)
        {
            rotationAngle = ((variation.y > 0) ? (.5 * Math.PI) : (1.5 * Math.PI));
        }
        return rotationAngle;
    }


    /**
     * Sets every part of the ship to null, as well as the angle back to 0.
     */
    public void clearShip() {
        mainBody = null;
        engine = null;
        cannon = null;
        extraParts = null;
        powerCore = null;
        setAngle(0);
    }

    /**
     * This function sets the boolean isShipBuilding, which is necessary so that the ship is drawn differently on the
     * ShipBuildingActivity from the GameActivity
     * @param isShipBuilding the boolean saying whether the current activity is the ShipBuilding or not
     */
    public void setShipBuilding(boolean isShipBuilding) {

        this.isShipBuilding = isShipBuilding;
    }

    /**
     * Gets called when the Ship hits an asteroid.
     * Decrement lives and sets the boolean isImmune to be true.
     */
    public void hitAsteroid() {

        lives--;
        isImmune = true;

    }

    /**
     * Sets the scale of the ship, as well as all of its parts.
     * @param scale the appropriate scale.
     */
    @Override
    public void setScale(float scale)
    {
        super.setScale(scale);
        if (isComplete()) {
            mainBody.setScale(scale);
            engine.setScale(scale);
            cannon.setScale(scale);
            extraParts.setScale(scale);
        }

    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setIsImmune(boolean isImmune) {
        this.isImmune = isImmune;
    }
}
