package edu.byu.cs.superasteroids.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.byu.cs.superasteroids.database.AsteroidsGameDAO;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.modelClasses.AsteroidsGame;

/**
 * Created by Victor on 2/18/2016.
 * Singleton containing the Database and the actual game.
 */
public class Asteroids {

    private static Asteroids asteroidsInstance;
    private Context context;
    private static SQLiteDatabase database;
    private static AsteroidsGame asteroidsGame;
    private static AsteroidsGameDAO asteroidsGameDAO;

    private Asteroids(Context context) {

        this.context = context;

    }


    public SQLiteDatabase getDatabase() {
        return database;
    }

    public AsteroidsGame getAsteroidsGame() {
        return asteroidsGame;
    }

    public static void initialize(Context context){
        asteroidsInstance = new Asteroids(context);
        database = DbOpenHelper.getInstance(context).getWritableDatabase();
        asteroidsGameDAO = new AsteroidsGameDAO(database);
        asteroidsGame = new AsteroidsGame();
    }

    public static Asteroids getInstance(){
        return asteroidsInstance;
    }
}
