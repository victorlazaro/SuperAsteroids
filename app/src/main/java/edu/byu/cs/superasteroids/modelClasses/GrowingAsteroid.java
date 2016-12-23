package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/4/2016.
 * This class represents a GrowingAsteroid. It implements all functions of the static classes it inherits from.
 */
public class GrowingAsteroid extends AsteroidType {


    public GrowingAsteroid(String imagePath, int imageHeight, int imageWidth, String asteroidName, String type) {
        super(imagePath, imageHeight, imageWidth, asteroidName, type);
        setSplitsInto(2);
        setGrowingRate(.1f);

    }

}
