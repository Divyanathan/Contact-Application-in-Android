package com.example.user.customlistview.database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 18/04/17.
 */

public class CotactTable {

    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_CONTACT_ID = "_id";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_NICK_NAME = "nick_name";
    public static final String COLUMN_CONTACT_PHONETIC_NAME = "phonetic_name";
    public static final String COLUMN_CONTACT_PHOTO = "photo";
    public static final String COLUMN_CONTACT_ORGANIZATION = "org";
    public static final String COLUMN_CONTACT_NOTES = "notes";

    Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    private DataBaseHelper mDbHelper;

    public CotactTable(Context pContext) {
        mContext=pContext;
    }


}
