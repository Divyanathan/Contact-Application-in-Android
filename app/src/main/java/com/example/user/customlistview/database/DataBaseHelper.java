package com.example.user.customlistview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

/**
 * Created by user on 16/04/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contact.db";





    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        SQLiteDatabase db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase pDataBase) {

        CotactTable.creatTable(pDataBase);
        ContactDetailsTable.createTable(pDataBase);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
