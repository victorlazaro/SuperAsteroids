package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.database.AsteroidsGameDAO;
import edu.byu.cs.superasteroids.modelClasses.AsteroidsGame;
import edu.byu.cs.superasteroids.modelClasses.Ship;

/**
 * Created by Victor on 2/10/2016.
 */
public class MainMenuController implements IMainMenuController {

    private IMainMenuView view;
    private int mainBodyId;
    private int engineId;
    private int extraPartId;
    private int cannonId;
    private int powerCoreId;
    public MainMenuController(IMainMenuView view)
    {
        this.view = view;
    }
    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     * This function builds a ship with random parts
     */
    @Override
    public void onQuickPlayPressed() {

        Ship ship = edu.byu.cs.superasteroids.core.Asteroids.getInstance().getAsteroidsGame().getCompleteShip();
        ship.clearShip();
        Asteroids.getInstance().getAsteroidsGame().loadShipParts();
        if (Asteroids.getInstance().getAsteroidsGame().getMainBodyObjects().size() == 0)
        {
            return;
        }
        loadContent(ContentManager.getInstance());
        mainBodyId = (Math.random() < 0.5) ? 0 : 1;
        engineId = (Math.random() < 0.5) ? 0 : 1;
        extraPartId = (Math.random() < 0.5) ? 0 : 1;
        cannonId = (Math.random() < 0.5) ? 0 : 1;
        powerCoreId = (Math.random() < 0.5) ? 0 : 1;
        ship.setMainBody(Asteroids.getInstance().getAsteroidsGame().getMainBodyObjects().get(mainBodyId));
        ship.setEngine(Asteroids.getInstance().getAsteroidsGame().getEngineObjects().get(engineId));
        ship.setPowerCore(Asteroids.getInstance().getAsteroidsGame().getPowerCoreObjects().get(powerCoreId));
        ship.setExtraParts(Asteroids.getInstance().getAsteroidsGame().getExtraPartsObjects().get(extraPartId));
        ship.setCannon(Asteroids.getInstance().getAsteroidsGame().getCannonObjects().get(cannonId));
        Asteroids.getInstance().getAsteroidsGame().setCurrentLevel(AsteroidsGameDAO.queryLevel(1));
        unloadContent(ContentManager.getInstance());
        view.startGame();
    }

    private void loadContent(ContentManager content) {
            AsteroidsGame asteroidsGame = Asteroids.getInstance().getAsteroidsGame();
            for (int i = 0; i < asteroidsGame.getMainBodyObjects().size(); i++)
            {
                content.loadImage(asteroidsGame.getMainBodyObjects().get(i).getImagePath());
            }
            for (int i = 0; i < asteroidsGame.getEngineObjects().size(); i++)
            {
                content.loadImage(asteroidsGame.getEngineObjects().get(i).getImagePath());
            }
            for (int i = 0; i < asteroidsGame.getCannonObjects().size(); i++)
            {
                content.loadImage(asteroidsGame.getCannonObjects().get(i).getImagePath());
            }
            for (int i = 0; i < asteroidsGame.getExtraPartsObjects().size(); i++)
            {
                content.loadImage(asteroidsGame.getExtraPartsObjects().get(i).getImagePath());
            }
            for (int i = 0; i < asteroidsGame.getPowerCoreObjects().size(); i++)
            {
                content.loadImage(asteroidsGame.getPowerCoreObjects().get(i).getImagePath());
            }
    }

    private void unloadContent(ContentManager content) {

        AsteroidsGame asteroidsGame = Asteroids.getInstance().getAsteroidsGame();
        for (int i = 0; i < asteroidsGame.getEngineObjects().size(); i++) {
            if (i != engineId) {
                content.unloadImage(content.getImageId(asteroidsGame.getEngineObjects().get(i).getImagePath()));
            }
        }
        for (int i = 0; i < asteroidsGame.getExtraPartsObjects().size(); i++) {
            if (i != extraPartId) {
                content.unloadImage(content.getImageId(asteroidsGame.getExtraPartsObjects().get(i).getImagePath()));
            }
        }
        for (int i = 0; i < asteroidsGame.getMainBodyObjects().size(); i++) {
            if (i != mainBodyId) {
                content.unloadImage(content.getImageId(asteroidsGame.getMainBodyObjects().get(i).getImagePath()));
            }
        }
        for (int i = 0; i < asteroidsGame.getCannonObjects().size(); i++) {
            if (i != cannonId) {
                content.unloadImage(content.getImageId(asteroidsGame.getCannonObjects().get(i).getImagePath()));
            }
        }
        for (int i = 0; i < asteroidsGame.getPowerCoreObjects().size(); i++) {
            if (i != powerCoreId) {
                content.unloadImage(content.getImageId(asteroidsGame.getPowerCoreObjects().get(i).getImagePath()));
            }
        }
    }
    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return null;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {

    }
}
