package edu.byu.cs.superasteroids.database;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.modelClasses.AsteroidType;
import edu.byu.cs.superasteroids.modelClasses.CannonObject;
import edu.byu.cs.superasteroids.modelClasses.EngineObject;
import edu.byu.cs.superasteroids.modelClasses.ExtraPartsObject;
import edu.byu.cs.superasteroids.modelClasses.Level;
import edu.byu.cs.superasteroids.modelClasses.LevelObjects;
import edu.byu.cs.superasteroids.modelClasses.MainBodyObject;
import edu.byu.cs.superasteroids.modelClasses.PowerCoreObject;

import static edu.byu.cs.superasteroids.database.AsteroidsGameDAO.*;


/**
 * This class tests for inserting and querying things from the database.
 */
public class AsteroidsGameDAOTests extends AndroidTestCase {

    private SQLiteDatabase database;
    private DbOpenHelper dbOpenHelper;

    /**
     * Sets up for testing by dropping all tables in the database, then creating them again.
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        dbOpenHelper = new DbOpenHelper(getContext());
        database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.createAll(database);
        database.beginTransaction();

    }
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
        database.endTransaction();
        database = null;
        dbOpenHelper.close();
        dbOpenHelper = null;
    }

    /**
     * This function tests for inserting and querying from the Images table.
     */
    public void testInsertAndQueryImage()
    {
        assertEquals(1, insertImage("firstTest", 1, 2));
        assertEquals(2, insertImage("secondTest", 3, 4));
        assertEquals(3, insertImage("thirdTest", 5, 6));
        assertEquals(4, insertImage("fourthTest", 7, 8));
        
        assertEquals("firstTest", queryImage(1).getImagePath());
        assertEquals("secondTest", queryImage(2).getImagePath());
        assertEquals("thirdTest", queryImage(3).getImagePath());
        assertEquals("fourthTest", queryImage(4).getImagePath());

        assertEquals(2, queryImage(1).getImageHeight());
        assertEquals(4, queryImage(2).getImageHeight());
        assertEquals(6, queryImage(3).getImageHeight());
        assertEquals(8, queryImage(4).getImageHeight());

        assertEquals(1, queryImage(1).getImageWidth());
        assertEquals(3, queryImage(2).getImageWidth());
        assertEquals(5, queryImage(3).getImageWidth());
        assertEquals(7, queryImage(4).getImageWidth());
    }

    /**
     * This function tests for inserting and querying from the Asteroids table
     */
    public void testInsertAndQueryAsteroid()
    {
        insertAsteroid("regular", "regular", "pathToRegular", 5, 6);
        insertAsteroid("octeroid", "octeroid", "pathToOcteroid", 7, 8);
        insertAsteroid("growing", "growing", "pathToGrowing", 9, 10);

        assertEquals("regular", queryAsteroid(1).getAsteroidName());
        assertEquals("octeroid", queryAsteroid(2).getAsteroidName());
        assertEquals("growing", queryAsteroid(3).getAsteroidName());

        assertEquals("regular", queryAsteroid(1).getType());
        assertEquals("octeroid", queryAsteroid(2).getType());
        assertEquals("growing", queryAsteroid(3).getType());

        assertEquals("pathToRegular", queryAsteroid(1).getImagePath());
        assertEquals("pathToOcteroid", queryAsteroid(2).getImagePath());
        assertEquals("pathToGrowing", queryAsteroid(3).getImagePath());
    }

    /**
     * This function tests for inserting and querying from the Cannons table
     */
    public void testInsertAndQueryCannon()
    {
        assertEquals(1, insertCannon("100,200", "300,400", "firstCannonPath", 0,1,"firstAttackPath",3,4,"firstSound",5));
        assertEquals(2, insertCannon("500,600", "700,800", "secondCannonPath", 6, 7, "secondAttackPath", 8, 9, "secondSound", 10));

        List<CannonObject> cannonObjectArrayList = getAllCannons();

        assertEquals(new PointF(100,200),cannonObjectArrayList.get(0).getAttachPoint());
        assertEquals(new PointF(300,400),cannonObjectArrayList.get(0).getEmitPoint());
        assertEquals("firstCannonPath",cannonObjectArrayList.get(0).getImagePath());
        assertEquals(0,cannonObjectArrayList.get(0).getImageWidth());
        assertEquals(1,cannonObjectArrayList.get(0).getImageHeight());
        assertEquals("firstAttackPath",cannonObjectArrayList.get(0).getAttackImage());
        assertEquals(3,cannonObjectArrayList.get(0).getAttackImageWidth());
        assertEquals(4,cannonObjectArrayList.get(0).getAttackImageHeight());
        assertEquals("firstSound",cannonObjectArrayList.get(0).getAttackSound());
        assertEquals(5,cannonObjectArrayList.get(0).getDamage());


        assertEquals(new PointF(500,600),cannonObjectArrayList.get(1).getAttachPoint());
        assertEquals(new PointF(700,800),cannonObjectArrayList.get(1).getEmitPoint());
        assertEquals("secondCannonPath",cannonObjectArrayList.get(1).getImagePath());
        assertEquals(6,cannonObjectArrayList.get(1).getImageWidth());
        assertEquals(7,cannonObjectArrayList.get(1).getImageHeight());
        assertEquals("secondAttackPath",cannonObjectArrayList.get(1).getAttackImage());
        assertEquals(8,cannonObjectArrayList.get(1).getAttackImageWidth());
        assertEquals(9,cannonObjectArrayList.get(1).getAttackImageHeight());
        assertEquals("secondSound",cannonObjectArrayList.get(1).getAttackSound());
        assertEquals(10,cannonObjectArrayList.get(1).getDamage());

    }

    /**
     * This function tests for inserting and querying from the Engines table
     */
    public void testInsertAndQueryEngines()
    {
        assertEquals(1, insertEngine(0, 1, "100,200", "firstEnginePath", 2, 3));
        assertEquals(2, insertEngine(4, 5, "300,400", "secondEnginePath", 6, 7));

        List<EngineObject> engineObjects = getAllEngines();

        assertEquals(0, engineObjects.get(0).getBaseSpeed());
        assertEquals(1, engineObjects.get(0).getBaseTurnRate());
        assertEquals(new PointF(100,200), engineObjects.get(0).getAttachPoint());
        assertEquals("firstEnginePath", engineObjects.get(0).getImagePath());
        assertEquals(2, engineObjects.get(0).getImageWidth());
        assertEquals(3, engineObjects.get(0).getImageHeight());

        assertEquals(4, engineObjects.get(1).getBaseSpeed());
        assertEquals(5, engineObjects.get(1).getBaseTurnRate());
        assertEquals(new PointF(300, 400), engineObjects.get(1).getAttachPoint());
        assertEquals("secondEnginePath", engineObjects.get(1).getImagePath());
        assertEquals(6, engineObjects.get(1).getImageWidth());
        assertEquals(7, engineObjects.get(1).getImageHeight());
    }

    /**
     * This function tests for inserting and querying from the mainBodies table.
     */
    public void testInsertAndQueryMainBodies()
    {
        assertEquals(1, insertMainBody("100,200", "300,400", "500,600", "firstMainBody", 0, 1));
        assertEquals(2, insertMainBody("700,800", "900,1000", "1100,1200", "secondMainBody", 2, 3));

        List<MainBodyObject> mainBody = getAllMainBodies();

        assertEquals(new PointF(100, 200), mainBody.get(0).getCannonAttach());
        assertEquals(new PointF(300, 400), mainBody.get(0).getEngineAttach());
        assertEquals(new PointF(500, 600), mainBody.get(0).getExtraAttach());
        assertEquals("firstMainBody", mainBody.get(0).getImagePath());
        assertEquals(0, mainBody.get(0).getImageWidth());
        assertEquals(1, mainBody.get(0).getImageHeight());

        assertEquals(new PointF(700,800), mainBody.get(1).getCannonAttach());
        assertEquals(new PointF(900, 1000), mainBody.get(1).getEngineAttach());
        assertEquals(new PointF(1100, 1200), mainBody.get(1).getExtraAttach());
        assertEquals("secondMainBody", mainBody.get(1).getImagePath());
        assertEquals(2, mainBody.get(1).getImageWidth());
        assertEquals(3, mainBody.get(1).getImageHeight());


    }
    /**
     * This function tests for inserting and querying from the extraParts table.
     */
    public void testInsertAndQueryExtraParts()
    {

        assertEquals(1, insertExtraParts("100,200", "firstExtraPart", 0, 1));
        assertEquals(2, insertExtraParts("300,400", "secondExtraPart", 2, 3));

        List<ExtraPartsObject> extraPartsObjects = getAllExtraParts();

        assertEquals(new PointF(100,200), extraPartsObjects.get(0).getAttachPoint());
        assertEquals("firstExtraPart", extraPartsObjects.get(0).getImagePath());
        assertEquals(0, extraPartsObjects.get(0).getImageWidth());
        assertEquals(1, extraPartsObjects.get(0).getImageHeight());

        assertEquals(new PointF(300,400), extraPartsObjects.get(1).getAttachPoint());
        assertEquals("secondExtraPart", extraPartsObjects.get(1).getImagePath());
        assertEquals(2, extraPartsObjects.get(1).getImageWidth());
        assertEquals(3, extraPartsObjects.get(1).getImageHeight());


    }

    /**
     * This function tests for inserting and querying from the powerCores table.
     */
    public void testInsertAndQueryPowerCores()
    {
        assertEquals(1, insertPowerCore(0, 1, "firstPowerCore"));
        assertEquals(2, insertPowerCore(2, 3, "secondPowerCore"));

        List<PowerCoreObject> powerCoreObjects = getAllPowerCores();

        assertEquals(0, powerCoreObjects.get(0).getCannonBoost());
        assertEquals(1, powerCoreObjects.get(0).getEngineBoost());
        assertEquals("firstPowerCore", powerCoreObjects.get(0).getImagePath());

        assertEquals(2, powerCoreObjects.get(1).getCannonBoost());
        assertEquals(3, powerCoreObjects.get(1).getEngineBoost());
        assertEquals("secondPowerCore", powerCoreObjects.get(1).getImagePath());
    }

    /**
     * This function tests insertion and query of levels. Because levels contain level asteroids,
     * bg objects and level objects, these are also tested.
     */
    public void testInsertAndQueryLevels()
    {
        //In order to test levels, we must add Bg Objects and Asteroids First
        assertEquals(1, insertBackgroundObjects("BgOb1"));
        assertEquals(2, insertBackgroundObjects("BgOb2"));
        assertEquals(3, insertBackgroundObjects("BgOb3"));
        assertEquals(1, insertAsteroid("regular", "regular", "pathRegular", 0, 1));
        assertEquals(2, insertAsteroid("octeroid", "octeroid", "pathOcteroid", 2, 3));
        assertEquals(3, insertAsteroid("growing", "growing", "pathGrowing", 4, 5));



        //Testing Level 1
        assertEquals(1, insertLevel(1, "level 1", "hint 1", 0, 1, "music1"));
        assertEquals(1, insertLevelAsteroids(1, 2, 3));
        assertEquals(1, insertLevelObject(1, "100,200", 2, 3));
        ArrayList<LevelObjects> levelObjects1 = queryLevelObjects(1);
        ArrayList<AsteroidType> asteroidTypes1 = new ArrayList<>();
        asteroidTypes1.add(queryAsteroid(3));

        Level test1 = new Level(1,"level 1", "hint 1", 0, 1, "music1", levelObjects1, asteroidTypes1);

        assertEquals(test1, queryLevel(1));

/*        assertEquals(1, queryLevel(1).getLevelNumber());
        assertEquals("hint 1", queryLevel(1).getLevelHint());
        assertEquals(0, queryLevel(1).getLevelWidth());
        assertEquals(1, queryLevel(1).getLevelHeight());
        assertEquals("music1", queryLevel(1).getLevelMusicPath());*/

        //Testing Level 2
        assertEquals(2, insertLevel(2, "level 2", "hint 2", 2, 3, "music2"));
        assertEquals(2, insertLevelAsteroids(4, 5, 6));
        assertEquals(2, insertLevelObject(2, "300,400", 2, 5));
        ArrayList<LevelObjects> levelObjects2 = queryLevelObjects(2);
        ArrayList<AsteroidType> asteroidTypes2 = new ArrayList<>();
        asteroidTypes1.add(queryAsteroid(2));

        Level test2 = new Level(2, "level 2", "hint 2", 2, 3, "music2", levelObjects2, asteroidTypes2);

        assertEquals(test2, queryLevel(2));

/*        assertEquals(2, queryLevel(2).getLevelNumber());
        assertEquals("hint 2", queryLevel(2).getLevelHint());
        assertEquals(2, queryLevel(2).getLevelWidth());
        assertEquals(3, queryLevel(2).getLevelHeight());
        assertEquals("music2", queryLevel(2).getLevelMusicPath());*/



        //Testing Level 3
        assertEquals(3, insertLevel(3, "level 3", "hint 3", 4, 5, "music3"));
        assertEquals(3, insertLevelAsteroids(7, 8, 9));
        assertEquals(3, insertLevelObject(3, "500,600", 3, 7));
        ArrayList<LevelObjects> levelObjects3 = queryLevelObjects(3);
        ArrayList<AsteroidType> asteroidTypes3 = new ArrayList<>();
        asteroidTypes1.add(queryAsteroid(3));

        Level test3 = new Level(3, "level 3", "hint 3", 4, 5, "music3", levelObjects3, asteroidTypes3);

        assertEquals(test3, queryLevel(3));
/*        assertEquals(3, queryLevel(3).getLevelNumber());
        assertEquals("hint 3", queryLevel(3).getLevelHint());
        assertEquals(4, queryLevel(3).getLevelWidth());
        assertEquals(5, queryLevel(3).getLevelHeight());
        assertEquals("music3", queryLevel(3).getLevelMusicPath());*/
    }

}
