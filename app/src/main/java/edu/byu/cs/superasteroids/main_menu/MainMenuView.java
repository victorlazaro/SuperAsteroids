package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IController;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingView;

/**
 * Created by Victor on 2/10/2016.
 */
public class MainMenuView implements IMainMenuView {
    /**
     * Instructs the ShipBuildingView to start the game.
     */
    @Override
    public void startGame() {

        ShipBuildingView shipBuildingView = new ShipBuildingView();
        shipBuildingView.startGame();

    }

    /**
     * Gets the view's controller
     *
     * @return The view's controller
     */
    @Override
    public IController getController() {
        return null;
    }

    /**
     * Sets the view's controller
     *
     * @param controller The controller to set
     */
    @Override
    public void setController(IController controller) {

    }
}
