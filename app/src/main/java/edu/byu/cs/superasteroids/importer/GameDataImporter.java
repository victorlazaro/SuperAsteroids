package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.superasteroids.database.AsteroidsGameDAO;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;


/**
 * Created by Victor on 2/3/2016.
 */
public class GameDataImporter implements IGameDataImporter {
    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     *
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */;

    private static DbOpenHelper openHelper;
    public GameDataImporter(Context context)
    {
        openHelper = DbOpenHelper.getInstance(context);
    }

    @Override
    public boolean importData(InputStreamReader dataInputReader) {


        SQLiteDatabase database = edu.byu.cs.superasteroids.core.Asteroids.getInstance().getDatabase();
        database.beginTransaction();
        openHelper.createAll(database);
        try {
            JSONObject rootObject = new JSONObject(makeString(dataInputReader));
            JSONObject asteroidsGame = rootObject.getJSONObject("asteroidsGame");
            insertObjectImagePaths(asteroidsGame);
            insertAsteroidTypes(rootObject.getJSONObject("asteroidsGame"));
            insertLevels(rootObject.getJSONObject("asteroidsGame"));
            insertMainBody(rootObject.getJSONObject("asteroidsGame"));
            insertCannons(rootObject.getJSONObject("asteroidsGame"));
            insertExtraParts(rootObject.getJSONObject("asteroidsGame"));
            insertEngines(rootObject.getJSONObject("asteroidsGame"));
            insertPowerCore(rootObject.getJSONObject("asteroidsGame"));
            database.setTransactionSuccessful();
        } catch (JSONException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        finally {
            database.endTransaction();
        }
        return true;
    }



    private static void insertPowerCore(JSONObject rootObject) throws JSONException {

        JSONArray powerCoresArray = rootObject.getJSONArray("powerCores");


        for (int i = 0; i < powerCoresArray.length(); i++)
        {
            int cannonBoost = powerCoresArray.getJSONObject(i).getInt("cannonBoost");
            int engineBoost = powerCoresArray.getJSONObject(i).getInt("engineBoost");
            String imagePath = powerCoresArray.getJSONObject(i).getString("image");

            AsteroidsGameDAO.insertPowerCore(cannonBoost, engineBoost, imagePath);


        }
    }

    private static void insertEngines(JSONObject rootObject) throws JSONException {

        JSONArray enginesArray = rootObject.getJSONArray("engines");
        for (int i = 0; i < enginesArray.length(); ++i)
        {
            int baseSpeed = enginesArray.getJSONObject(i).getInt("baseSpeed");
            int baseTurnRate = enginesArray.getJSONObject(i).getInt("baseTurnRate");
            String attachPoint = enginesArray.getJSONObject(i).getString("attachPoint");
            String imagePath = enginesArray.getJSONObject(i).getString("image");
            int imageWidth = enginesArray.getJSONObject(i).getInt("imageWidth");
            int imageHeight = enginesArray.getJSONObject(i).getInt("imageHeight");

            AsteroidsGameDAO.insertEngine(baseSpeed, baseTurnRate, attachPoint, imagePath, imageWidth, imageHeight);
        }

    }

    private static void insertExtraParts(JSONObject rootObject) throws JSONException {
        JSONArray extraPartsArray = rootObject.getJSONArray("extraParts");

        for (int i = 0; i < extraPartsArray.length(); ++i)
        {
            String attachPoint = extraPartsArray.getJSONObject(i).getString("attachPoint");
            String imagePath = extraPartsArray.getJSONObject(i).getString("image");
            int imageWidth = extraPartsArray.getJSONObject(i).getInt("imageWidth");
            int imageHeight = extraPartsArray.getJSONObject(i).getInt("imageHeight");

            AsteroidsGameDAO.insertExtraParts(attachPoint, imagePath, imageWidth, imageHeight);
        }
    }

    private static void insertCannons(JSONObject rootObject) throws JSONException {
        JSONArray cannonsArray = rootObject.getJSONArray("cannons");

        for (int i = 0; i < cannonsArray.length(); ++i)
        {
            String attachPoint = cannonsArray.getJSONObject(i).getString("attachPoint");
            String emitPoint = cannonsArray.getJSONObject(i).getString("emitPoint");
            String imagePath = cannonsArray.getJSONObject(i).getString("image");
            int imageWidth = cannonsArray.getJSONObject(i).getInt("imageWidth");
            int imageHeight = cannonsArray.getJSONObject(i).getInt("imageHeight");
            String attackImagePath = cannonsArray.getJSONObject(i).getString("attackImage");
            int attackImageWidth = cannonsArray.getJSONObject(i).getInt("attackImageWidth");
            int attackImageHeight = cannonsArray.getJSONObject(i).getInt("attackImageHeight");
            String attackSoundPath = cannonsArray.getJSONObject(i).getString("attackSound");
            int damage = cannonsArray.getJSONObject(i).getInt("damage");

            AsteroidsGameDAO.insertCannon(attachPoint, emitPoint, imagePath, imageWidth, imageHeight, attackImagePath, attackImageWidth, attackImageHeight, attackSoundPath, damage);
        }
    }

    private static void insertMainBody(JSONObject rootObject) throws JSONException {

        JSONArray mainBodyArray = rootObject.getJSONArray("mainBodies");

        for (int i = 0; i < mainBodyArray.length(); ++i)
        {
            String cannonAttach = mainBodyArray.getJSONObject(i).getString("cannonAttach");
            String engineAttach = mainBodyArray.getJSONObject(i).getString("engineAttach");
            String extraAttach = mainBodyArray.getJSONObject(i).getString("extraAttach");
            String imagePath = mainBodyArray.getJSONObject(i).getString("image");
            int imageWidth = mainBodyArray.getJSONObject(i).getInt("imageWidth");
            int imageHeight = mainBodyArray.getJSONObject(i).getInt("imageHeight");

            AsteroidsGameDAO.insertMainBody(cannonAttach, engineAttach, extraAttach, imagePath, imageWidth, imageHeight);
        }



    }

    private static void insertLevels(JSONObject rootObject) throws JSONException {

        JSONArray levelsArray = rootObject.getJSONArray("levels");

        for (int i = 0; i < levelsArray.length(); ++i)
        {
            int levelNumber = levelsArray.getJSONObject(i).getInt("number");
            String levelTitle = levelsArray.getJSONObject(i).getString("title");
            String levelHint = levelsArray.getJSONObject(i).getString("hint");
            int levelWidth = levelsArray.getJSONObject(i).getInt("width");
            int levelHeight = levelsArray.getJSONObject(i).getInt("height");
            String musicPath = levelsArray.getJSONObject(i).getString("music");

            JSONArray levelObjectsArray = levelsArray.getJSONObject(i).getJSONArray("levelObjects");

            for (int j = 0; j < levelObjectsArray.length(); j++)
            {
                String position = levelObjectsArray.getJSONObject(j).getString("position");
                int objectId = levelObjectsArray.getJSONObject(j).getInt("objectId"); //should I subtract 1?
                long scale = levelObjectsArray.getJSONObject(j).getLong("scale");

                AsteroidsGameDAO.insertLevelObject(levelNumber, position, objectId, scale);

            }

            JSONArray levelAsteroidsArray = levelsArray.getJSONObject(i).getJSONArray("levelAsteroids");


            for (int j = 0; j < levelAsteroidsArray.length(); ++j)
            {
                int numberOfAsteroids = levelAsteroidsArray.getJSONObject(j).getInt("number");
                int asteroidId = levelAsteroidsArray.getJSONObject(j).getInt("asteroidId");

                AsteroidsGameDAO.insertLevelAsteroids(levelNumber, numberOfAsteroids, asteroidId);
            }

            AsteroidsGameDAO.insertLevel(levelNumber, levelTitle, levelHint, levelWidth, levelHeight, musicPath);
        }


    }

    private static void insertAsteroidTypes(JSONObject rootObject) throws JSONException {

        JSONArray asteroidTypesArray = rootObject.getJSONArray("asteroids");

        for (int i = 0; i < asteroidTypesArray.length(); ++i)
        {
            String asteroidName = asteroidTypesArray.getJSONObject(i).getString("name");
            String imagePath = asteroidTypesArray.getJSONObject(i).getString("image");
            int imageWidth = asteroidTypesArray.getJSONObject(i).getInt("imageWidth");
            int imageHeight = asteroidTypesArray.getJSONObject(i).getInt("imageHeight");
            String asteroidType = asteroidTypesArray.getJSONObject(i).getString("type");

            AsteroidsGameDAO.insertAsteroid(asteroidName, asteroidType, imagePath, imageWidth, imageHeight);
        }

    }

    private static void insertObjectImagePaths(JSONObject rootObject) throws JSONException {
        JSONArray objectImageArray = rootObject.getJSONArray("objects");
        {
            for (int i = 0; i < objectImageArray.length(); ++i)
            {
                String imagePath = objectImageArray.getString(i);
                AsteroidsGameDAO.insertBackgroundObjects(imagePath);
            }
        }
    }

    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while((n = reader.read(buf)) > 0)
        {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

}
