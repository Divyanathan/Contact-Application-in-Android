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
    public void onCreate(SQLiteDatabase pDatatBase) {


        String lContactQuery = "create table " + TABLE_CONTACT + "(" +
                COLUMN_CONTACT_ID + " integer primary key ," +
                COLUMN_CONTACT_NAME + " text ," +
                COLUMN_CONTACT_PHONETIC_NAME + " text ," +
                COLUMN_CONTACT_NICK_NAME + " text ," +
                COLUMN_CONTACT_PHOTO + " text," +
                COLUMN_CONTACT_ORGANIZATION + " text," +
                COLUMN_CONTACT_NOTES + " text" +


                ");";
        pDatatBase.execSQL(lContactQuery);

        String lContactDetailQuery = "create table " + TABLE_CONTACT_DETAILS + "(" +
                COLUMN_DETAIL_ID + " integer primary key autoincrement," +
                COLUMN_DETAIL_TYPE + " text ," +
                COLUMN_DETAIL_VALUE + " text ," +
                COLUMN_CONTACT_ID + " text" +
                ");";
        pDatatBase.execSQL(lContactDetailQuery);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Inserting the value
     */
    public void addContact(int pContactId,String pContatctName,String pNickName,String pPhoneticName,String pContactPhoto,String pOrganization,String pNotes){

        ContentValues lContactValue = new ContentValues();

        lContactValue.put(COLUMN_CONTACT_ID, pContactId);
        lContactValue.put(COLUMN_CONTACT_NAME, pContatctName);
        lContactValue.put(COLUMN_CONTACT_PHONETIC_NAME, pPhoneticName);
        lContactValue.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContactValue.put(COLUMN_CONTACT_PHOTO, pContactPhoto);
        lContactValue.put(COLUMN_CONTACT_ORGANIZATION, pOrganization);
        lContactValue.put(COLUMN_CONTACT_NOTES, pNotes);

        SQLiteDatabase lDataBase = getWritableDatabase();
        lDataBase.insert(TABLE_CONTACT, null, lContactValue);
        lDataBase.close();
    }

    public  void addContactDetails(String pType,String pValue,String pContactId){

        ContentValues lContactValue = new ContentValues();

        lContactValue.put(COLUMN_DETAIL_TYPE, pType);
        lContactValue.put(COLUMN_DETAIL_VALUE, pValue);
        lContactValue.put(COLUMN_CONTACT_ID, pContactId);

        SQLiteDatabase lDataBase = getWritableDatabase();
        lDataBase.insert(TABLE_CONTACT_DETAILS, null, lContactValue);
        lDataBase.close();
    }

    public Cursor getNamrAndImage()
    {

        SQLiteDatabase lDataBase = getReadableDatabase();
       return lDataBase.query(TABLE_CONTACT, null, null, null, null, null, COLUMN_CONTACT_NAME + " ASC");

    }



    public Cursor getCotact(String pContactId) {

        SQLiteDatabase lDataBase = getReadableDatabase();
        Cursor lContactCursor = lDataBase.rawQuery("SELECT * FROM " + TABLE_CONTACT + " WHERE "+COLUMN_CONTACT_ID+"="+"'" + Integer.parseInt(pContactId)+ "'", null);

      return lContactCursor;

    }
    public Cursor getContactDetails(String pContactId){

        SQLiteDatabase lDataBase = getReadableDatabase();
        return  lDataBase.rawQuery("SELECT * FROM " + TABLE_CONTACT_DETAILS + " WHERE "+COLUMN_CONTACT_ID+"="+"'" + Integer.parseInt(pContactId)+ "'", null);

    }

    public void updateContactDeatails(String pId,String pValue) {

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_DETAIL_VALUE, pValue);

        SQLiteDatabase lDataBase = getWritableDatabase();

        lDataBase.update(TABLE_CONTACT_DETAILS, lContentValues,COLUMN_DETAIL_ID + " = ?", new String[]{pId});

        lDataBase.close();
    }

    public void updateContact(String pContactId,String pName,String pOrg,String pNickName,String pPhonetic,String pNotes) {

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_CONTACT_NAME, pName);
        lContentValues.put(COLUMN_CONTACT_ORGANIZATION, pOrg);
        lContentValues.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContentValues.put(COLUMN_CONTACT_PHONETIC_NAME, pPhonetic);
        lContentValues.put(COLUMN_CONTACT_NOTES, pNotes);

        SQLiteDatabase lDataBase = getWritableDatabase();

        lDataBase.update(TABLE_CONTACT, lContentValues,COLUMN_CONTACT_ID + " = ?", new String[]{pContactId});

        lDataBase.close();
    }
    public void  insertContatct(String pContactId,String pName,String pOrg,String pNickName,String pPhonetic,String pNotes){


        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_CONTACT_ID, pContactId);
        lContentValues.put(COLUMN_CONTACT_NAME, pName);
        lContentValues.put(COLUMN_CONTACT_PHONETIC_NAME, pPhonetic);
        lContentValues.put(COLUMN_CONTACT_NICK_NAME, pNickName);
        lContentValues.put(COLUMN_CONTACT_ORGANIZATION, pOrg);
        lContentValues.put(COLUMN_CONTACT_NOTES, pNotes);

        SQLiteDatabase lDataBase = getWritableDatabase();
        lDataBase.insert(TABLE_CONTACT, null, lContentValues);
        lDataBase.close();

    }

    public String getContactDetailId(){



        SQLiteDatabase lDataBase = getReadableDatabase();

        Cursor lGetContactIdCursor=lDataBase.query(TABLE_CONTACT_DETAILS, new  String[]{COLUMN_DETAIL_ID}, null, null, null, null, COLUMN_DETAIL_ID + " DESC");

        if(lGetContactIdCursor.getCount()>0 && lGetContactIdCursor.moveToFirst()) {

            return lGetContactIdCursor.getString(0);

        }else {
            return "1";
        }

    }

    public int getContactId(){



        SQLiteDatabase lDataBase = getReadableDatabase();

        Cursor lGetContactIdCursor=lDataBase.query(TABLE_CONTACT, new  String[]{COLUMN_CONTACT_ID}, null, null, null, null, COLUMN_CONTACT_ID + " DESC");

        if(lGetContactIdCursor.getCount()>0 && lGetContactIdCursor.moveToFirst()) {

            return Integer.parseInt(lGetContactIdCursor.getString(0))+1;

        }else {
            return 1;
        }

    }

    public void deleteContactDetails(int pContactDetailId){

        SQLiteDatabase lDataBase = getWritableDatabase();

        lDataBase.execSQL("DELETE FROM " + TABLE_CONTACT_DETAILS + " WHERE " + COLUMN_DETAIL_ID + "=\"" + pContactDetailId + "\";");
        lDataBase.close();

    }
    public void deleteContactItem(String pContactId,String pColumn){

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(pColumn,"\0");

        SQLiteDatabase lDataBase = getWritableDatabase();

        lDataBase.update(TABLE_CONTACT, lContentValues,COLUMN_CONTACT_ID + " = ?", new String[]{pContactId});

        lDataBase.close();

    }

    public  Cursor retriveValue(String pContactId,String pType){

        SQLiteDatabase lDataBase = getReadableDatabase();


        String lSelectQuery = "SELECT * "+
                " FROM "+TABLE_CONTACT_DETAILS+
                " WHERE " + COLUMN_DETAIL_TYPE +" = \'" + pType+"\'"+
                " AND "+COLUMN_CONTACT_ID+ " = "+pContactId;

        return lDataBase.rawQuery(lSelectQuery, null);

    }
    public  void deleteNullValues(){


        SQLiteDatabase lDataBase = getWritableDatabase();
        lDataBase.execSQL("DELETE FROM " + TABLE_CONTACT_DETAILS + " WHERE " + COLUMN_DETAIL_TYPE + "=\"" + "" + "\";");
        lDataBase.close();

    }

}
