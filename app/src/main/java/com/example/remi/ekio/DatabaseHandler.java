package com.example.remi.ekio;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by remi on 30/05/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // For User
    public static final String USER_KEY = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_NOM = "nom";
    public static final String USER_PASSWORD = "password";
    public static final String USER_AUTO_CONNECT = "auto_connect";

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_EMAIL + " TEXT, " +
                    USER_PASSWORD + " TEXT, " +
                    USER_AUTO_CONNECT + " BOOLEAN, " +
                    USER_NOM + " TEXT);";

    public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    // Collectionable
    public static final String COLLECTIONABLE_KEY = "id";
    public static final String COLLECTIONABLE_TITLE = "title";
    public static final String COLLECTIONABLE_LOCATION = "location";
    public static final String COLLECTIONABLE_DATE = "date";
    public static final String COLLECTIONABLE_COMMENT = "comment";
    public static final String COLLECTIONABLE_KEYWORDS = "key_words";
    public static final String COLLECTIONABLE_PHOTOPATH = "photo_path";

    public static final String COLLECTIONABLE_TABLE_NAME = "Collectionable";
    public static final String COLLECTIONABLE_TABLE_CREATE =
            "CREATE TABLE " + COLLECTIONABLE_TABLE_NAME + " (" +
                    COLLECTIONABLE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLLECTIONABLE_TITLE + " TEXT, " +
                    COLLECTIONABLE_LOCATION + " TEXT, " +
                    COLLECTIONABLE_DATE + " TEXT, " +
                    COLLECTIONABLE_COMMENT + " TEXT, " +
                    COLLECTIONABLE_PHOTOPATH + " TEXT, " +
                    COLLECTIONABLE_KEYWORDS + " TEXT);";
    public static final String COLLECTIONABLE_TABLE_DROP = "DROP TABLE IF EXISTS " + COLLECTIONABLE_TABLE_NAME + ";";



    public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       // db.execSQL(USER_TABLE_CREATE); // comment because it's stable now (for new use uncomment)
        db.execSQL(COLLECTIONABLE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(USER_TABLE_DROP); // comment because stable now (for new use uncomment)
        db.execSQL(COLLECTIONABLE_TABLE_DROP);
        onCreate(db);
    }

}

