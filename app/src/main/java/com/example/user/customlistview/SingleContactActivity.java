package com.example.user.customlistview;

import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleContactActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    String mContactId="";
    String mContactName="";
    String mPhoneNumber="";
    String mContactImgUri="";
    TextView mNameTextView,mNumberTextView;
    ImageView mContactImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_single_contact);
        mNameTextView=(TextView) findViewById(R.id.ContactName);
        mNumberTextView=(TextView) findViewById(R.id.Contactnumber);
        mContactImageView =(ImageView) findViewById(R.id.display_contact_img);
        Intent pValueOfTheContactIdFromList=getIntent();
        mContactId=pValueOfTheContactIdFromList.getStringExtra("Id");
        getLoaderManager().initLoader(1,null,this);

    }

    //new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.HAS_PHONE_NUMBER}
    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==1){
            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.HAS_PHONE_NUMBER},
                    ContactsContract.Contacts._ID+" = ?",new String[]{mContactId},null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor pCursor) {


        if(pCursor.getCount()>0) {

            pCursor.moveToFirst();

            mContactName = pCursor.getString(0);

            ContentResolver lContentResolver = getContentResolver();

            if(!pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)).equals("0")) {



                Cursor lCursor = lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{mContactId}, null);

                while (lCursor.moveToNext()) {

                            mPhoneNumber = mPhoneNumber +""+ lCursor.getString(0) + "\n";

                }

            }

            Cursor lCorsor2=lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_URI},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{mContactId}, null);

            if(lCorsor2.getCount()>0){

                lCorsor2.moveToFirst();

                    Picasso.with(this)
                            .load(lCorsor2.getString(0))
                            .placeholder(R.drawable.contact_img)
                            .resize(200, 200)
                            .transform(new RoundedTransformation(100, 1))
                            .into(mContactImageView);


            }

        }
        mNameTextView.setText(mContactName);
        mNumberTextView.setText(mPhoneNumber);


    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}
