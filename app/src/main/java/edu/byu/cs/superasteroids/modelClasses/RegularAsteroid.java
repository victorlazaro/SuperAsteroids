package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/4/2016.
 * This class represents a regularAsteroid
 */
public class RegularAsteroid extends AsteroidType {


    /**
     * Constructor for a Regular Asteroid, which is basically the same as any other asteroid, except its scale is .5f and it
     * splits into 2 once it's hit.
     */
    public RegularAsteroid(String imagePath, int imageHeight, int imageWidth, String asteroidName, String type) {
        super(imagePath, imageHeight, imageWidth, asteroidName, type);
        setScale(.5f);
        setSplitsInto(2);
    }
}
