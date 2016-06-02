package com.example.remi.ekio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

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

    public int ajouter(Collectionable object) {
        ContentValues value = new ContentValues();
        value.put(CollectionableDAO.TITLE, object.getTitle());
        value.put(CollectionableDAO.LOCATION, object.getLocation());
        value.put(CollectionableDAO.DATE, object.getDate());
        value.put(CollectionableDAO.COMMENT, object.getcomment());
        value.put(CollectionableDAO.KEYWORDS, object.getKeyWords());
        value.put(CollectionableDAO.PHOTO_PATH, object.getPhotoPath());
        long id = mDb.insert(CollectionableDAO.TABLE_NAME, null, value);

        return (int) (long)id;

    }

    public Collectionable select(int idSearch) {
        Cursor cursor = mDb.rawQuery("select *  from " + TABLE_NAME + " where id = ?", new String[]{Integer.toString(idSearch)});

        Boolean test = cursor.moveToFirst();
        if (test) {
            long id = cursor.getLong(0);
            String title = cursor.getString(1);
            String location = cursor.getString(2);
            String date = cursor.getString(3);
            String comment = cursor.getString(4);
            String path = cursor.getString(5);
            String keyword = cursor.getString(6);

            Collectionable object = new Collectionable(id, title, date, location, comment, keyword, path);
            cursor.close();
            return object;
        }
        return null;

    }

    public ArrayList<String> allPath(){
        Cursor cursor = mDb.rawQuery("select " + PHOTO_PATH + " from " + TABLE_NAME, null);
        ArrayList<String> path = new ArrayList<String>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            path.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return path;
    }

/*    public Collectionable lastEntry() {
        Cursor cursor = mDb.rawQuery("select *  from " + TABLE_NAME + " ORDER BY column DESC LIMIT 1;", null);
        cursor.moveToLast();

        long id = cursor.getLong(0);
        String title = cursor.getString(1);
        String location = cursor.getString(2);
        String date = cursor.getString(3);
        String comment = cursor.getString(4);
        String path = cursor.getString(5);
        String keyword = cursor.getString(6);

        Collectionable object = new Collectionable(id, title, location, date, comment, keyword, path)
        return object;

    }*/

}
