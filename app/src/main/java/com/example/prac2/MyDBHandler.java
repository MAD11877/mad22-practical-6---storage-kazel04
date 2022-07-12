package com.example.prac2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "madDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_FOLLOWED = "followed";
    private static MyDBHandler dbInstance;

    public MyDBHandler(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    public static MyDBHandler getInstance() {
        return dbInstance == null ? dbInstance = new MyDBHandler(ListActivity.getInstance()) : dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLES = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_FOLLOWED + " INTEGER NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user) {
        // Gets the data repository in write mode
        SQLiteDatabase dbwrite = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);;
        values.put(COLUMN_DESCRIPTION, user.description);
        values.put(COLUMN_FOLLOWED, user.followed);

// Insert the new row, returning the primary key value of the new row
        dbwrite.insert(TABLE_USERS, null, values);
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> userssql = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) { //https://stackoverflow.com/questions/10081631/android-cursor-movetonext
            Integer idsql = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            String namesql = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            String descriptionsql = cursor.getString((int) cursor.getColumnIndex(COLUMN_DESCRIPTION));
            Boolean followedsql =  cursor.getInt((int) cursor.getColumnIndex(COLUMN_FOLLOWED)) != 0;
            User newuser = new User(namesql,descriptionsql,idsql,followedsql);
            userssql.add(newuser);
        }

        return userssql;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);;
        values.put(COLUMN_DESCRIPTION, user.description);
        values.put(COLUMN_FOLLOWED, user.followed);

        db.update(TABLE_USERS, values, COLUMN_ID+"=?", new String[]{Integer.toString(user.id)});
        //https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#update(java.lang.String,%20android.content.ContentValues,%20java.lang.String,%20java.lang.String[])
    }






}
