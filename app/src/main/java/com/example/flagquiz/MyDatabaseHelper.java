package com.example.flagquiz;

import android.content.ContentValues;
import android.content.Context;
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
                        " TEXT);";

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

    public boolean checkInfo(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + FIRST_TABLE_NAME + " WHERE username=?" +  " and password =?";
        Cursor result = db.rawQuery(query, new String[]{""+username, ""+password});

        try{

            if(result.getCount() <= 0)
                return false;
            else{
                return true;
        }

        }catch (Exception e){
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void addUser(String username, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);

        db.insert(FIRST_TABLE_NAME, null, cv);
    }

    public Cursor readToDoListTableData(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + SECOND_TABLE_NAME;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public void addNote(String note, boolean edit, String oldNote){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTES, note);

        if(!edit) {
            if (db.insert(SECOND_TABLE_NAME, null, cv) != -1)
                Toast.makeText(context, "note Added Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
        }else{

            if(db.update(SECOND_TABLE_NAME,cv,"notes=?", new String[]{oldNote}) != -1)
            {
                Toast.makeText(context, "note Updated Successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Failed to update note", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void deleteNote(String note){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SECOND_TABLE_NAME, "notes=?", new String[]{note});
    }
}
