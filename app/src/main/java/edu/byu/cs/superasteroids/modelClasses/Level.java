package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a full single level
 */
public class Level {

    private int levelNumber;
    private String levelTitle;
    private String levelHint;
    private int levelHeight;
    private int levelWidth;
    private String levelMusicPath;
    private ArrayList<LevelObjects> levelObjectsArrayList;
    private ArrayList<AsteroidType> asteroids;
    private ViewPort viewPort;
    private MiniMap miniMap;

    public Level(int levelNumber, String levelTitle, String levelHint, int levelHeight, int levelWidth,
                 String levelMusicPath, ArrayList<LevelObjects> levelObjectsArrayList,
                 ArrayList<AsteroidType> asteroids)
    {
        this.levelNumber = levelNumber;
        this.levelTitle = levelTitle;
        this.levelHint = levelHint;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.levelMusicPath = levelMusicPath;
        this.asteroids = asteroids;
        this.levelObjectsArrayList = levelObjectsArrayList;
        if (Asteroids.getInstance() != null) {
            Ship ship = Asteroids.getInstance().getAsteroidsGame().getCompleteShip();
            ship.setPositionCoordinates(new PointF(levelWidth / 2, levelHeight / 2));
            ship.setScale(.15f);
            ship.setLives(3);
            viewPort = new ViewPort("", 0, 0);
            ViewPort.setWorldDimensions(levelHeight, levelWidth);
            miniMap = new MiniMap("", levelHeight, levelWidth);
            ViewPort.setWorldDimensions(levelHeight, levelWidth);
            Asteroids.getInstance().getAsteroidsGame().setFirstLoop(true);
        }

    }

    public ArrayList<LevelObjects> getLevelObjectsArrayList() {
        return levelObjectsArrayList;
    }
    public String getLevelHint() {
        return levelHint;
    }
    public ArrayList<AsteroidType> getAsteroids() {
        return asteroids;
    }
    public String getLevelMusicPath() {
        return levelMusicPath;
    }
    public MiniMap getMiniMap() {
        return miniMap;
    }


    public void draw()
    {
        drawObjects();
        drawAsteroids();
        viewPort.draw();
        miniMap.draw();
        DrawingHelper.drawText(new Point(0, 13), "Level " + levelNumber, Color.GREEN, 15);
        DrawingHelper.drawText(new Point(0, 25), "Lives left: " + Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getLives(), Color.GREEN, 15);

    }

    private void drawObjects() {

        for (int i = 0; i < levelObjectsArrayList.size(); i++)
        {
            levelObjectsArrayList.get(i).draw();
        }
    }

    /**
     * Draws the asteroid if it intersects the viewPort rect.
     */
    private void drawAsteroids() {

        Rect viewPortRect = GraphicsUtils.rectFToRect(ViewPort.getViewPortWorldDimensions());
        for (int i = 0; i < asteroids.size(); i++)
        {
            if (asteroids.get(i).getBounds().intersect(viewPortRect)) {
                asteroids.get(i).draw();
            }
        }
    }

    public void update(double elapsedTime) {

        updateAsteroids(elapsedTime);
        viewPort.update(elapsedTime);
        miniMap.update(elapsedTime);

    }

    private void updateAsteroids(double elapsedTime) {

        for (int i = 0; i < asteroids.size(); i++)
        {
            asteroids.get(i).update(elapsedTime);
            if (asteroids.get(i).isCollided())
            {
                asteroids.remove(i);
            }
        }

    }



    public void insertAsteroid(AsteroidType asteroid) {

        asteroids.add(asteroid);

    }
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (o.getClass() != getClass())
        {
            return false;
        }
        Level level = (Level) o;
        return (level.levelNumber != levelNumber || !level.levelHint.equals(levelHint) ||
             level.levelHeight != levelHeight || level.levelWidth != levelWidth || !level.levelTitle.equals(levelTitle) ||
                !level.levelMusicPath.equals(levelMusicPath));

    }
}
