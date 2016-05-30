package com.example.remi.ekio;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by remi on 30/05/16.
 */
public class UserDAO extends DAOBase {

    public static final String TABLE_NAME = "user";
    public static final String KEY = "id";
    public static final String EMAIL = "email";
    public static final String NOM = "nom";
    public static final String PASSWORD = "password";
    public static final String AUTO_CONNECT = "auto_connect";

    //public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTITULE + " TEXT, " + SALAIRE + " REAL);";

    //public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    //constructor
    public UserDAO(Context pContext) {
        super(pContext);
    }
    /**
     * @param user le métier à ajouter à la base
     */

    public void ajouter(User user) {
        ContentValues value = new ContentValues();
        value.put(UserDAO.EMAIL, user.getEmail());
        value.put(UserDAO.NOM, user.getNom());
        value.put(UserDAO.PASSWORD, user.getPassword());
        value.put(UserDAO.AUTO_CONNECT, user.getAuto());
        mDb.insert(UserDAO.TABLE_NAME, null, value);

    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {
        // CODE
    }

    /**
     * @param user le métier modifié
     */
    public void modifier(User user) {
        // CODE
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public User selectionner(int idSearch) {
        //
        Cursor cursor = mDb.rawQuery("select *  from " + TABLE_NAME + " where id = ?", new String[]{Integer.toString(idSearch)});

        Boolean test = cursor.moveToFirst();
        if (test) {
            long id = cursor.getLong(0);
            String email = cursor.getString(1);
            String password = cursor.getString(2);
            Boolean autoConnect = cursor.getInt(3) > 0;
            String nom = cursor.getString(4);

            User user = new User(id, email, nom, password, autoConnect);
            return user;
        }
        return null;
    }
}
