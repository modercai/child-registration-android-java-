package com.example.register_child;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySqLite extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "childreg.db";
    public static  final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "child_registration";
    //Definition for the database schema containing the requested information
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST = "first_name";
    public static final String COLUMN_LAST = "last_name";
    public static final String COLUMN_AGE = "age";
    public static final String  COLUMN_GENDER = "gender";
    public static final String COLUMN_IMMUNIZATION = "immunization";
    public MySqLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST + " TEXT, " +
                COLUMN_LAST + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_IMMUNIZATION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addChildInfo(String firstName, String lastName, int age, String gender, String immunizations){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST, firstName);
        contentValues.put(COLUMN_LAST, lastName);
        contentValues.put(COLUMN_AGE, age);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_IMMUNIZATION, immunizations);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed to submit to database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readDatabase(){
        String fetchQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
           cursor = db.rawQuery(fetchQuery,null);
        }
        return cursor;
    }
}
