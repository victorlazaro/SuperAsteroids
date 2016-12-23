package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Victor on 2/3/2016.
 * Creates a database, if it did not exist. Deals with the creation and dropping of tables
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "superasteroids.sqlite";
    private static final int DB_VERSION = 1;
    private static DbOpenHelper dbOpenHelper = null;

    public DbOpenHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static DbOpenHelper getInstance(Context context){
        if (dbOpenHelper == null)
        {
            dbOpenHelper = new DbOpenHelper(context);
        }

        return dbOpenHelper;
    }

    /**
     * Function that creates all of the initial tables.
     * First, it drops all tables if they already exist, by calling the function dropAll
     * Then, it creates the following tables:
     *  asteroids(primaryKey, asteroidName, imageIdForeignKey, asteroidType)
     *  cannons (primaryKey, attachPoint, emitPoint, imageIdForeignKey, attackIdForeignKey, attackSoundPath, damage)
     *  engines (primaryKey, baseSpeed, baseTurnRate, attachPoint, imageIdForeignKey)
     *  extraParts (primaryKey, attachPoint, imageIdForeignKey)
     *  levelAsteroids (primaryKey, asteroidNumber, asteroidId)
     *  levelObjects(primaryKey, position, objectId, scale)
     *  levels(primaryKey, levelNumber, levelTitle, levelHint, levelWidth, levelHeight, levelMusic, levelObjectForeignKey, levelAsteroidForeignKey)
     *  mainBodies(primaryKey, cannonAttach, engineAttach, extraAttach, imageIdForeignKey)
     *  imageObjects(primaryKey, imagePath)
     *  powerCores(primaryKey, cannonBoost, engineBoost, imagePath)
     *  imagesInfo(primaryKey, imagePath, imageWidth, imageHeight)
     *  levelObjectLinker( level_id, object_id)
     *  levelAsteroidLinker(level_id, asteroid_id)
     * @param db the asteroids database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        dropAll(db);
        final String createAsteroidTable =
                "CREATE TABLE asteroids " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asteroidName VARCHAR , " +
                "imageIdForKey INTEGER, " +
                "asteroidType VARCHAR) ";
        final String createCannonTable =
                "CREATE TABLE cannons " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "attachPoint VARCHAR , " +
                "emitPoint VARCHAR , " +
                "imageIdForKey INTEGER , " +
                "attackImageIdForKey INTEGER , " +
                "attackSoundPath VARCHAR , " +
                "damage INTEGER )";
        final String createEngineTable =
                "CREATE TABLE engines " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "baseSpeed INTEGER , " +
                "baseTurnRate INTEGER , " +
                "attachPoint VARCHAR , " +
                "imageIdForKey INTEGER )";
        final String createExtraPartsTable =
                "CREATE TABLE extraParts " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "attachPoint VARCHAR ," +
                "imageIdForKey INTEGER )";
        final String createLevelAsteroidsTable =
                "CREATE TABLE levelAsteroids " +
                "(level_id INTEGER," +
                "numberOfAsteroids INTEGER , " +
                "asteroidId INTEGER )";
        final String createLevelObjectsTable =
                "CREATE TABLE levelObjects " +
                "(level_id INTEGER, " +
                "position VARCHAR, " +
                "object_id INTEGER, " +
                "scale REAL)";
        final String createLevelsTable =
                "CREATE TABLE levels " +
                " (levelNumber INTEGER PRIMARY KEY," +
                " levelTitle VARCHAR ," +
                " levelHint VARCHAR ," +
                " levelWidth INTEGER ," +
                " levelHeight INTEGER ," +
                " levelMusic VARCHAR)";
        final String createMainBodyTable =
                "CREATE TABLE mainBodies " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonAttach VARCHAR , " +
                        "engineAttach VARCHAR , " +
                        "extraAttach VARCHAR , " +
                        "imageIdForKey INTEGER )";
        final String createImageObjectsPathTable =
                "CREATE TABLE backgroundObjects " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "imagePath VARCHAR)";
        final String createPowerCoreTable =
                "CREATE TABLE powerCores " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonBoost INTEGER , " +
                        "engineBoost INTEGER , " +
                        "imagePath VARCHAR )";
        final String createImagesInfoTable =
                "CREATE TABLE imagesInfo " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "imagePath VARCHAR , " +
                        "imageHeight INTEGER , " +
                        "imageWidth INTEGER)";
        db.execSQL(createAsteroidTable);
        db.execSQL(createCannonTable);
        db.execSQL(createEngineTable);
        db.execSQL(createExtraPartsTable);
        db.execSQL(createLevelAsteroidsTable);
        db.execSQL(createImageObjectsPathTable);
        db.execSQL(createLevelObjectsTable);
        db.execSQL(createLevelsTable);
        db.execSQL(createMainBodyTable);
        db.execSQL(createPowerCoreTable);
        db.execSQL(createImagesInfoTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    /**
     * Drops all tables, if they exist.
     * @param db the asteroids database
     */
    private void dropAll(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS asteroids");
        db.execSQL("DROP TABLE IF EXISTS cannons");
        db.execSQL("DROP TABLE IF EXISTS engines");
        db.execSQL("DROP TABLE IF EXISTS extraParts");
        db.execSQL("DROP TABLE IF EXISTS levelAsteroids");
        db.execSQL("DROP TABLE IF EXISTS levelObjects");
        db.execSQL("DROP TABLE IF EXISTS levels");
        db.execSQL("DROP TABLE IF EXISTS mainBodies");
        db.execSQL("DROP TABLE IF EXISTS backgroundObjects");
        db.execSQL("DROP TABLE IF EXISTS powerCores");
        db.execSQL("DROP TABLE IF EXISTS imagesInfo");

    }
    public void createAll(SQLiteDatabase database) {

        dropAll(database);
        final String createAsteroidTable =
                "CREATE TABLE asteroids " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "asteroidName VARCHAR , " +
                        "imageIdForKey INTEGER, " +
                        "asteroidType VARCHAR) ";
        final String createCannonTable =
                "CREATE TABLE cannons " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "attachPoint VARCHAR , " +
                        "emitPoint VARCHAR , " +
                        "imageIdForKey INTEGER , " +
                        "attackImageIdForKey INTEGER , " +
                        "attackSoundPath VARCHAR , " +
                        "damage INTEGER )";
        final String createEngineTable =
                "CREATE TABLE engines " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "baseSpeed INTEGER , " +
                        "baseTurnRate INTEGER , " +
                        "attachPoint VARCHAR , " +
                        "imageIdForKey INTEGER )";
        final String createExtraPartsTable =
                "CREATE TABLE extraParts " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "attachPoint VARCHAR ," +
                        "imageIdForKey INTEGER )";
        final String createLevelAsteroidsTable =
                "CREATE TABLE levelAsteroids " +
                        "(level_id INTEGER," +
                        "numberOfAsteroids INTEGER , " +
                        "asteroidId INTEGER )";
        final String createLevelObjectsTable =
                "CREATE TABLE levelObjects " +
                        "(level_id INTEGER, " +
                        "position VARCHAR, " +
                        "object_id INTEGER, " +
                        "scale REAL)";
        final String createLevelsTable =
                "CREATE TABLE levels " +
                        " (levelNumber INTEGER PRIMARY KEY," +
                        " levelTitle VARCHAR ," +
                        " levelHint VARCHAR ," +
                        " levelWidth INTEGER ," +
                        " levelHeight INTEGER ," +
                        " levelMusic VARCHAR)";
        final String createMainBodyTable =
                "CREATE TABLE mainBodies " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonAttach VARCHAR , " +
                        "engineAttach VARCHAR , " +
                        "extraAttach VARCHAR , " +
                        "imageIdForKey INTEGER )";
        final String createImageObjectsPathTable =
                "CREATE TABLE backgroundObjects " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "imagePath VARCHAR)";
        final String createPowerCoreTable =
                "CREATE TABLE powerCores " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonBoost INTEGER , " +
                        "engineBoost INTEGER , " +
                        "imagePath VARCHAR )";
        final String createImagesInfoTable =
                "CREATE TABLE imagesInfo " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "imagePath VARCHAR , " +
                        "imageHeight INTEGER , " +
                        "imageWidth INTEGER)";
        database.execSQL(createAsteroidTable);
        database.execSQL(createCannonTable);
        database.execSQL(createEngineTable);
        database.execSQL(createExtraPartsTable);
        database.execSQL(createLevelAsteroidsTable);
        database.execSQL(createImageObjectsPathTable);
        database.execSQL(createLevelObjectsTable);
        database.execSQL(createLevelsTable);
        database.execSQL(createMainBodyTable);
        database.execSQL(createPowerCoreTable);
        database.execSQL(createImagesInfoTable);



    }
}
