package com.example.alexandrebornerand.pretaporter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users.db";
    private static final String TAG = "UsersDatabaseHelper";
    private static final String TABLE_NAME = "prod_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "DESCRIPTION";
    private static final String COL4 = "PRICE";
    private static final String COL5 = "RATING";
    private static final String COL6 = "IMAGE";


    //constructor
    public UsersDataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, DESCRIPTION TEXT, PRICE DOUBLE, RATING DOUBLE, IMAGE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String description, double price, double rating, String image_src) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, description);
        contentValues.put(COL4, price);
        contentValues.put(COL5, rating);
        contentValues.put(COL6, image_src);
        long result = db.insert(TABLE_NAME, null, contentValues);


        //check if data inserted correctly. return -1 if incorrect.
        return result != -1;
    }
}
