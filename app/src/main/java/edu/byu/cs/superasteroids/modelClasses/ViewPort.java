package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/13/2016.
 * This class represents the portion of the space that is seen on the screen at a given moment.
 */
public class ViewPort extends Visible {

    /** Dimensions of the viewPort */
    public static RectF worldDimensions;
    public static RectF viewPortWorldPosition;
    private static AsteroidsGame asteroidsGame;
    private static float viewPortHeight;
    private static float viewPortWidth;
    /**Dimensions of the miniMap*/
    public static RectF miniMapDimensions;
    public static RectF viewPortDimensions;


    public ViewPort(String imagePath, int imageHeight, int imageWidth) {
        super(imagePath, imageHeight, imageWidth);
        asteroidsGame = edu.byu.cs.superasteroids.core.Asteroids.getInstance().getAsteroidsGame();
    }


    public static void setWorldDimensions(int levelHeight, int levelWidth) {
        worldDimensions = new RectF(0,0,levelWidth, levelHeight);
    }



    /**
     * This function moves the viewPort, while keeping the ship centered, when possible.
     *
     * In order to do that, it moves the viewPort, then checks to see if it went out of bounds. If it did,
     * then that specific direction is reset to the previous one.
     */
    public static void moveViewPort()
    {
        RectF currentViewPort = viewPortWorldPosition;

        setNewViewPortPosition();

        if (viewPortWorldPosition.right > worldDimensions.right) {

            viewPortWorldPosition.right = currentViewPort.right;
            viewPortWorldPosition.left = viewPortWorldPosition.right - viewPortWidth;
        }
        else if (viewPortWorldPosition.left < worldDimensions.left)
        {
            viewPortWorldPosition.left = currentViewPort.left;
            viewPortWorldPosition.right = viewPortWorldPosition.left + viewPortWidth;

        }
        if (viewPortWorldPosition.top < worldDimensions.top)
        {
            viewPortWorldPosition.top = currentViewPort.top;
            viewPortWorldPosition.bottom = viewPortWorldPosition.top + viewPortHeight;

        }
        else if (viewPortWorldPosition.bottom > worldDimensions.bottom)
        {
            viewPortWorldPosition.bottom = currentViewPort.bottom;
            viewPortWorldPosition.top = viewPortWorldPosition.bottom - viewPortHeight;
        }


    }

    private static void setNewViewPortPosition() {

        PointF shipPosition = asteroidsGame.getCompleteShip().getPositionCoordinates();

        viewPortWorldPosition = new RectF(shipPosition.x - viewPortWidth/2, shipPosition.y - viewPortHeight/2, shipPosition.x+viewPortWidth/2, shipPosition.y + viewPortHeight/2);

    }


    public static void initializeViewPort() {

        viewPortHeight = DrawingHelper.getGameViewHeight();
        viewPortWidth = DrawingHelper.getGameViewWidth();
        initializeViewPortWorldPosition();

    }

    /**
     * This function initializes the ViewPort.
     */
    public static void initializeViewPortWorldPosition() {
        viewPortWorldPosition = new RectF(((worldDimensions.right - viewPortDimensions.width())/2),
                ((worldDimensions.bottom - viewPortDimensions.height())/2),
                ((worldDimensions.right - viewPortDimensions.width())/2 + viewPortDimensions.width()),
                ((worldDimensions.bottom - viewPortDimensions.height())/2) + viewPortDimensions.height());
    }

    public void update(double elapsedTime) {

    }

    @Override
    public void draw() {
    }

    /**
     * Translates a point from world coordinates into viewPort coordinates
     * @param objectPosition position of the object in the world
     * @return A point in viewPort coordinates
     */
    public static PointF worldToViewPort(PointF objectPosition)
    {
        return GraphicsUtils.subtract(objectPosition, new PointF(viewPortWorldPosition.left, viewPortWorldPosition.top));
    }
    /**
     * Converts a point in the world into a point in the miniMap
     * @param scaledObjectPosition the object position, already scaled down
     * @return A point in viewPort coordinates that fits into the MiniMap
     */
    public static PointF worldToMiniMap(PointF scaledObjectPosition)
    {
        return new PointF(scaledObjectPosition.x + miniMapDimensions.left, scaledObjectPosition.y);
    }

    public static RectF getViewPortWorldDimensions() {
        return viewPortWorldPosition;
    }


    public static RectF getWorldDimensions() {
        return worldDimensions;
    }

    public static RectF getMiniMapDimensions() {
        return miniMapDimensions;
    }

    public static void setViewPortDimensions() {
        viewPortDimensions = new RectF(0,0, DrawingHelper.getGameViewWidth(), DrawingHelper.getGameViewHeight());
    }
}
