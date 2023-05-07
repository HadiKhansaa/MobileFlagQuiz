package com.example.flagquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private final String DATABASE_NAME = "my_database";
    private final String FIRST_TABLE_NAME = "user_table";
    private final int DATABASE_VERSION = 1;

    private final String SECOND_TABLE_NAME = "toDOList_table";
    private final String COLUMN_NOTES = "notes";

    private final String COLUMN_ID = "_id";
    private final String COLUMN_USERNAME = "username";
    private final String COLUMN_PASSWORD = "password";

    public MyDatabaseHelper(Context c) {
        super(c, "my_database", null, 1);
        context = c;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE =
                "CREATE TABLE " + FIRST_TABLE_NAME + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD +
                        " TEXT, rating TEXT);";

        String CREATE_TODOLIST_TABLE =
                "CREATE TABLE " + SECOND_TABLE_NAME + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NOTES + " TEXT);";

        db.execSQL(CREATE_TODOLIST_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public int checkInfo(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + FIRST_TABLE_NAME + " WHERE username=?" +  " and password =?";
        Cursor result = db.rawQuery(query, new String[]{""+username, ""+password});

        try{

            if(result.getCount() <= 0)
                return -1;
            else{
                String query2 = "SELECT * FROM user_table WHERE username =? and password =?";
                Cursor result2 = db.rawQuery(query2, new String[] {username+"", password+""});
                result2.moveToNext();
                return result2.getInt(result2.getColumnIndex("_id"));
        }

        }catch (Exception e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    @SuppressLint("Range")
    public int addUser(String username, String password){
        if(this.checkInfo(username, password) != -1) {
            return -1;
        }

        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put("rating", 1000);

        db.insert(FIRST_TABLE_NAME, null, cv);

        SQLiteDatabase db2 = getReadableDatabase();
        String query = "SELECT * FROM user_table WHERE username =? and password =?";
        Cursor result = db2.rawQuery(query, new String[] {username+"", password+""});
        result.moveToNext();
        return result.getInt(result.getColumnIndex("_id"));
    }


    public void deleteNote(String note){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SECOND_TABLE_NAME, "notes=?", new String[]{note});
    }

    @SuppressLint("Range")
    public String getRating(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM user_table WHERE _id=?";
        Cursor result = db.rawQuery(query, new String[]{""+id});
        result.moveToNext();
        return result.getString(result.getColumnIndex("rating"));
    }

    public void updateRating(int score){
        // score is between 0 and 100
        SQLiteDatabase db = getWritableDatabase();
        int newRating = Integer.parseInt(MainActivity.rating) + (score - 50);
        MainActivity.rating = newRating + "";
        ContentValues cv = new ContentValues();
        cv.put("rating", newRating+"");
        db.update(FIRST_TABLE_NAME,cv,"_id=?", new String[]{MainActivity.ID+""});
        Toast.makeText(context, ""+newRating, Toast.LENGTH_SHORT).show();
    }

    Cursor getUserInfo(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM user_table WHERE _id=?";
        return db.rawQuery(query, new String[]{""+id});
    }

    void deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("DefaultLocale") String deleteQuery = String.format("DELETE FROM %s WHERE %s = %d", FIRST_TABLE_NAME, "_id", id);
        db.execSQL(deleteQuery);
        db.close();
    }
}
