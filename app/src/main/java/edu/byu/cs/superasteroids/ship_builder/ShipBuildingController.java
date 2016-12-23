package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.PointF;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.modelClasses.*;

/**
 * Created by Victor on 2/10/2016.
 */
public class ShipBuildingController implements IShipBuildingController {


    private IShipBuildingView shipBuildingView;
    private List<CannonObject> cannonObjectList;
    private List<EngineObject> engineObjectList;
    private List<ExtraPartsObject> extraPartsObjectList;
    private List<MainBodyObject> mainBodyObjectList;
    private List<PowerCoreObject> powerCoreObjectList;
    private Integer cannonId;
    private Integer engineId;
    private Integer extraPartId;
    private Integer mainBodyId;
    private Integer powerCoreId;
    private IShipBuildingView.PartSelectionView currentPartSelectionView;
    private Ship completeShip;

    public ShipBuildingController(IShipBuildingView shipBuildingView)
    {
        setView(shipBuildingView);
        AsteroidsGame asteroidsGame = edu.byu.cs.superasteroids.core.Asteroids.getInstance().getAsteroidsGame();
        asteroidsGame.loadShipParts();
        cannonObjectList = asteroidsGame.getCannonObjects();
        engineObjectList = asteroidsGame.getEngineObjects();
        extraPartsObjectList = asteroidsGame.getExtraPartsObjects();
        mainBodyObjectList = asteroidsGame.getMainBodyObjects();
        powerCoreObjectList = asteroidsGame.getPowerCoreObjects();
        currentPartSelectionView = IShipBuildingView.PartSelectionView.MAIN_BODY;
        completeShip = asteroidsGame.getCompleteShip();
        completeShip.clearShip();
        completeShip.setAngle(0f);
        completeShip.setScale(3);
        completeShip.setShipBuilding(true);

    }

    /**
     * This function is called by the update method in the ShipBuildingController. It calls the setStartGameButton
     * on the view, and passes a boolean into it, which is a function in the Ship class that returns true if the ship is complete.
     * This way, it will enable the start button once the ship is complete.
     */
    private void setUpStartButton()
    {
        shipBuildingView.setStartGameButton(completeShip.isComplete());

    }
    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     *
     * @param partView
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        setCurrentArrows(partView);
    }

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     *
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime) {
        setUpStartButton();
    }

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
    @Override
    public void loadContent(ContentManager content) {
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 0; i < mainBodyObjectList.size(); i++)
        {
            imageIds.add(content.loadImage(mainBodyObjectList.get(i).getImagePath()));
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, imageIds);
        imageIds.clear();
        for (int i = 0; i < engineObjectList.size(); i++)
        {
            imageIds.add(content.loadImage(engineObjectList.get(i).getImagePath()));
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, imageIds);
        imageIds.clear();

        for (int i = 0; i < cannonObjectList.size(); i++)
        {
            imageIds.add(content.loadImage(cannonObjectList.get(i).getImagePath()));
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, imageIds);
        imageIds.clear();

        for (int i = 0; i < extraPartsObjectList.size(); i++)
        {
            imageIds.add(content.loadImage(extraPartsObjectList.get(i).getImagePath()));
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, imageIds);
        imageIds.clear();

        for (int i = 0; i < powerCoreObjectList.size(); i++)
        {
            imageIds.add(content.loadImage(powerCoreObjectList.get(i).getImagePath()));
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, imageIds);
        imageIds.clear();

    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     *
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {

        for(int i = 0; i < engineObjectList.size(); i++)
        {
            if (i != engineId) {
                content.unloadImage(content.getImageId(engineObjectList.get(i).getImagePath()));
            }
        }
        for(int i = 0; i < extraPartsObjectList.size(); i++)
        {
            if (i != extraPartId) {
                content.unloadImage(content.getImageId(extraPartsObjectList.get(i).getImagePath()));
            }
        }
        for(int i = 0; i < mainBodyObjectList.size(); i++)
        {
            if (i != mainBodyId) {
                content.unloadImage(content.getImageId(mainBodyObjectList.get(i).getImagePath()));
            }
        }
        for(int i = 0; i < cannonObjectList.size(); i++)
        {
            if (i != cannonId) {
                content.unloadImage(content.getImageId(cannonObjectList.get(i).getImagePath()));
            }
        }
        for(int i = 0; i < powerCoreObjectList.size(); i++)
        {
            if (i != powerCoreId) {
                content.unloadImage(content.getImageId(powerCoreObjectList.get(i).getImagePath()));
            }
        }
    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw() {

        completeShip.setPositionCoordinates(new PointF(DrawingHelper.getGameViewWidth() /2, DrawingHelper.getCanvas().getHeight()/2));
        completeShip.draw();

    }

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     *
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        ShipBuildingActivity shipBuildingActivity = (ShipBuildingActivity)getView();
        switch(currentPartSelectionView) {
            case MAIN_BODY:
                switch (direction) {
                    case RIGHT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.ENGINE;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case ENGINE:
                switch (direction) {
                    case RIGHT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.CANNON;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case CANNON:
                switch (direction) {
                    case RIGHT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.ENGINE;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case EXTRA_PART:
                switch (direction) {
                    case RIGHT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.CANNON;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case POWER_CORE:
                switch (direction) {
                    case RIGHT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentPartSelectionView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        shipBuildingActivity.animateToView(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;

        }
    }

    /**
     * This function returns the string related to the respective arrow.
     * @param currentPartSelectionView the current part of the ship that is being shown
     * @param direction the direction of the arrow
     * @return String (one of the ship parts)
     */
    private String getDirection(IShipBuildingView.PartSelectionView currentPartSelectionView, IShipBuildingView.ViewDirection direction) {

        switch (currentPartSelectionView){

            case MAIN_BODY:{
                switch (direction){
                    case LEFT: return "POWER CORE";
                    case RIGHT: return "ENGINE";
                }
            }
            case ENGINE:{
                switch (direction){
                    case LEFT: return "MAIN BODY";
                    case RIGHT: return "CANNON";
                }
            }
            case CANNON:{
                switch (direction){
                    case LEFT: return "ENGINE";
                    case RIGHT: return "EXTRA PART";
                }
            }
            case EXTRA_PART:{
                switch (direction){
                    case LEFT: return "CANNON";
                    case RIGHT: return "POWER CORE";
                }
            }
            case POWER_CORE:{
                switch (direction){
                    case LEFT: return "EXTRA PART";
                    case RIGHT: return "MAIN BODY";
                }
            }
        }

        return null;
    }

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     *
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {

        switch (currentPartSelectionView){
            case MAIN_BODY:
            {
                mainBodyId = index;
                completeShip.setMainBody(mainBodyObjectList.get(index));
                break;
            }
            case ENGINE:
            {
                if (completeShip.getMainBody() == null)
                {
                    Toast toast = Toast.makeText((ShipBuildingActivity)getView(), "You must choose a main body first", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                engineId = index;
                completeShip.setEngine(engineObjectList.get(index));
                break;
            }
            case CANNON:
            {
                if (completeShip.getMainBody() == null)
                {
                    Toast toast = Toast.makeText((ShipBuildingActivity)getView(), "You must choose a main body first", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                cannonId = index;
                completeShip.setCannon(cannonObjectList.get(index));
                break;
            }
            case EXTRA_PART:
            {
                if (completeShip.getMainBody() == null)
                {
                    Toast toast = Toast.makeText((ShipBuildingActivity)getView(), "You must choose a main body first", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                extraPartId = index;
                completeShip.setExtraParts(extraPartsObjectList.get(index));
                break;
            }
            case POWER_CORE:
            {
                if (completeShip.getMainBody() == null)
                {
                    Toast toast = Toast.makeText((ShipBuildingActivity)getView(), "You must choose a main body first", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                powerCoreId = index;
                completeShip.setPowerCore(powerCoreObjectList.get(index));
                break;
            }
        }

    }

    /**
     * The ShipBuildingView calls this function is called when the start game button is pressed.
     */
    @Override
    public void onStartGamePressed() {

        completeShip.setShipBuilding(false);

        shipBuildingView.startGame();
    }

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Reset the Camera and
     * the ship position as needed when this is called.
     */
    @Override
    public void onResume() {

    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return shipBuildingView;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {

        shipBuildingView = (IShipBuildingView) view;
    }

    /**
     * Sets the left and right arrows related to that specific partView
     * Enables the left and right arrows, disables the up and down.
     *
     * @param partView the current partView (the part that is being shown)
     */
    public void setCurrentArrows(IShipBuildingView.PartSelectionView partView)
    {
        shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, getDirection(currentPartSelectionView, IShipBuildingView.ViewDirection.LEFT));
        shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, getDirection(currentPartSelectionView, IShipBuildingView.ViewDirection.RIGHT));
        shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, "");
        shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, "");
    }
}
