package edu.byu.cs.superasteroids.modelClasses;

import android.graphics.PointF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.Asteroids;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Victor on 2/2/2016.
 * This class represents a single CannonObject
 */
public class CannonObject extends AttachedObjects {

    private PointF emitPoint;
    private String attackImage;
    private int damage;
    private PointF emitOffset;
    private PointF rotatedOffset;
    private int attackImageWidth;
    private int attackImageHeight;
    private String attackSound;
    private ArrayList<Projectile> projectiles;

    public CannonObject(String imagePath, int imageHeight, int imageWidth, PointF attachPoint,
                      PointF emitPoint, String attackImage, int attackImageHeight, int attackImageWidth,
                      String attackSound, int damage) {
        super(imagePath, imageHeight, imageWidth, attachPoint);
        this.emitPoint = emitPoint;
        this.attackImage = attackImage;
        this.attackImageHeight = attackImageHeight;
        this.attackImageWidth = attackImageWidth;
        this.attackSound = attackSound;
        this.damage = damage;
        projectiles = new ArrayList<>();
        emitOffset = GraphicsUtils.scale(GraphicsUtils.subtract(emitPoint, new PointF(getImageWidth()/2, getImageHeight()/2)), getScale());
        rotatedOffset = null;
    }



    public String getAttackSound() {
        return attackSound;
    }
    public String getAttackImage() {
        return attackImage;
    }
    public int getAttackImageWidth() {
        return attackImageWidth;
    }

    public int getAttackImageHeight() {
        return attackImageHeight;
    }

    public int getDamage() {
        return damage;
    }
    public PointF getEmitPoint() {
        return emitPoint;
    }

    /**
     * Shoots a projectile from the cannon.
     * Creates a projectile object, sets all of its information, and adds it to the list of projectiles
     *
     * @param angleDegrees the angle in degrees that the projectile should be launched.
     * */
    public void shootProjectile(double angleDegrees) {

        Projectile newProjectile = new Projectile(attackImage, attackImageHeight, attackImageWidth);
        newProjectile.setPositionCoordinates(GraphicsUtils.add(getPositionCoordinates(),rotatedOffset));
        newProjectile.setBounds();
        newProjectile.setAngle((float) GraphicsUtils.degreesToRadians(angleDegrees));
        newProjectile.setScale(.1f);
        newProjectile.setSpeed(500);
        projectiles.add(newProjectile);


    }

    /**
     * Calls update on all projectiles. If any of them went out of bounds or hit an asteroid, it is removed from the list
     *
     */
    @Override
    public void update(double elapsedTime) {

        for (int i = 0; i < projectiles.size(); i++)
        {
            projectiles.get(i).update(elapsedTime);
            if (projectiles.get(i).hitAsteroid || projectiles.get(i).outOfBounds)
            {
                projectiles.remove(projectiles.get(i));
            }

        }

    }


    @Override
    public void draw()
    {
        drawCannon();
        drawProjectiles();

    }

    private void drawProjectiles() {

        for (Projectile p : projectiles)
        {
            p.draw();
        }

    }

    private void drawCannon() {

        PointF viewPortPosition = ViewPort.worldToViewPort(new PointF(getPositionCoordinates().x, getPositionCoordinates().y));
        DrawingHelper.drawImage(ContentManager.getInstance().getImageId(getImagePath()),
                viewPortPosition.x, viewPortPosition.y,
                Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(),
                getScale(), getScale(), 255);
    }

    /**
     * When the cannon is rotated, the offset between the center of the cannon and the emit point is also
     * rotated.
     * @param rotationAngle The ship rotation angle in radians
     * @param mainBodyAttachPoint The cannon attach point on the main body
     */
    @Override
    public void rotate(double rotationAngle, PointF mainBodyAttachPoint)
    {
        super.rotate(rotationAngle, mainBodyAttachPoint);

        rotatedOffset = GraphicsUtils.rotate(emitOffset, rotationAngle);


    }


    public void drawShipBuilding() {

        DrawingHelper.drawImage(ContentManager.getInstance().
                getImageId(getImagePath()), getPositionCoordinates().x, getPositionCoordinates().y
                , Asteroids.getInstance().getAsteroidsGame().getCompleteShip().getAngle(), getScale(), getScale(), 255);

    }

    public void resetProjectiles() {
        projectiles.clear();
    }
}
