package com.example.user.customlistview.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.customlistview.R;
import com.example.user.customlistview.adapter.ContactListRecylcerAdapter;
import com.example.user.customlistview.custom.RecyclerItemClickListener;
import com.example.user.customlistview.database.ContactDetailsTable;
import com.example.user.customlistview.database.CotactTable;
import com.example.user.customlistview.database.DataBaseHelper;
import com.example.user.customlistview.jdo.ContactJDO;

import java.util.ArrayList;
import java.util.Collections;

import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_ID;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_PHOTO;

public class ContactActivity extends AppCompatActivity  implements View.OnClickListener {



    DataBaseHelper mMataBaseHelper;

    CotactTable mContactTable;
    ContactDetailsTable mContactDetailsTable;

    final int RETRIVE_DATA_FROM_CONTACT = 1;

    RecyclerView mContactRecyclerView;
    ContactListRecylcerAdapter mContactListRecylcerAdapter;
    LinearLayoutManager mRecylerLayoutManager;


    public static final String DATA_RETRIVING_SHARED_PREFRENCE = "contact_shared_prefrence";


    ArrayList<ContactJDO> mContactJDOArrayList=new ArrayList<ContactJDO>();

    final static String TAG="Contact Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Log.e(TAG, "onCreate: " );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContactTable=new CotactTable(this);
        mContactDetailsTable=new ContactDetailsTable(this);

         mRecylerLayoutManager = new LinearLayoutManager(this);
        mRecylerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mMataBaseHelper = new DataBaseHelper(this, null, null, 1);

        mContactRecyclerView = (RecyclerView) findViewById(R.id.ContactlistRecycler);

        mContactRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        TextView lContactIdTextView = (TextView) view.findViewById(R.id.contctId);
                        final String lContactId = lContactIdTextView.getText().toString();

                        Intent mMoveToSingleContact = new Intent(view.getContext(), SingleContactActivityCollapsingToolBar.class);
                        mMoveToSingleContact.putExtra("Id", lContactId);

                        startActivityForResult(mMoveToSingleContact,1);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

                    }

                })
        );

        /**
         * lisetner for swiping and daragging and dropping.....
         */

        ItemTouchHelper lItemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(UP|DOWN,LEFT|RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                Log.d(TAG, "onMove: ");
                int lDragingPosition=viewHolder.getLayoutPosition();
                int lTargetingPosition=target.getLayoutPosition();

                Log.d(TAG, "onMove: "+lDragingPosition+" "+lTargetingPosition);
                mContactListRecylcerAdapter.changePositionOnMove(lDragingPosition,lTargetingPosition);
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Log.d(TAG, "onSwiped: left");

                int lLoyoutPosition=viewHolder.getLayoutPosition();
                if (direction==LEFT){
//                    mContactJDOArrayList.get(lLoyoutPosition).g
                    Log.d(TAG, "onSwiped: left");
                }else {
                    Log.d(TAG, "onSwiped: Right");
                }

            }

        });

        lItemTouchHelper.attachToRecyclerView(mContactRecyclerView);

       


        SharedPreferences sharedPrefs = getSharedPreferences(DATA_RETRIVING_SHARED_PREFRENCE, MODE_PRIVATE);

        String lIsDataRetrived = sharedPrefs.getString("isDataRetrived", "no");

        if (lIsDataRetrived.equals("no")) {

            SharedPreferences.Editor editor = getSharedPreferences(DATA_RETRIVING_SHARED_PREFRENCE, MODE_PRIVATE).edit();

            editor.putString("isDataRetrived", "yes");
            editor.commit();

            ensurePermissions();


        } else {

            getContactFromLocalDataBase();

        }


    }

    void getContactFromLocalDataBase(){

        mContactTable.open();
        Cursor lContactCursor = mContactTable.getNamrAndImage();


        if (lContactCursor.getCount() > 0 && lContactCursor.moveToNext()) {

            do {
                ContactJDO mContactJDO=new ContactJDO();
                mContactJDO.setmCotactId(lContactCursor.getString(lContactCursor.getColumnIndex(COLUMN_CONTACT_ID)));
                mContactJDO.setmContactName(lContactCursor.getString(lContactCursor.getColumnIndex(COLUMN_CONTACT_NAME)));
                mContactJDO.setmCotactImage(lContactCursor.getString(lContactCursor.getColumnIndex(COLUMN_CONTACT_PHOTO)));
                mContactJDOArrayList.add(mContactJDO);

            } while (lContactCursor.moveToNext());

        }

        mContactTable.close();
        Collections.sort(mContactJDOArrayList);

        if(mContactListRecylcerAdapter==null) {
            mContactListRecylcerAdapter = new ContactListRecylcerAdapter(this, mContactJDOArrayList);
            mContactRecyclerView.setLayoutManager(mRecylerLayoutManager);
            mContactRecyclerView.setAdapter(mContactListRecylcerAdapter);
        }else {
            mContactListRecylcerAdapter.notifyDataSetChanged();
        }


    }

    void  getContactsFromContentProvider(){


        ContentResolver lContentResolver = getContentResolver();

        String lContactId, lContactName, lContactImage, lOrganization, lnote, lNickName, lPhoneticname, lType;

        Cursor lContactCursor=lContentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHONETIC_NAME,
                        ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER}, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        if (lContactCursor.getCount() > 0 && lContactCursor.moveToNext()) {

            do {

                /**
                 ****************************** Contact ID  *******************************
                 */
                lContactId = lContactCursor.getString(lContactCursor.getColumnIndex(ContactsContract.Contacts._ID));

                /**
                 ****************************** Contact Name  *******************************
                 */
                lContactName = lContactCursor.getString(lContactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                /**
                 ******************************  phonetc name  *******************************
                 */

                lPhoneticname = lContactCursor.getString(lContactCursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));


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

                if (lOrganizationCursor.getCount() > 0 && lOrganizationCursor.moveToFirst()) {

                    String lOrganizationName = lOrganizationCursor.getString(lOrganizationCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                    String lOrgJobTitle = lOrganizationCursor.getString(lOrganizationCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                    lOrganization = lOrganizationName + ":" + lOrgJobTitle;
                } else {
                    lOrganization = "";
                }

                lOrganizationCursor.close();

                /**
                 ******************************   Notes   *******************************
                 */


                String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] noteWhereParams = new String[]{lContactId,
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};

                Cursor lNoteCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);

                if (lNoteCursor.getCount() > 0 && lNoteCursor.moveToFirst()) {

                    lnote = lNoteCursor.getString(lNoteCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                } else {
                    lnote = "";
                }


                lNoteCursor.close();

                /**
                 ******************************   Nick Name   *******************************
                 */


                Cursor lNicknameCursor = getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Nickname.NAME},
                        ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + "= ?",
                        new String[]{lContactId, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE},
                        null);
                if (lNicknameCursor.getCount() > 0) {
                    lNicknameCursor.moveToFirst();

                    lNickName = lNicknameCursor.getString(0);
                } else {
                    lNickName = "";
                }


                /**
                 * ***************************   Adding the contact data which has single value ***************************
                 */
                mContactTable.open();
                mContactTable.addContact(Integer.parseInt(lContactId), lContactName, lNickName, lPhoneticname
                        , lContactImage, lOrganization, lnote);
                mContactTable.close();

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

                            lType = "phone";

                            mContactDetailsTable.open();
                            mContactDetailsTable.addContactDetails(lType, lPhoneNumber, lContactId);
                            mContactDetailsTable.close();

                        } while (lPhoneCursor.moveToNext());


                    }
                }


                /**
                 ****************************** Email *******************************
                 */


                Cursor lEmailCursor = lContentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{lContactId}, null);

                if (lEmailCursor.getCount() > 0 && lEmailCursor.moveToFirst()) {
                    do {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String lEmail = lEmailCursor.getString(
                                lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = lEmailCursor.getString(
                                lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        lType = "email";
                        mContactDetailsTable.open();
                        mContactDetailsTable.addContactDetails(lType, lEmail, lContactId);
                        mContactDetailsTable.close();

                    } while (lEmailCursor.moveToNext());

                }
                lEmailCursor.close();


                /**
                 ******************************  Address  *******************************
                 */

                String lAddressWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] addrWhereParams = new String[]{lContactId,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

                Cursor lAddressCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                        null, lAddressWhere, addrWhereParams, null);

                if (lAddressCursor.getCount() > 0 && lAddressCursor.moveToFirst()) {
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


                        String lAddress = street + ":" + city + ":" + state + ":" + postalCode + ":" + country;

                        lType = "address";
                        mContactDetailsTable.open();
                        mContactDetailsTable.addContactDetails(lType, lAddress, lContactId);
                        mContactDetailsTable.close();
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

                if (lIMCursor.getCount() > 0 && lIMCursor.moveToFirst()) {

                    do {

                        String imName = lIMCursor.getString(
                                lIMCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                        String imType;
                        imType = lIMCursor.getString(
                                lIMCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));

                        lType = "im";
                        mContactDetailsTable.open();
                        mContactDetailsTable.addContactDetails(lType, imName, lContactId);
                        mContactDetailsTable.close();

                    } while (lImageCursor.moveToNext());
                }


                /**
                 ******************************   Website   *******************************
                 */


                String[] projection = new String[]{
                        ContactsContract.CommonDataKinds.Website.URL,
                        ContactsContract.CommonDataKinds.Website.TYPE
                };

                String selection = ContactsContract.Data.CONTACT_ID + " = " + lContactId + " AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE + "'";


                Cursor lWebsiteCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);

                if (lWebsiteCursor.getCount() > 0 && lWebsiteCursor.moveToFirst()) {

                    do {

                        String lWebSite = lWebsiteCursor.getString(0);
                        String lWebsiteType = lWebsiteCursor.getString(1);

                        lType = "website";
                        mContactDetailsTable.open();
                        mContactDetailsTable.addContactDetails(lType, lWebSite, lContactId);
                        mContactDetailsTable.close();

                    } while (lWebsiteCursor.moveToNext());
                }


                /**
                 ******************************   Relation   *******************************
                 */


                String noteWhere1 = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] noteWhereParams1 = new String[]{lContactId,
                        ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE};

                Cursor lRelationcCursor = lContentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere1, noteWhereParams1, null);

                if (lRelationcCursor.moveToFirst()) {

                    do {
                        String lRelation = lRelationcCursor.getString(lRelationcCursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.NAME));

                        lType = "relation";
                        mContactDetailsTable.open();
                        mContactDetailsTable.addContactDetails(lType, lRelation, lContactId);


                    } while (lRelationcCursor.moveToNext());


                }


            } while (lContactCursor.moveToNext());
        }
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
        } else {

            getContactsFromContentProvider();
            getContactFromLocalDataBase();

        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RETRIVE_DATA_FROM_CONTACT: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    getContactsFromContentProvider();

                    getContactFromLocalDataBase();


                } else {

                }
                return;
            }

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String message = data.getStringExtra("MESSAGE");


                mContactJDOArrayList.clear();
                getContactFromLocalDataBase();

            }

        }
        if (requestCode == 2) {

            if (resultCode == RESULT_OK) {

                String message = data.getStringExtra("MESSAGE");

                Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

                mContactJDOArrayList.clear();
                getContactFromLocalDataBase();

            }

        }

    }


    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("action", "add");
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }
}
