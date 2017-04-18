package com.example.user.customlistview.ui;


import android.Manifest;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import com.example.user.customlistview.adapter.CustomListViewAdapter;
import com.example.user.customlistview.R;

public class MainActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<Cursor> {

    ListView mListView;
    String[] mContactName;
    String[] mContactId;
    String[] mContactImage;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACT = 0;

    private Handler updateBarHandler;


    public ContentResolver mContentResolver;
    public Cursor mCursor;

    Intent mMoveToSingleContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        mListView.setTextFilterEnabled(true);
        mListView.setFastScrollEnabled(true);
        mListView.setFastScrollAlwaysVisible(true);

         mMoveToSingleContact=new Intent(this,SingleContactActivity.class);
        // if(PackageManager.PERMISSION_DENIED)
        //getContactDeatails();
        ensurePermissions();

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                   // getContactDeatails();

                    getLoaderManager().initLoader(1,null,this);
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

    private void ensurePermissions() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getLoaderManager().initLoader(1,null,this);
           // getContactDeatails();
        }

    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] value={ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID};
        if(id==1){
            //mContentResolver=getContentResolver();
            //mCursor=mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, value,null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASc");
            return new CursorLoader(this,ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID},null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor pCursor) {

        mContactId = new String[pCursor.getCount()];
        mContactName = new String[pCursor.getCount()];
        mContactImage=new String[pCursor.getCount()];
        Cursor lCursor;
        ContentResolver lContentResolver=getContentResolver();
        if (pCursor.getCount() > 0) {
            int count = 0;
            while (pCursor.moveToNext()) {
                mContactName[count] = pCursor.getString(pCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mContactId[count] = pCursor.getString(pCursor.getColumnIndex(ContactsContract.Contacts._ID));


                 lCursor=lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_URI},
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",new String[]{mContactId[count]}, null);

                if(lCursor.getCount()>0) {
                    lCursor.moveToNext();
                    mContactImage[count] = lCursor.getString(0);
                }
                else {
                    mContactImage[count]="null";
                }
                count++;

            }
        }
        CustomListViewAdapter mCustomList = new CustomListViewAdapter(this, mContactName, mContactId,mContactImage);
        mListView.setAdapter(mCustomList);


        final Intent pMoveToSingleConatctActivity=new Intent(this,SingleContactActivity.class);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mMoveToSingleContact.putExtra("Id", mContactId[position]);
                startActivity(mMoveToSingleContact);
                //Toast.makeText(MainActivity.this, mContactId[position], Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}


