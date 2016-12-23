package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.Rect;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/4/2016.
 * This class represents the whole space
 */
public class Space extends Visible {

    private int backgroundImageId;
    private Rect backgroundImage;


    public Space(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        backgroundImageId = -1;
        backgroundImage = new Rect(0,0,getImageWidth(), getImageHeight());
    }

    /**
     * Will call update on all visible objects in the game every 1/60th of a second
     *
     */

    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {

        if (backgroundImageId == -1)
        {
            backgroundImageId = ContentManager.getInstance().loadImage(getImagePath());
        }
        DrawingHelper.drawImage(backgroundImageId, backgroundImage, null);

    }



}
