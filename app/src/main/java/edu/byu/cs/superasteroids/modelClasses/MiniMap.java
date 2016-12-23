package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/4/2016.
 * This class is related to the miniMap on the corner of the screen
 */
public class MiniMap extends PositionedObject {

    private ArrayList<AsteroidType> asteroids;

    public MiniMap(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);

        setScale(.06f);
        asteroids = new ArrayList<>();
    }

    @Override
    public void update(double elapsedTime) {
    }

    /**
     * Draws the miniMap rectangle, the ship, the asteroids and the viewPort
     */
    @Override
    public void draw() {

        drawMiniMap();
        drawShip();
        drawAsteroids();
        drawViewPort();

    }

    private void drawViewPort() {

        Rect scaledRectangle = GraphicsUtils.scaleRectangle(ViewPort.getViewPortWorldDimensions(), getScale());

        Rect viewPortInMiniMap = new Rect(Math.round(scaledRectangle.left + getPositionCoordinates().x),
                Math.round(scaledRectangle.top + getPositionCoordinates().y),
                Math.round(scaledRectangle.right + getPositionCoordinates().x),
                Math.round(scaledRectangle.bottom + getPositionCoordinates().y));

        DrawingHelper.drawRectangle(viewPortInMiniMap, Color.WHITE, 255);


    }

    private void drawShip() {

        DrawingHelper.drawPoint(ViewPort.worldToMiniMap
                (GraphicsUtils.scale(Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getPositionCoordinates(),
                        getScale())), 5, Color.GREEN, 255);
    }

    private void drawAsteroids() {

        for(int i = 0; i < asteroids.size(); i++)
        {
            DrawingHelper.drawPoint(ViewPort.worldToMiniMap
                            (GraphicsUtils.scale(asteroids.get(i).getPositionCoordinates(), getScale())), 3, Color.RED, 255);
        }


    }

    private void drawMiniMap() {

        Rect pixelPosition = GraphicsUtils.rectFToRect(ViewPort.getMiniMapDimensions());
            DrawingHelper.drawRectangle(pixelPosition, Color.WHITE, 255);

    }

    public void setAsteroids(ArrayList<AsteroidType> asteroids) {
        this.asteroids = asteroids;
    }

    public void initializeMiniMap()
    {
        asteroids = Asteroids.getInstance().getAsteroidsGame().getCurrentLevel().getAsteroids();

        setBounds(new Rect(Math.round(DrawingHelper.getGameViewWidth() - getImageWidth()*getScale()),
                0, Math.round(DrawingHelper.getGameViewWidth()-1), Math.round(getImageHeight()*getScale())));

        setPositionCoordinates(new PointF(getBounds().left, getBounds().top));

        ViewPort.miniMapDimensions = new RectF(Math.round(getBounds().left),
                Math.round(getBounds().top), Math.round(getBounds().right), Math.round(getBounds().bottom));
    }
}
