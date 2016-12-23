package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;
import android.graphics.RectF;

import junit.framework.TestCase;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

import static edu.byu.cs.superasteroids.modelClasses.ViewPort.*;



public class ViewPortTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        worldDimensions = new RectF(0,0,1000,1000);
        viewPortWorldPosition = new RectF(400,400,600,600);
        viewPortDimensions = new RectF(0,0,200,200);
        miniMapDimensions = new RectF(viewPortDimensions.right - worldDimensions.right*0.01f,
                viewPortWorldPosition.top, viewPortDimensions.right - 1,
                viewPortWorldPosition.top + worldDimensions.bottom*.01f);
    }

    public void testWorldToMiniMap()
    {
        //top left corner of world
        assertEquals(new PointF(190,0), worldToMiniMap(GraphicsUtils.scale(new PointF(0, 0), .01f)));
        //top right corner of world
        assertEquals(new PointF(200,0), worldToMiniMap(GraphicsUtils.scale(new PointF(1000,0), .01f)));
        //bottom left corner of world
        assertEquals(new PointF(190,10), worldToMiniMap(GraphicsUtils.scale(new PointF(0, 1000), .01f)));
        //bottom right corner of world
        assertEquals(new PointF(200,10), worldToMiniMap(GraphicsUtils.scale(new PointF(1000, 1000), .01f)));
    }

    public void testWorldToViewPort()
    {
        //center of the world
        assertEquals(new PointF(100,100), worldToViewPort(new PointF(500,500)));
        //viewPort top left corner
        assertEquals(new PointF(0,0), worldToViewPort(new PointF(400,400)));
        //world top left corner
        assertEquals(new PointF(-400,-400), worldToViewPort(new PointF(0,0)));
        //point (600,400) in the world
        assertEquals(new PointF(200,0), worldToViewPort(new PointF(600,400)));
        //world bottom right corner
        assertEquals(new PointF(600,600), worldToViewPort(new PointF(1000,1000)));
        //viewPort bottom left corner
        assertEquals(new PointF(0,200), worldToViewPort(new PointF(400,600)));
        //viewPort top right corner
        assertEquals(new PointF(200,0), worldToViewPort(new PointF(600,400)));
    }

}
