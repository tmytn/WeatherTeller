package com.example.monet.weaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MovieDetails";

    public static final String TABLE_NAME = "movies";
    public static final String KEY_MOVIE_ID = "movie_id";
    public static final String KEY_MOVIE_NAME = "movie_name";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_YEAR = "year";
    public static final String KEY_RATING = "rating";
    //Create Table Query
    private static final String SQL_CREATE_MOVIES =
            "CREATE TABLE movies (" + KEY_MOVIE_ID + "  INTEGER PRIMARY KEY, "
                    + KEY_MOVIE_NAME + " TEXT, " + KEY_GENRE + "  TEXT, "
                    + KEY_YEAR + "  INTEGER, " + KEY_RATING + "  REAL );";

    private static final String SQL_DELETE_MOVIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop the table while upgrading the database
        // such as adding new column or changing existing constraint
        db.execSQL(SQL_DELETE_MOVIES);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }
}
