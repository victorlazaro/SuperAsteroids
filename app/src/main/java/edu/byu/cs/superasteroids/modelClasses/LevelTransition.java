package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.Color;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 3/3/2016.
 */
public class LevelTransition {

    private int levelNumber;
    private String levelHint;
    private double transitionDuration;

    public LevelTransition() {
        transitionDuration = 5;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void setLevelHint(String levelHint) {
        this.levelHint = levelHint;
    }

    public double getTransitionDuration() {
        return transitionDuration;
    }

    public void resetTransitionDuration() {
        transitionDuration = 5;
    }

    /**
     * Will call update on all visible objects in the game every 1/60th of a second
     *
     * @param elapsedTime
     */
    public void update(double elapsedTime) {

        if (transitionDuration > 0)
        {
            transitionDuration -= elapsedTime;
        }

    }

    public void draw() {


        DrawingHelper.drawFilledRectangle(new Rect(0, 0, DrawingHelper.getGameViewWidth(), DrawingHelper.getGameViewHeight()), Color.BLACK, 255);
        String level = "Level " + levelNumber + " - " + levelHint;
        DrawingHelper.drawCenteredText(level, 50, Color.WHITE);
    }
}
