package com.example.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstname";
    private static final String COLUMN_LAST_NAME = "lastname";
    private static final String COLUMN_SALARY = "salary";
    private SQLiteDatabase mDatabase;
     UserDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase mDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_FIRST_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_LAST_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_SALARY + " long NOT NULL\n" +
                ");";
        mDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    boolean addUser(String firstname, String lastname, long salary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstname);
        contentValues.put(COLUMN_LAST_NAME, lastname);
        contentValues.put(COLUMN_SALARY, salary);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;

    }
    Cursor getAllUser() {
   SQLiteDatabase db = getReadableDatabase();
//        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    boolean updateUser(int id, String first_name, String last_name, Integer salary) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, first_name);
        contentValues.put(COLUMN_LAST_NAME, last_name);
        contentValues.put(COLUMN_SALARY, salary);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
}
