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
    SQLiteDatabase lDataBase;
    private DataBaseHelper mDbHelper;

    public CotactTable(Context pContext) {
        mContext = pContext;
    }


    public void open() {

        mDbHelper = new DataBaseHelper(mContext, null, null, 1);
        lDataBase = mDbHelper.getWritableDatabase();
    }

    public static void creatTable(SQLiteDatabase pDataBase) {


        String lContactQuery = "create table " + TABLE_CONTACT + "(" +
                COLUMN_CONTACT_ID + " integer primary key ," +
                COLUMN_CONTACT_NAME + " text ," +
                COLUMN_CONTACT_PHONETIC_NAME + " text ," +
                COLUMN_CONTACT_NICK_NAME + " text ," +
                COLUMN_CONTACT_PHOTO + " text," +
                COLUMN_CONTACT_ORGANIZATION + " text," +
                COLUMN_CONTACT_NOTES + " text" +


                ");";
        pDataBase.execSQL(lContactQuery);

    }

    public void close()
    {
        mDbHelper.close();
    }


    public void addContact(int pContactId, String pContatctName, String pNickName, String pPhoneticName, String pContactPhoto, String pOrganization, String pNotes) {

        ContentValues lContactValue = new ContentValues();

        lContactValue.put(COLUMN_CONTACT_ID, pContactId);
        lContactValue.put(COLUMN_CONTACT_NAME, pContatctName);
        lContactValue.put(COLUMN_CONTACT_PHONETIC_NAME, pPhoneticName);
        lContactValue.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContactValue.put(COLUMN_CONTACT_PHOTO, pContactPhoto);
        lContactValue.put(COLUMN_CONTACT_ORGANIZATION, pOrganization);
        lContactValue.put(COLUMN_CONTACT_NOTES, pNotes);

        lDataBase.insert(TABLE_CONTACT, null, lContactValue);
        lDataBase.close();
    }


    public Cursor getNamrAndImage() {


        return lDataBase.query(TABLE_CONTACT, null, null, null, null, null, COLUMN_CONTACT_NAME + " ASC");

    }


    public Cursor getCotact(String pContactId) {


        Cursor lContactCursor = lDataBase.rawQuery("SELECT * FROM " + TABLE_CONTACT + " WHERE " + COLUMN_CONTACT_ID + "=" + "'" + Integer.parseInt(pContactId) + "'", null);

        return lContactCursor;

    }

    public void updateContact(String pContactId, String pName, String pOrg, String pNickName, String pPhonetic, String pNotes) {

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_CONTACT_NAME, pName);
        lContentValues.put(COLUMN_CONTACT_ORGANIZATION, pOrg);
        lContentValues.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContentValues.put(COLUMN_CONTACT_PHONETIC_NAME, pPhonetic);
        lContentValues.put(COLUMN_CONTACT_NOTES, pNotes);


        lDataBase.update(TABLE_CONTACT, lContentValues, COLUMN_CONTACT_ID + " = ?", new String[]{pContactId});

        lDataBase.close();
    }

    public void insertContatct(String pContactId, String pName, String pOrg, String pNickName, String pPhonetic, String pNotes) {


        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_CONTACT_ID, pContactId);
        lContentValues.put(COLUMN_CONTACT_NAME, pName);
        lContentValues.put(COLUMN_CONTACT_PHONETIC_NAME, pPhonetic);
        lContentValues.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContentValues.put(COLUMN_CONTACT_ORGANIZATION, pOrg);
        lContentValues.put(COLUMN_CONTACT_NOTES, pNotes);


        lDataBase.insert(TABLE_CONTACT, null, lContentValues);
        lDataBase.close();

    }

    public int getContactId() {


        Cursor lGetContactIdCursor = lDataBase.query(TABLE_CONTACT, new String[]{COLUMN_CONTACT_ID}, null, null, null, null, COLUMN_CONTACT_ID + " DESC");

        if (lGetContactIdCursor.getCount() > 0 && lGetContactIdCursor.moveToFirst()) {

            return Integer.parseInt(lGetContactIdCursor.getString(0)) + 1;

        } else {
            return 1;
        }

    }


    public void deleteContactint(int pContactDetailId) {


        lDataBase.execSQL("DELETE FROM " + TABLE_CONTACT + " WHERE " + COLUMN_CONTACT_ID + "=\"" + pContactDetailId + "\";");
//        lDataBase.close();

    }

    public void deleteContactItem(String pContactId, String pColumn) {

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(pColumn, "\0");


        lDataBase.update(TABLE_CONTACT, lContentValues, COLUMN_CONTACT_ID + " = ?", new String[]{pContactId});

        lDataBase.close();

    }

}
