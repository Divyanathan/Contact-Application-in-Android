package com.example.user.customlistview.ui;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.user.customlistview.R;
import com.example.user.customlistview.adapter.ContactListRecylcerAdapter;
import com.example.user.customlistview.custom.RecyclerItemClickListener;
import com.example.user.customlistview.database.DataBaseHelper;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<String> mContactName = new ArrayList<String>();
    ArrayList<String> mContactimageuri = new ArrayList<String>();
    ArrayList<String> mConactId = new ArrayList<String>();

    DataBaseHelper mMataBaseHelper;
    Boolean mIsDataRetrived;

    final int RETRIVE_DATA_FROM_CONTACT = 1;
    final int RETRIVE_DATA_FROM_LOCAL_DATBASE = 2;

    RecyclerView mContactRecyclerView;
    ContactListRecylcerAdapter mContactListRecylcerAdapter;

    public static final String DATA_RETRIVING_SHARED_PREFRENCE = "contact_shared_prefrence";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        mMataBaseHelper = new DataBaseHelper(this, null, null, 1);

        mContactRecyclerView = (RecyclerView) findViewById(R.id.ContactlistRecycler);

        mContactRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        TextView lContactIdTextView = (TextView) view.findViewById(R.id.contctId);
                        final String lContactId=lContactIdTextView.getText().toString();
                        Intent mMoveToSingleContact=new Intent(view.getContext(),SingleContactActivityCollapsingToolBar.class);
                        mMoveToSingleContact.putExtra("Id", lContactId);
                        startActivity(mMoveToSingleContact);
                       // Toast.makeText(ContactActivity.this, "" + lContactId, Toast.LENGTH_SHORT).show();

                    }

                })
        );

        mContactRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        SharedPreferences sharedPrefs = getSharedPreferences(DATA_RETRIVING_SHARED_PREFRENCE, MODE_PRIVATE);

        String lIsDataRetrived = sharedPrefs.getString("isDataRetrived", "no");

        if(lIsDataRetrived.equals("no")){

            SharedPreferences.Editor editor = getSharedPreferences(DATA_RETRIVING_SHARED_PREFRENCE, MODE_PRIVATE).edit();

            editor.putString("isDataRetrived", "yes");
            editor.commit();

            ensurePermissions();


        }else {

            retriveDataFromLocalDataBase();

        }








    }

    void retriveDataFromLocalDataBase(){

        getLoaderManager().initLoader(RETRIVE_DATA_FROM_LOCAL_DATBASE, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == RETRIVE_DATA_FROM_CONTACT) {

            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHONETIC_NAME,
                            ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER}, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        }
        if (id == RETRIVE_DATA_FROM_LOCAL_DATBASE) {

            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                    null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        }


        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor pContactCursor) {

        switch (loader.getId()) {

            case RETRIVE_DATA_FROM_CONTACT:


                ContentResolver lContentResolver = getContentResolver();

                String lContactId,lContactName , lContactImage, lOrganization ,lnote,lNickName,lPhoneticname,lType;

                if (pContactCursor.getCount() > 0 && pContactCursor.moveToNext()) {

                    do {

                        /**
                         ****************************** Contact ID  *******************************
                         */
                        lContactId = pContactCursor.getString(pContactCursor.getColumnIndex(ContactsContract.Contacts._ID));

                        /**
                         ****************************** Contact Name  *******************************
                         */
                        lContactName = pContactCursor.getString(pContactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        /**
                         ******************************  phonetc name  *******************************
                         */

                        lPhoneticname = pContactCursor.getString(pContactCursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));


                        /**
                         ******************************  photo  *******************************
                         */
                        Cursor lImageCursor = lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_URI},
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{lContactId}, null);

                        if (lImageCursor.getCount() > 0) {
                            lImageCursor.moveToNext();
                            lContactImage = lImageCursor.getString(lImageCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                        } else {

                            lContactImage = "default imamge path";
                        }



                        /**
                         ******************************   Organization   *******************************
                         */


                        String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                        String[] orgWhereParams = new String[]{lContactId,
                                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};

                        Cursor lOrganizationCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                                null, orgWhere, orgWhereParams, null);

                        if (lOrganizationCursor.getCount()>0 && lOrganizationCursor.moveToFirst()) {

                             String lOrganizationName = lOrganizationCursor.getString(lOrganizationCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                             String lOrgJobTitle = lOrganizationCursor.getString(lOrganizationCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                            lOrganization = lOrganizationName + lOrgJobTitle;
                        }else {
                            lOrganization="";
                        }

                        lOrganizationCursor.close();

                        /**
                         ******************************   Notes   *******************************
                         */


                        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                        String[] noteWhereParams = new String[]{lContactId,
                                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};

                        Cursor lNoteCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);

                        if (lNoteCursor.getCount()>0 && lNoteCursor.moveToFirst()) {

                            lnote = lNoteCursor.getString(lNoteCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                        }else {
                            lnote="";
                        }


                        lNoteCursor.close();

                        /**
                         ******************************   Nick Name   *******************************
                         */


                        Cursor lNicknameCursor =  getContentResolver().query(
                                ContactsContract.Data.CONTENT_URI,
                                new String[]{ContactsContract.CommonDataKinds.Nickname.NAME},
                                ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + "= ?",
                                new String[]{lContactId, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE},
                                null);
                        if(lNicknameCursor.getCount()>0 ) {
                            lNicknameCursor.moveToFirst();

                            lNickName = lNicknameCursor.getString(0);
                        }else {
                            lNickName="";
                        }


                        /**
                         * ***************************   Adding the contact data which has single value ***************************
                         */
                        mMataBaseHelper.addContact( Integer.parseInt(lContactId),lContactName,lNickName,lPhoneticname
                                ,lContactImage,lOrganization,lnote);


                        /**
                         ******************************  Phone Number  *******************************
                         */

                        Cursor lPhoneCursor = lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{lContactId}, null);

                        if (lPhoneCursor.getCount() > 0) {

                         String lPhoneNumberType;

                            if (lPhoneCursor.moveToFirst() && lPhoneCursor.moveToFirst()) {


                                do {
                                    lPhoneNumberType = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(this.getResources(),
                                            lPhoneCursor.getInt(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)), "");

                                    String lPhoneNumber = lPhoneCursor.getString(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                    lType="phone";
                                    mMataBaseHelper.addContactDetails(lType,lPhoneNumber,lContactId);

                                }while (lPhoneCursor.moveToNext());


                            }
                        }


                        /**
                         ****************************** Email *******************************
                         */


                        Cursor lEmailCursor=lContentResolver.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{lContactId}, null);

                        if(lEmailCursor.getCount()>0 && lEmailCursor.moveToFirst())  {
                            do {
                                // This would allow you get several email addresses
                                // if the email addresses were stored in an array
                                String  lEmail = lEmailCursor.getString(
                                        lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                String emailType = lEmailCursor.getString(
                                        lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                                lType="email";
                                mMataBaseHelper.addContactDetails(lType,lEmail,lContactId);

                            } while (lEmailCursor.moveToNext());

                        }
                        lEmailCursor.close();


                        /**
                         ******************************  Address  *******************************
                         */

                        String lAddressWhere =ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                        String[] addrWhereParams = new String[]{lContactId,
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

                        Cursor lAddressCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                                null, lAddressWhere, addrWhereParams, null);

                        if(lAddressCursor.getCount()>0 && lAddressCursor.moveToFirst()) {
                            do {


                                String street = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                String city = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                String state = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                                String postalCode = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                                String country = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                                String type = lAddressCursor.getString(
                                        lAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                                // Do something with these....


                               String lAddress= street+city+state+postalCode+country;

                                lType="address";
                                mMataBaseHelper.addContactDetails(lType,lAddress,lContactId);
                            } while (lAddressCursor.moveToNext());

                        }
                        lAddressCursor.close();


                        /**
                         ******************************   IM   *******************************
                         */

                        String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                        String[] imWhereParams = new String[]{lContactId,
                                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};

                        Cursor lIMCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                                null, imWhere, imWhereParams, null);

                        if (lIMCursor.getCount()>0 && lIMCursor.moveToFirst()) {

                            do {

                                String imName = lIMCursor.getString(
                                        lIMCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                                String imType;
                                imType = lIMCursor.getString(
                                        lIMCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));

                                lType="im";
                                mMataBaseHelper.addContactDetails(lType,imName,lContactId);

                            }while (lImageCursor.moveToNext());
                        }








                        /**
                         ******************************   Website   *******************************
                         */


                        String[] projection = new String[] {
                                ContactsContract.CommonDataKinds.Website.URL,
                                ContactsContract.CommonDataKinds.Website.TYPE
                        };

                        String selection = ContactsContract.Data.CONTACT_ID + " = " + lContactId + " AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE + "'";


                         Cursor lWebsiteCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);

                        if(lWebsiteCursor.getCount()>0 && lWebsiteCursor.moveToFirst()){

                            do{

                                String lWebSite=lWebsiteCursor.getString(0);
                                String lWebsiteType=lWebsiteCursor.getString(1);

                                lType="website";
                                mMataBaseHelper.addContactDetails(lType,lWebSite,lContactId);

                            }while (lWebsiteCursor.moveToNext());
                        }


                        /**
                         ******************************   Relation   *******************************
                         */


                        String noteWhere1 = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                        String[] noteWhereParams1 = new String[]{lContactId,
                                ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE};

                        Cursor lRelationcCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI,null , noteWhere1, noteWhereParams1, null);

                        if (lRelationcCursor.moveToFirst()) {

                            do{
                                String lRelation = lRelationcCursor.getString(lRelationcCursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.NAME));

                                lType="relation";
                                mMataBaseHelper.addContactDetails(lType,lRelation,lContactId);

                            }while (lRelationcCursor.moveToNext());



                        }



                    } while (pContactCursor.moveToNext());
                }

                break;

            case RETRIVE_DATA_FROM_LOCAL_DATBASE:

                Cursor lContactCursor = mMataBaseHelper.getNamrAndImage();
                if (lContactCursor.getCount() > 0 && lContactCursor.moveToNext()) {

                    do {

                        mConactId.add(lContactCursor.getString(lContactCursor.getColumnIndex(mMataBaseHelper.COLUMN_CONTACT_ID)));
                        mContactName.add(lContactCursor.getString(lContactCursor.getColumnIndex(mMataBaseHelper.COLUMN_CONTACT_NAME)));
                        mContactimageuri.add(lContactCursor.getString(lContactCursor.getColumnIndex(mMataBaseHelper.COLUMN_CONTACT_PHOTO)));

                    } while (lContactCursor.moveToNext());

                }

                mContactListRecylcerAdapter = new ContactListRecylcerAdapter(this, new ArrayList<>(mConactId), new ArrayList<>(mContactName), new ArrayList<>(mContactimageuri));
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mContactRecyclerView.setLayoutManager(layoutManager);
                mContactRecyclerView.setAdapter(mContactListRecylcerAdapter);
                break;

        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void ensurePermissions() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {



            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        RETRIVE_DATA_FROM_CONTACT);



            }
        }else {

            getLoaderManager().initLoader(RETRIVE_DATA_FROM_CONTACT, null, this);
            retriveDataFromLocalDataBase();
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RETRIVE_DATA_FROM_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // getContactDeatails();

                    getLoaderManager().initLoader(RETRIVE_DATA_FROM_CONTACT, null, this);

                    retriveDataFromLocalDataBase();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}