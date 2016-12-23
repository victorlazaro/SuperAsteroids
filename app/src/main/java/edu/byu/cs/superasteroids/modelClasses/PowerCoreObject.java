package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a single PowerCoreObject
 */
public class PowerCoreObject extends Visible {




    private int cannonBoost;
    private int engineBoost;

    public PowerCoreObject(String imagePath, int imageHeight, int imageWidth, int cannonBoost, int engineBoost) {
        super(imagePath, imageHeight, imageWidth);
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    /**
     * Will call update on all visible objects in the game every 1/60th of a second
     *
     */
    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void draw() {


    }
}
