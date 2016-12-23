package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.RectF;

import java.util.List;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.database.AsteroidsGameDAO;

/**
 * Created by Victor on 2/2/2016.
 * This class contains the objects used to make the game function. It is also a singleton.
 */
public class AsteroidsGame {


    private List<CannonObject> cannonObject;
    private List <EngineObject> engineObjects;
    private List<ExtraPartsObject> extraPartsObject;
    private List<MainBodyObject> mainBodyObject;
    private List<PowerCoreObject> powerCoreObject;
    private Ship completeShip;
    private Space space;
    private Level currentLevel;
    private boolean firstLoop = true;

    public AsteroidsGame(){
        completeShip = new Ship("", 0, 0);
        space = new Space("images/space.bmp", 2048, 2048);

    }

    public void setFirstLoop(boolean firstLoop) {
        this.firstLoop = firstLoop;
    }
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Ship getCompleteShip() {
        return completeShip;
    }
    public Level getCurrentLevel() {
        return currentLevel;
    }

    public List<CannonObject> getCannonObjects() {
        return cannonObject;
    }

    public List<EngineObject> getEngineObjects() {
        return engineObjects;
    }

    public List<ExtraPartsObject> getExtraPartsObjects() {
        return extraPartsObject;
    }

    public List<MainBodyObject> getMainBodyObjects() {
        return mainBodyObject;
    }

    public List<PowerCoreObject> getPowerCoreObjects() {
        return powerCoreObject;
    }

    public void loadShipParts(){
        powerCoreObject = AsteroidsGameDAO.getAllPowerCores();
        mainBodyObject = AsteroidsGameDAO.getAllMainBodies();
        extraPartsObject = AsteroidsGameDAO.getAllExtraParts();
        cannonObject = AsteroidsGameDAO.getAllCannons();
        engineObjects = AsteroidsGameDAO.getAllEngines();
    }


    /**
     * If it is the first loop of the game, this draw will initialize the ViewPort and the MiniMap.
     */
    public void draw()
    {
        if(firstLoop)
        {
            ViewPort.setViewPortDimensions();
            ViewPort.initializeViewPort();
            Asteroids.getInstance().getAsteroidsGame().getCurrentLevel().getMiniMap().initializeMiniMap();
            firstLoop = false;
        }
        space.draw();
        currentLevel.draw();
        completeShip.draw();

    }
    public void update(double elapsedTime)
    {

        if (!firstLoop) {
            space.update(elapsedTime);
            currentLevel.update(elapsedTime);
            completeShip.update(elapsedTime);
        }


    }



}
