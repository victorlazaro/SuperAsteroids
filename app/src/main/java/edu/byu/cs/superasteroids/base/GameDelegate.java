package edu.byu.cs.superasteroids.base;

import android.graphics.Color;
import android.graphics.Rect;
import java.io.IOException;
import java.util.ArrayList;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.database.AsteroidsGameDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.modelClasses.AsteroidType;
import edu.byu.cs.superasteroids.modelClasses.AsteroidsGame;
import edu.byu.cs.superasteroids.modelClasses.Level;
import edu.byu.cs.superasteroids.modelClasses.LevelObjects;
import edu.byu.cs.superasteroids.modelClasses.LevelTransition;

/**
 * Created by Victor on 2/11/2016.
 */
public class GameDelegate implements IGameDelegate {


    private AsteroidsGame asteroidsGame;
    private Level currentLevel;
    private ArrayList<Integer> contentIds;
    private LevelTransition levelTransition;
    private int currentLevelNumber;
    private int bgSoundId;
    private boolean bgSoundIsPlaying;
    private boolean win;
    private boolean newLevel;

    public GameDelegate (){
        asteroidsGame = Asteroids.getInstance().getAsteroidsGame();
        currentLevelNumber = 1;
        if (asteroidsGame.getCurrentLevel() == null)
        {
            asteroidsGame.setCurrentLevel(AsteroidsGameDAO.queryLevel(currentLevelNumber));
            currentLevel = asteroidsGame.getCurrentLevel();
        }
        contentIds = new ArrayList<>();
        bgSoundIsPlaying = false;
        newLevel = true;
        levelTransition = new LevelTransition();
        asteroidsGame.getCompleteShip().setScale(.15f);
        win = false;
    }
    /**
     * Updates the gameDelegate. This controls the whole game. There are multiple flags in this function:
     *
     *
     * Boolean win:
     * - if the user won the game, this function will just return.
     *
     * If the user runs out of lives:
     * - game just returns
     *
     * If the background sound is not playing:
     * - starts playing the bg loop sound, sets the boolean to true.
     *
     * If the number of asteroids is 0 (the user destroyed all):
     * - loads the next level, resets all of the data (ship position, clears the projectiles)
     *
     * Boolean newLevel:
     * - Goes to the transition screen.
     *
     * Then calls update on the actual game.
     *
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime) {

        if (win || asteroidsGame.getCompleteShip().getLives() == 0)
        {
            return;
        }
/*        if (!bgSoundIsPlaying)
        {
            ContentManager.getInstance().playLoop(bgSoundId);
            bgSoundIsPlaying = true;
        }*/
        if (asteroidsGame.getCurrentLevel().getAsteroids().size() == 0)
        {
            currentLevelNumber++;
            if (currentLevelNumber == 4)
            {
                win = true;
                return;
            }
            currentLevel = AsteroidsGameDAO.queryLevel(currentLevelNumber);
            asteroidsGame.setCurrentLevel(currentLevel);
            unloadContent(ContentManager.getInstance());
            loadContent(ContentManager.getInstance());
            asteroidsGame.getCompleteShip().setAngle(0);
            asteroidsGame.getCompleteShip().getCannon().resetProjectiles();
            newLevel = true;
        }
        if (newLevel)
        {
            levelTransition.setLevelNumber(currentLevelNumber);
            levelTransition.setLevelHint(currentLevel.getLevelHint());
            levelTransition.update(elapsedTime);
            if (levelTransition.getTransitionDuration() <= 0)
            {
                newLevel = false;
                levelTransition.resetTransitionDuration();
            }
        }
        else {
            asteroidsGame.update(elapsedTime);
        }

    }

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivity will
     * call this once right before entering the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
    @Override
    public void loadContent(ContentManager content) {

        currentLevel = asteroidsGame.getCurrentLevel();
        ArrayList<AsteroidType> levelAsteroids = currentLevel.getAsteroids();
        for (AsteroidType l : levelAsteroids)
        {
            try {
                contentIds.add(content.loadImage(l.getImagePath()));
            }
            catch (NullPointerException n)
            {
                //n.printStackTrace();
            }
        }
        ArrayList<LevelObjects> levelObjects = currentLevel.getLevelObjectsArrayList();
        for (LevelObjects l : levelObjects)
        {
            contentIds.add(content.loadImage(l.getImagePath()));
        }
        try {
            bgSoundId = content.loadLoopSound(currentLevel.getLevelMusicPath());
            ContentManager.getInstance().playLoop(bgSoundId);

        } catch (IOException e) {
            //e.printStackTrace();
        }

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
/*
        for (Integer i : contentIds)
        {
            content.unloadImage(i);
        }
        content.unloadLoopSound(bgSoundId);*/

    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     *
     * If the ship lives are 0:
     *  - Draws a screen saying Game Over, and returns
     *
     * If it is a new level:
     *  - Calls draw on the respective levelTransition
     *
     * If the user won the game:
     *  - Draws a black screen with the text: "Congratulations, you win"
     *
     *  Else
     *  - Calls draw on the actual game.
     */
    @Override
    public void draw() {
        if (Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getLives() == 0)
        {
            DrawingHelper.drawFilledRectangle(new Rect(0,0, DrawingHelper.getGameViewWidth(), DrawingHelper.getGameViewHeight()), Color.BLACK, 255);
            DrawingHelper.drawCenteredText("Game Over", 50, Color.WHITE);
        }
        else if (newLevel)
        {
            levelTransition.draw();
        }
        else if (win)
        {
            DrawingHelper.drawFilledRectangle(new Rect(0,0, DrawingHelper.getGameViewWidth(), DrawingHelper.getGameViewHeight()), Color.BLACK, 255);
            DrawingHelper.drawCenteredText("Congratulations, you win!", 50, Color.WHITE);
        }
        else {
            asteroidsGame.draw();
        }
    }
}
