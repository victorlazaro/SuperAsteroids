package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/4/2016.
 * This class represents the Background object
 */
public class BackgroundObject extends Visible {


    public BackgroundObject(String imagePath) {
        super(imagePath, 0,0);
    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void draw() {

    }
}
