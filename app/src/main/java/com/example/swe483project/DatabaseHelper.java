package com.example.swe483project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "NameToBeChanged", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table USERS(email TEXT PRIMARY KEY NOT NULL, passcode TEXT(4) NOT NULL, SIM TEXT NOT NULL, status TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        //not sure if we even need it tbh
        DB.execSQL("drop Table if exists USERS");
    }

    public long insertUser(String email, String passcode, String SIM, String status)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("passcode", passcode);
        contentValues.put("SIM", SIM);
        contentValues.put("status", status);

        long key =DB.insert("USERS", null, contentValues);
        //Toast.makeText(RegisterActivity.this, "card is exist", Toast.LENGTH_SHORT).show();
        return key;
    }

    public Boolean checkExistEmail (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(" Select * from USERS where email = ?", new String [] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void updateEmail (String query)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL(query);

    }

    public Cursor getCustomQuery (String query)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(query, null);
        return cursor;
    }

    public String getUserData (String data){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USERS", null);
        if(cursor.moveToFirst())
            return cursor.getString(cursor.getColumnIndex(data));
        return null;
    }

    public void updateUserData (String data, String value){
        String email = getUserData(Constants.DATABASE_COLUMN.EMAIL);
        String UpdateQuery = "UPDATE USERS SET "+ data +" = \'" + value + "\' WHERE "
                + "email" + " = \'" + email + "\'";
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL(UpdateQuery);
    }



}
