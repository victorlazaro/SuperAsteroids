package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/4/2016.
 * This class is shared by all objects that are visible at some moment on the screen
 */
public abstract class Visible extends Image {

    private float scale;
    public Visible(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        scale = .4f;
    }


    /**
     * Will call update on all visible objects in the game every 1/60th of a second
     */
    public abstract void update(double elapsedTime);
    public abstract void draw();

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
