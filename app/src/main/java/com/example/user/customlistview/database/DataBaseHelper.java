package com.example.user.customlistview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 16/04/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contact.db";


    /**
     *
    Contact Table
     */
    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_CONTACT_ID = "_id";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_NICK_NAME = "nick_name";
    public static final String COLUMN_CONTACT_PHONETIC_NAME = "phonetic_name";
    public static final String COLUMN_CONTACT_PHOTO = "photo";
    public static final String COLUMN_CONTACT_ORGANIZATION = "org";
    public static final String COLUMN_CONTACT_NOTES = "notes";

    /**
     *
     MultiValue Table
     */
    public static final String TABLE_CONTACT_DETAILS = "contact_details";
    public static final String COLUMN_DETAIL_ID = "id";
    public static final String COLUMN_DETAIL_TYPE = "type";
    public static final String COLUMN_DETAIL_VALUE = "value";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        SQLiteDatabase db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String contact_query = "create table " + TABLE_CONTACT + "(" +
                COLUMN_CONTACT_ID + " integer primary key ," +
                COLUMN_CONTACT_NAME + " text ," +
                COLUMN_CONTACT_PHONETIC_NAME + " text ," +
                COLUMN_CONTACT_NICK_NAME + " text ," +
                COLUMN_CONTACT_PHOTO + " text," +
                COLUMN_CONTACT_ORGANIZATION + " text," +
                COLUMN_CONTACT_NOTES + " text" +


                ");";
        db.execSQL(contact_query);

        String phone_query = "create table " + TABLE_CONTACT_DETAILS + "(" +
                COLUMN_DETAIL_ID + " integer primary key autoincrement," +
                COLUMN_DETAIL_TYPE + " text ," +
                COLUMN_DETAIL_VALUE + " text ," +
                COLUMN_CONTACT_ID + " text" +
                ");";
        db.execSQL(phone_query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Inserting the value
     */
    public void addContact(int pContactId,String pContatctName,String pNickName,String pPhoneticName,String pContactPhoto,String pOrganization,String pNotes){

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTACT_ID, pContactId);
        cv.put(COLUMN_CONTACT_NAME, pContatctName);
        cv.put(COLUMN_CONTACT_PHONETIC_NAME, pPhoneticName);
        cv.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        cv.put(COLUMN_CONTACT_PHOTO, pContactPhoto);
        cv.put(COLUMN_CONTACT_ORGANIZATION, pOrganization);
        cv.put(COLUMN_CONTACT_NOTES, pNotes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACT, null, cv);
        db.close();
    }

    public  void addContactDetails(String pType,String pPhoneNumber,String pContactId){

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DETAIL_TYPE, pType);
        cv.put(COLUMN_DETAIL_VALUE, pPhoneNumber);
        cv.put(COLUMN_CONTACT_ID, pContactId);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACT_DETAILS, null, cv);
        db.close();
    }

    public Cursor getNamrAndImage()
    {

        SQLiteDatabase db = getReadableDatabase();
       return db.query(TABLE_CONTACT, null, null, null, null, null, COLUMN_CONTACT_NAME + " ASC");

    }



    public Cursor getNameAndImageForSinglecontact(String pContactId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor lContactCursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT + " WHERE "+COLUMN_CONTACT_ID+"="+"'" + Integer.parseInt(pContactId)+ "'", null);

      return lContactCursor;
    }
}
