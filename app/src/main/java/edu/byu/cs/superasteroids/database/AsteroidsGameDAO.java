package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.byu.cs.superasteroids.modelClasses.AsteroidType;
import edu.byu.cs.superasteroids.modelClasses.BackgroundObject;
import edu.byu.cs.superasteroids.modelClasses.CannonObject;
import edu.byu.cs.superasteroids.modelClasses.EngineObject;
import edu.byu.cs.superasteroids.modelClasses.ExtraPartsObject;
import edu.byu.cs.superasteroids.modelClasses.GrowingAsteroid;
import edu.byu.cs.superasteroids.modelClasses.Image;
import edu.byu.cs.superasteroids.modelClasses.Level;
import edu.byu.cs.superasteroids.modelClasses.LevelAsteroids;
import edu.byu.cs.superasteroids.modelClasses.LevelObjects;
import edu.byu.cs.superasteroids.modelClasses.MainBodyObject;
import edu.byu.cs.superasteroids.modelClasses.OcteroidAsteroid;
import edu.byu.cs.superasteroids.modelClasses.PowerCoreObject;
import edu.byu.cs.superasteroids.modelClasses.RegularAsteroid;

/**
 * Created by Victor on 2/9/2016.
 * This class manages the database. It contains insert functions for all tables, as well as query functions
 */
public class AsteroidsGameDAO {

    private static SQLiteDatabase database;

    public AsteroidsGameDAO (SQLiteDatabase database) {
        AsteroidsGameDAO.database = database;
    }

    /**
     * Creates an instance of the database, begins a transaction. The image information (path, width and height) are stored into a
     * separate table (images), and the key of that row is returned, so that it can be referenced from the asteroids table. Then,
     * asteroidName, asteroidType and the foreignKey are stored into the asteroids table.
     * Transaction set as successful, then end.
     *
     * @param asteroidName name of the Asteroid
     * @param asteroidType type of the Asteroid
     * @param imagePath path to the Asteroid image
     * @param imageWidth width of the Asteroid image
     * @param imageHeight height of the Asteroid image
     */
    public static long insertAsteroid(String asteroidName, String asteroidType, String imagePath, int imageWidth, int imageHeight) {

        long imageId = insertImage(imagePath, imageWidth, imageHeight);
        ContentValues contentValues = new ContentValues();

        contentValues.put("asteroidName", asteroidName);
        contentValues.put("imageIdForKey", imageId);
        contentValues.put("asteroidType", asteroidType);

        return database.insert("asteroids", null, contentValues);



    }

    /**
     * Inserts a cannon into the cannon table.
     * First, it inserts the imageId into the image table, getting the respective row id in return
     * Then, it adds the attackId into the image table, getting the respective row id in return
     * Begins transaction, putting the attachPoint, emitPoint, attackSoundPath, damage, and the foreign keys imageId and attackId into
     * the cannon table
     * Sets the transaction as successful, ends transaction
     *
     * @param attachPoint point on the cannon that attaches to the main body of the ship
     * @param emitPoint point on the cannon from which the projectile is shot
     * @param imagePath path of the cannon image
     * @param imageWidth width of the cannon image
     * @param imageHeight height of the cannon image
     * @param attackImagePath path of the attack image
     * @param attackImageWidth width of the attack image
     * @param attackImageHeight height of the attack image
     * @param attackSoundPath sound path of the attack
     * @param damage amount of damage caused by the shot
     */
    public static long insertCannon(String attachPoint, String emitPoint, String imagePath, int imageWidth,
                             int imageHeight, String attackImagePath, int attackImageWidth, int attackImageHeight,
                             String attackSoundPath, int damage) {


        long imageId = insertImage(imagePath, imageWidth, imageHeight);
        long attackId = insertImage(attackImagePath, attackImageWidth, attackImageHeight);

        ContentValues contentValues = new ContentValues();

        contentValues.put("attachPoint", attachPoint);
        contentValues.put("emitPoint", emitPoint);
        contentValues.put("attackSoundPath", attackSoundPath);
        contentValues.put("damage", damage);
        contentValues.put("imageIdForKey", imageId);
        contentValues.put("attackImageIdForKey", attackId);

        return database.insert("cannons", null, contentValues);
    }
    /**
     * Creates an instance of the database, begins a transaction.
     * Inserts an engine into the database. The image information (path to file, width and height) are stored into a separate table(images),
     * and the id related to that row is returned and stored as a foreign key in the engine table, so that we can reference to the image
     * info whenever it is needed.
     * sets the transaction successful, and ends it.
     * @param baseSpeed speed of the engine
     * @param baseTurnRate turn rate of the engine
     * @param attachPoint point on the engine that will be attached to the spaceship
     * @param imagePath path to the engine image
     * @param imageWidth width of the engine image
     * @param imageHeight height of the engine image
     */
    public static long insertEngine(int baseSpeed, int baseTurnRate, String attachPoint, String imagePath, int imageWidth,
                             int imageHeight) {
        ContentValues contentValues = new ContentValues();
        long imageId = insertImage(imagePath, imageWidth, imageHeight);
        contentValues.put("baseSpeed", baseSpeed);
        contentValues.put("baseTurnRate", baseTurnRate);
        contentValues.put("attachPoint", attachPoint);
        contentValues.put("imageIdForKey", imageId);

        return database.insert("engines", null, contentValues);

    }

    /**
     * Inserts an ExtraParts row into the database extraParts table
     * @param attachPoint point on the extra part that will be attached to the spaceship
     * @param imagePath path to the extra part image
     * @param imageWidth width of the extra part image
     * @param imageHeight height of the extra part image
     */
    public static long insertExtraParts(String attachPoint, String imagePath, int imageWidth, int imageHeight) {

        ContentValues contentValues = new ContentValues();
        ContentValues imageValues = new ContentValues();
        imageValues.put("imagePath", imagePath);
        imageValues.put("imageWidth", imageWidth);
        imageValues.put("imageHeight", imageHeight);

        long imageId = database.insert("imagesInfo", null, imageValues);

        contentValues.put("attachPoint", attachPoint);
        contentValues.put("imageIdForKey", imageId);

        return database.insert("extraParts", null, contentValues);
    }

    /**
     * Inserts an Image into the database with the given information from the DataImporter. It returns the primary key(id) of that
     * row, which becomes the foreign key for all objects that contain this image
     *
     * @param imagePath   the path to the image
     * @param imageWidth  the width of the image
     * @param imageHeight the height of the image
     * @return id, a long
     */
    public static long insertImage(String imagePath, int imageWidth, int imageHeight) {

        ContentValues imageValues = new ContentValues();
        imageValues.put("imagePath", imagePath);
        imageValues.put("imageWidth", imageWidth);
        imageValues.put("imageHeight", imageHeight);
        return database.insert("imagesInfo", null, imageValues);

    }

    /**
     * inserts imagePath into the backgroundObjects table
     * @param imagePath the path to the image
     */
    public static long insertBackgroundObjects(String imagePath) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("imagePath", imagePath);
        return database.insert("backgroundObjects", null, contentValues);
    }

    /**
     * Inserts a LevelAsteroid into the table, containing the number of Asteroids in that level, and the id of the respective asteroid
     * @param levelNumber the number of the related level
     * @param numberOfAsteroids number of asteroids in that level
     * @param asteroidId id of the asteroids in that level
     */
    public static long insertLevelAsteroids(int levelNumber, int numberOfAsteroids, int asteroidId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("level_id", levelNumber);
        contentValues.put("numberOfAsteroids", numberOfAsteroids);
        contentValues.put("asteroidId", asteroidId);
        return database.insert("levelAsteroids", null, contentValues);

    }

    /**
     * Inserts a Level into the Levels table. This function creates a link between a Level and a LevelAsteroid(number of asteroids and asteroidId), and
     * a link between a Level and an Object.
     * @param levelNumber level of the number
     * @param levelTitle title of the level
     * @param levelHint hint of the level
     * @param levelWidth width of the level
     * @param levelHeight height of the level
     * @param musicPath path to the music pertaining to that level
     */
    public static long insertLevel(int levelNumber, String levelTitle, String levelHint, int levelWidth,
                            int levelHeight, String musicPath) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("levelNumber", levelNumber);
        contentValues.put("levelTitle", levelTitle);
        contentValues.put("levelHint", levelHint);
        contentValues.put("levelWidth", levelWidth);
        contentValues.put("levelHeight", levelHeight);
        contentValues.put("levelMusic", musicPath);

        return database.insert("levels", null, contentValues);

    }


    /**
     * Inserts a Level into the levelObject table
     * @param levelId the id of the related level
     * @param position the position that the object is supposed to be in
     * @param objectId the id of the object
     * @param scale the scale in which the object will be presented
     */
    public static long insertLevelObject(int levelId, String position, int objectId, float scale) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("level_id", levelId);
        contentValues.put("position", position);
        contentValues.put("object_id", objectId); // this is the id of the backgroundObjectType
        contentValues.put("scale", scale);

        return database.insert("levelObjects", null, contentValues);
    }

    /**
     * Inserts a MainBody into the database. It inserts the imagePath, width and height into the imagesInfo table, returning the foreign key,
     * and then storing that key into the mainBodies table.
     * @param cannonAttach the point in which the cannon will be attached
     * @param engineAttach the point in which the engine will be attached
     * @param extraAttach the point in which the extra part will be attached
     * @param imagePath the path to the image of the main body
     * @param imageWidth the width of the main body image
     * @param imageHeight the height of the main body image
     */
    public static long insertMainBody(String cannonAttach, String engineAttach, String extraAttach, String imagePath,
                               int imageWidth, int imageHeight) {

        ContentValues values = new ContentValues();
        values.put("cannonAttach", cannonAttach);
        values.put("engineAttach", engineAttach);
        values.put("extraAttach", extraAttach);
        ContentValues imageValues = new ContentValues();
        imageValues.put("imagePath", imagePath);
        imageValues.put("imageWidth", imageWidth);
        imageValues.put("imageHeight", imageHeight);
        long id = database.insert("imagesInfo", null, imageValues);
        values.put("imageIdForKey", id);
        return database.insert("mainBodies", null, values);
    }
    /**
     * inserts a power core into the database
     * @param cannonBoost the boost of the cannon
     * @param engineBoost the boost of the engine
     * @param imagePath the path to the power core image
     */
    public static long insertPowerCore(int cannonBoost, int engineBoost, String imagePath) {


        ContentValues contentValues = new ContentValues();
        contentValues.put("cannonBoost", cannonBoost);
        contentValues.put("engineBoost", engineBoost);
        contentValues.put("imagePath", imagePath);

        return database.insert("powerCores", null, contentValues);
    }

    /**
     * Retrieves asteroid based upon the asteroid Id
     *
     * @param asteroidId id of the requested Asteroid
     * @return AsteroidType with all of the information
     */
    public static AsteroidType queryAsteroid(int asteroidId) {
        final String SQL = "SELECT * " +
                "FROM asteroids " +
                "WHERE id = ?";

        Cursor cursor = database.rawQuery(SQL, new String[]{Integer.toString(asteroidId)});
        try {
            cursor.moveToFirst();
            Image image = queryImage(cursor.getInt(cursor.getColumnIndex("imageIdForKey")));
            String asteroidType = cursor.getString(cursor.getColumnIndex("asteroidType"));
            if (asteroidType.equals("regular")) {
                return new RegularAsteroid(image.getImagePath(), image.getImageHeight(), image.getImageWidth(),
                        cursor.getString(1), cursor.getString(3));
            } else if (asteroidType.equals("growing")) {
                return new GrowingAsteroid(image.getImagePath(), image.getImageHeight(), image.getImageWidth(),
                        cursor.getString(1), cursor.getString(3));
            } else if (asteroidType.equals("octeroid")) {

                return new OcteroidAsteroid(image.getImagePath(), image.getImageHeight(), image.getImageWidth(),
                        cursor.getString(1), cursor.getString(3));
            }

        } finally {
            cursor.close();
        }
        return null;
    }


    public static List<CannonObject> getAllCannons()
    {
        List<CannonObject> cannonObjectList = new ArrayList<>();
        final String SQL = "SELECT * FROM cannons";
        Cursor cursor = database.rawQuery(SQL, null);
        try{
            while(cursor.moveToNext()) {
                Image image = queryImage(cursor.getInt(3));
                Image attackImage = queryImage(cursor.getInt(4));
                String attachPoint = cursor.getString(1);
                String[] attachXAndY = attachPoint.split(",");
                PointF attachPointF = new PointF(Integer.parseInt(attachXAndY[0]), Integer.parseInt(attachXAndY[1]));
                String emmitPoint = cursor.getString(2);
                String[] emmitPointXandY = emmitPoint.split(",");
                PointF emitPointF = new PointF(Integer.parseInt(emmitPointXandY[0]), Integer.parseInt(emmitPointXandY[1]));
                String attackSoundPath = cursor.getString(5);
                int damage = cursor.getInt(6);
                cannonObjectList.add(new CannonObject(image.getImagePath(), image.getImageHeight(), image.getImageWidth(),
                        attachPointF, emitPointF, attackImage.getImagePath(), attackImage.getImageHeight(),
                        attackImage.getImageWidth(), attackSoundPath, damage));
            }
        }finally {
            cursor.close();
        }
        return cannonObjectList;
    }

    public static List<ExtraPartsObject> getAllExtraParts()
    {
        List<ExtraPartsObject> extraPartsObjectList = new ArrayList<>();
        final String extraPartsQuery = "SELECT * FROM extraParts";
        Cursor extraPartsCursor = database.rawQuery(extraPartsQuery, null);
        try{
            while(extraPartsCursor.moveToNext()) {
                Image image = queryImage(extraPartsCursor.getInt(2));
                PointF extraPartsAttachPoint = new PointF((Integer.parseInt(extraPartsCursor.getString(1).split(",")[0])),
                        Integer.parseInt(extraPartsCursor.getString(1).split(",")[1]));
                extraPartsObjectList.add(new ExtraPartsObject(image.getImagePath(), image.getImageHeight(),
                        image.getImageWidth(), extraPartsAttachPoint));
            }
        }finally {
            extraPartsCursor.close();
        }
        return extraPartsObjectList;
    }


    /**
     * Retrieves the imageInfo based on the id passed in.
     *
     * @param id the imageId, which is the primary key
     * @return an Image object containing the requested information
     */
    public static Image queryImage(int id) {

        final String imageRetriever = "SELECT * FROM imagesInfo WHERE id = ?";
        Cursor cursor = database.rawQuery(imageRetriever, new String[]{Integer.toString(id)});
        try {
            cursor.moveToFirst();
            Image image = new Image(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            return image;
        } finally {
            cursor.close();
        }

    }
    /**
     * Queries the database for an image
     * @param imageId the id of the image
     * @return Object, an image
     */
    private static String queryBackgroundObject(int imageId)
    {
        final String SQL = "SELECT * FROM backgroundObjects WHERE id = ?";
        Cursor cursor = database.rawQuery(SQL, new String[]{Integer.toString(imageId)});
        try
        {
            cursor.moveToFirst();
            return cursor.getString(1);
        }finally {
            cursor.close();
        }
    }



    /**
     * Queries the database for a Level based on the levelId
     * @param levelId the id of the requested level
     * @return Level, containing all of the required information
     */
    public static Level queryLevel(int levelId) {

        final String SQL = "SELECT * FROM levels WHERE levelNumber = ?";
        Cursor cursor = database.rawQuery(SQL, new String[]{Integer.toString(levelId)});
        try
        {
            cursor.moveToFirst();
            int levelNumber = cursor.getInt(cursor.getColumnIndex("levelNumber"));
            String leveltitle = cursor.getString(cursor.getColumnIndex("levelTitle"));
            String levelHint = cursor.getString(cursor.getColumnIndex("levelHint"));
            int width = cursor.getInt(cursor.getColumnIndex("levelWidth"));
            int height = cursor.getInt(cursor.getColumnIndex("levelHeight"));
            String musicPath = cursor.getString(cursor.getColumnIndex("levelMusic"));

            ArrayList<LevelObjects> levelObjectsArrayList = queryLevelObjects(levelId);
            ArrayList<LevelAsteroids> levelAsteroidsArrayList = queryLevelAsteroids(levelId);

            ArrayList<AsteroidType> asteroids = new ArrayList<>();
            for (int i = 0; i < levelAsteroidsArrayList.size(); i++)
            {
                for (int j = 0; j < levelAsteroidsArrayList.get(i).getAsteroidQuantity(); j++)
                {
                    AsteroidType asteroid = AsteroidsGameDAO.queryAsteroid(levelAsteroidsArrayList.get(i).getAsteroidId());

                    Random random = new Random();
                    asteroid.setPositionCoordinates(new PointF(random.nextInt(width + 1), random.nextInt(height + 1)));
                    asteroid.setBounds();
                    asteroids.add(asteroid);
                }
            }

            return new Level(levelNumber, leveltitle, levelHint, height, width, musicPath, levelObjectsArrayList,
                    asteroids);
        }finally {
            cursor.close();
        }
    }



    /**
     * Gets the Array containing all of the object ids related to that level
     * @param levelId the id of the requested level
     * @return ArrayList<Integer>, containing all of the ids of the objects pertaining to that level
     */
    public static ArrayList<LevelObjects> queryLevelObjects(int levelId) {
        final String levelObjectRetriever = "SELECT * FROM levelObjects WHERE level_id = ?";
        Cursor levelObjectCursor = database.rawQuery(levelObjectRetriever, new String[]{Integer.toString(levelId)});
        ArrayList<LevelObjects> levelObjectsArrayList = new ArrayList<>();
        try {
            while (levelObjectCursor.moveToNext()) {
                PointF position = new PointF((Integer.parseInt(levelObjectCursor.getString(1).split(",")[0])),
                        Integer.parseInt(levelObjectCursor.getString(1).split(",")[1]));
                int objectId = levelObjectCursor.getInt(2);
                BackgroundObject backgroundObject = new BackgroundObject(queryBackgroundObject(objectId));
                float scale = levelObjectCursor.getFloat(3);
                LevelObjects levelObject = new LevelObjects(backgroundObject.getImagePath(), position, scale);
                levelObject.setBounds();
                levelObjectsArrayList.add(new LevelObjects(backgroundObject.getImagePath(), position, scale));
            }
        } finally {
            levelObjectCursor.close();
        }
        return levelObjectsArrayList;
    }

    /**
     * Queries the database for a Level
     * @param levelId the id whose objects are requested
     * @return LevelObjects
     */
    public static ArrayList<LevelAsteroids> queryLevelAsteroids(int levelId)
    {
        ArrayList<LevelAsteroids> levelAsteroidsArrayList = new ArrayList<>();
        final String levelAsteroidsQuery = "SELECT * FROM levelAsteroids WHERE level_id = ?";

        Cursor levelAsteroidsCursor = database.rawQuery(levelAsteroidsQuery, new String[]{Integer.toString(levelId)});
        try{
            while(levelAsteroidsCursor.moveToNext())
            {
                int numberOfAsteroids = levelAsteroidsCursor.getInt(1);
                int asteroidId = levelAsteroidsCursor.getInt(2);
                levelAsteroidsArrayList.add(new LevelAsteroids(numberOfAsteroids, asteroidId));
            }
        }finally {
            levelAsteroidsCursor.close();
        }
        return levelAsteroidsArrayList;
    }

    public static List<MainBodyObject> getAllMainBodies()
    {
        List<MainBodyObject> mainBodyObjectList = new ArrayList<>();
        final String SQL = "SELECT * FROM mainBodies";
        Cursor cursor = database.rawQuery(SQL, null);
        try
        {
            while(cursor.moveToNext()) {
                int imageForeignKey = cursor.getInt(4);

                Image image = queryImage(imageForeignKey);
                PointF cannonAttachPointF = new PointF((Integer.parseInt(cursor.getString(1).split(",")[0])),
                        Integer.parseInt(cursor.getString(1).split(",")[1]));
                PointF engineAttachPointF = new PointF((Integer.parseInt(cursor.getString(2).split(",")[0])),
                        Integer.parseInt(cursor.getString(2).split(",")[1]));
                PointF extraAttachPointF = new PointF((Integer.parseInt(cursor.getString(3).split(",")[0])),
                        Integer.parseInt(cursor.getString(3).split(",")[1]));
                mainBodyObjectList.add(new MainBodyObject(image.getImagePath(), image.getImageHeight(),
                        image.getImageWidth(), cannonAttachPointF, engineAttachPointF, extraAttachPointF));
            }

        }finally {
            cursor.close();
        }
        return mainBodyObjectList;
    }

    public static List<PowerCoreObject> getAllPowerCores()
    {
        List<PowerCoreObject> powerCoreObjectList = new ArrayList<>();
        final String SQL = "SELECT * FROM powerCores";
        Cursor cursor = database.rawQuery(SQL, null);
        try{
            while(cursor.moveToNext()) {
                int cannonBoost = cursor.getInt(1);
                int engineBoost = cursor.getInt(2);
                String imagePath = cursor.getString(3);
                powerCoreObjectList.add(new PowerCoreObject(imagePath, 0,0, cannonBoost, engineBoost));
            }
        }finally {
            cursor.close();
        }
        return powerCoreObjectList;
    }

    public static List<EngineObject> getAllEngines() {

        List<EngineObject> engineObjectList = new ArrayList<>();
        final String SQL = "SELECT * FROM engines";
        Cursor cursor = database.rawQuery(SQL, null);
        try
        {
            while(cursor.moveToNext()) {
                int baseSpeed = cursor.getInt(1);
                int baseTurnRate = cursor.getInt(2);
                PointF attachPointF = new PointF((Integer.parseInt(cursor.getString(3).split(",")[0])),
                        Integer.parseInt(cursor.getString(3).split(",")[1]));
                Image image = queryImage(cursor.getInt(4));
                engineObjectList.add(new EngineObject(image.getImagePath(), image.getImageHeight(),
                        image.getImageWidth(), attachPointF, baseSpeed, baseTurnRate));
            }
        }finally {
            cursor.close();
        }
        return engineObjectList;
    }
}
