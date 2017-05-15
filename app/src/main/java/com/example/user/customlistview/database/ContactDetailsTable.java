package com.example.user.customlistview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 18/04/17.
 */

public class ContactDetailsTable {

    public static final String TABLE_CONTACT_DETAILS = "contact_details";
    public static final String COLUMN_DETAIL_ID = "id";
    public static final String COLUMN_DETAIL_TYPE = "type";
    public static final String COLUMN_DETAIL_VALUE = "value";
    public static final String COLUMN_CONTACT_ID = "_id";


    Context mContext;
    SQLiteDatabase lDataBase;
    private DataBaseHelper mDbHelper;

    public ContactDetailsTable(Context mContext) {
        this.mContext = mContext;
    }

    public static void createTable(SQLiteDatabase pDataBase){

        String lContactDetailQuery = "create table " + TABLE_CONTACT_DETAILS + "(" +
                COLUMN_DETAIL_ID + " integer primary key autoincrement," +
                COLUMN_DETAIL_TYPE + " text ," +
                COLUMN_DETAIL_VALUE + " text ," +
                COLUMN_CONTACT_ID + " text" +
                ");";
        pDataBase.execSQL(lContactDetailQuery);

    }

    public void open(){

        mDbHelper = new DataBaseHelper(mContext,null,null,1);
        lDataBase = mDbHelper.getWritableDatabase();
    }
    public void close() {
        mDbHelper.close();
    }


    public  void addContactDetails(String pType,String pValue,String pContactId){

        ContentValues lContactValue = new ContentValues();

        lContactValue.put(COLUMN_DETAIL_TYPE, pType);
        lContactValue.put(COLUMN_DETAIL_VALUE, pValue);
        lContactValue.put(COLUMN_CONTACT_ID, pContactId);

        lDataBase.insert(TABLE_CONTACT_DETAILS, null, lContactValue);
//        lDataBase.close();
    }





    public Cursor getContactDetails(String pContactId){


        return  lDataBase.rawQuery("SELECT * FROM " + TABLE_CONTACT_DETAILS + " WHERE "+COLUMN_CONTACT_ID+"="+"'" + Integer.parseInt(pContactId)+ "'", null);

    }

    public void updateContactDeatails(String pId,String pValue) {

        ContentValues lContentValues = new ContentValues();

        lContentValues.put(COLUMN_DETAIL_VALUE, pValue);



        lDataBase.update(TABLE_CONTACT_DETAILS, lContentValues,COLUMN_DETAIL_ID + " = ?", new String[]{pId});

//        lDataBase.close();
    }



    public String getContactDetailId(){

        Cursor lGetContactIdCursor=lDataBase.query(TABLE_CONTACT_DETAILS, new  String[]{COLUMN_DETAIL_ID}, null, null, null, null, COLUMN_DETAIL_ID + " DESC");

        if(lGetContactIdCursor.getCount()>0 && lGetContactIdCursor.moveToFirst()) {

            return lGetContactIdCursor.getString(0);

        }else {
            return "1";
        }

    }


    public void deleteContactDetails(int pContactDetailId){

        lDataBase.execSQL("DELETE FROM " + TABLE_CONTACT_DETAILS + " WHERE " + COLUMN_DETAIL_ID + "=\"" + pContactDetailId + "\";");
//        lDataBase.close();

    }



    public  Cursor retriveValue(String pContactId,String pType){

        String lSelectQuery = "SELECT * "+
                " FROM "+TABLE_CONTACT_DETAILS+
                " WHERE " + COLUMN_DETAIL_TYPE +" = \'" + pType+"\'"+
                " AND "+COLUMN_CONTACT_ID+ " = "+pContactId;

        return lDataBase.rawQuery(lSelectQuery, null);

    }

    public  void deleteNullValues(){

        lDataBase.execSQL("DELETE FROM " + TABLE_CONTACT_DETAILS + " WHERE " + COLUMN_DETAIL_VALUE + "=\"" + "" + "\";");
//        lDataBase.close();

    }
}
