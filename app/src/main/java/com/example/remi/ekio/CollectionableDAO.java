package com.example.remi.ekio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by remi on 01/06/16.
 */
public class CollectionableDAO extends DAOBase {


    public static final String KEY = "id";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String DATE = "date";
    public static final String COMMENT = "comment";
    public static final String KEYWORDS = "key_words";
    public static final String PHOTO_PATH = "photo_path";

    public static final String TABLE_NAME = "Collectionable";

    //public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTITULE + " TEXT, " + SALAIRE + " REAL);";

    //public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    //constructor
    public CollectionableDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(Collectionable object) {
        ContentValues value = new ContentValues();
        value.put(CollectionableDAO.TITLE, object.getTitle());
        value.put(CollectionableDAO.LOCATION, object.getLocation());
        value.put(CollectionableDAO.DATE, object.getDate());
        value.put(CollectionableDAO.COMMENT, object.getcomment());
        value.put(CollectionableDAO.KEYWORDS, object.getKeyWords());
        value.put(CollectionableDAO.PHOTO_PATH, object.getPhotoPath());
        mDb.insert(CollectionableDAO.TABLE_NAME, null, value);

    }

}
