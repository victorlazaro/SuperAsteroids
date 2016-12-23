package edu.byu.cs.superasteroids.modelClasses;


/**
 * Created by Victor on 2/4/2016.
 * This class represents a single OcteroidAsteroid
 */
public class OcteroidAsteroid extends AsteroidType {

    public OcteroidAsteroid(String imagePath, int imageHeight, int imageWidth, String asteroidName, String type) {
        super(imagePath, imageHeight, imageWidth, asteroidName, type);
        setScale(1);
        setSplitsInto(8);

    }




}
