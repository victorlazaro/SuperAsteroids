package edu.byu.cs.superasteroids.modelClasses;


/**
 * Created by Victor on 2/8/2016.
 */
public class LevelAsteroids {

    private int asteroidQuantity;
    private int asteroidId;

    public LevelAsteroids(int asteroidQuantity, int asteroidId){
        this.asteroidQuantity = asteroidQuantity;
        this.asteroidId = asteroidId;
    }

    public int getAsteroidQuantity() {
        return asteroidQuantity;
    }
    public int getAsteroidId() {
        return asteroidId;
    }

}
