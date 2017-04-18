package com.example.user.customlistview.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.customlistview.adapter.PhoneNumberRecylerViewAdapter;
import com.example.user.customlistview.custom.RecyclerItemClickListener;
import com.example.user.customlistview.R;
import com.example.user.customlistview.database.DataBaseHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleContactActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,RecyclerView.OnItemTouchListener {

    String mContactId = "";
    String mContactName = "";

    ArrayList<String> mPhoneNumer = new ArrayList<String>();

    ArrayList<String> mPhoneNumberTemp =new ArrayList<String>();

    ArrayList<String> mISNumberOrEmail=new ArrayList<String>();
    ArrayList<String> mISNumberOrEmailTemp=new ArrayList<String>();
    TextView mNameTextView;
    TextView mValueTextView;
    ImageView mContactImageView;


    LinearLayout mPhoneNuberLayout;


    RecyclerView mPhoneNumberRecyclerView;

    private PhoneNumberRecylerViewAdapter lPhoneNumberRecylerViewAdapter;

    DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_single_contact);

        mDataBaseHelper = new DataBaseHelper(this, null, null, 1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNameTextView = (TextView) findViewById(R.id.ContactName);
        mValueTextView=(TextView) findViewById(R.id.phoneText);
        mContactImageView = (ImageView) findViewById(R.id.display_contact_img);
        mPhoneNuberLayout = (LinearLayout) findViewById(R.id.phone_number_layout);

        mPhoneNumberRecyclerView = (RecyclerView) findViewById(R.id.phoneNumberRecycle);

        mPhoneNumberRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {

                        TextView number = (TextView) view.findViewById(R.id.phoneText);
                        final String n=number.getText().toString();
                        number.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SingleContactActivity.this,"text"+ n, Toast.LENGTH_SHORT).show();

                            }
                        });
                       ImageView lCallImg=(ImageView) view.findViewById(R.id.callImg);
                        lCallImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + n));

                                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                                    ensurePermissions();


                                }else {
                                    startActivity(callIntent);
                                }
                            }
                        });
                        ImageView lSmsImg=(ImageView) view.findViewById(R.id.smsImg);
                        lSmsImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(mISNumberOrEmailTemp.get(position).equals("number")) {

                                    Intent intent1 = new Intent("android.intent.action.VIEW");
                                    Uri data = Uri.parse("sms:" + n);
                                    intent1.setData(data);
                                    startActivity(intent1);

                                }else {

                                    Intent intent=new Intent(Intent.ACTION_SEND);
                                    String[] recipients={n};
                                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                                    intent.putExtra(Intent.EXTRA_SUBJECT,"abc");
                                    intent.putExtra(Intent.EXTRA_TEXT,"def");
                                    intent.putExtra(Intent.EXTRA_CC,"ghi");
                                    intent.setType("text/html");
                                    startActivity(Intent.createChooser(intent, "Send mail"));

                                }
                            }
                        });
                    }
                })
        );

        Intent pValueOfTheContactIdFromList = getIntent();
        mContactId = pValueOfTheContactIdFromList.getStringExtra("Id");
        getLoaderManager().initLoader(1, null, this);


    }

    private void ensurePermissions() {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        0);

            }


    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER},
                    ContactsContract.Contacts._ID + " = ?", new String[]{mContactId}, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor pCursor) {

        //mPhoneNuberLayout.removeAllViews();
        ContentResolver lContentResolver = getContentResolver();


        switch (loader.getId()) {

            case 1:

                String lContactName,lContactImage;

                Cursor lContactNameCursor=mDataBaseHelper.getNamrAndImage();

               /** if (pCursor.getCount() > 0) {
                    pCursor.moveToFirst();

                    mContactName = pCursor.getString(0);
                    mNameTextView.setText(mContactName);
                }

                Cursor lCorsor2 = lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_URI},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{mContactId}, null);

                if (lCorsor2.getCount() > 0) {

                    lCorsor2.moveToFirst();

                  
                    Picasso.with(this)
                            .load(lCorsor2.getString(0))
                            .placeholder(R.drawable.contact_person_icon)
                            //.resize(1200, 800)
                            //.transform(new RoundedTransformation(100, 1))
                            .into(mContactImageView);



                }else{
                    Picasso.with(this)
                            .load(R.drawable.contact_person_icon)
                            //.placeholder()
                            //.resize(1200, 800)
                            //.transform(new RoundedTransformation(100, 1))
                            .into(mContactImageView);
                }

                Cursor lCorsor3 = lContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{mContactId}, null);
                if (lCorsor3.getCount() > 0) {

                    int id = 0;
                    if (lCorsor3.moveToFirst()) {


                        String lPhoneNumbrType= (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(this.getResources(), lCorsor3.getInt(1), "");

                        Log.d("Phone number lable","checking"+lPhoneNumbrType);
                        //  mPhoneNumer[id]=pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        mPhoneNumer.add(lCorsor3.getString(lCorsor3.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        mISNumberOrEmail.add("number");

                        String lksdjf=lCorsor3.getString(lCorsor3.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String kj=lksdjf;
                        id++;

                        while (lCorsor3.moveToNext()) {
                            mPhoneNumer.add(lCorsor3.getString(lCorsor3.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            mISNumberOrEmail.add("number");

                            String lPhoneNumbrType2= (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(this.getResources(), lCorsor3.getInt(1), "");
                            Log.i("Phone number lable",lPhoneNumbrType2);

                            String jdsf= (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(this.getResources(), lCorsor3.getInt(1), "");
                            String ljd=jdsf;
                            id++;
                        }

                    }
                }



                Cursor lCorsor4 = lContentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Email.DATA,ContactsContract.CommonDataKinds.Email.TYPE},
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{mContactId}, null);

                if (lCorsor4.getCount() > 0) {

                    int id = 0;
                    if (lCorsor4.moveToFirst()) {



                        mPhoneNumer.add(lCorsor4.getString(0));
                        mISNumberOrEmail.add("email");
                        id++;
                        String email = lCorsor4.getString(
                                lCorsor4.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = lCorsor4.getString(
                                lCorsor4.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        String k=emailType;
                        k=email;
                        while (lCorsor4.moveToNext()) {
                            mPhoneNumer.add(lCorsor4.getString(0));
                            mISNumberOrEmail.add("email");

                            id++;
                        }

                    }
                }


               //for organization

                String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                String[] orgWhereParams = new String[]{mContactId,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                Cursor orgCur = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                        null, orgWhere, orgWhereParams, null);
                if (orgCur.moveToFirst()) {
                    String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                    String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                     String a=orgName;
                    a=title;
                }

               // for IM

                String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                String[] imWhereParams = new String[]{mContactId,
                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                Cursor imCur = lContentResolver.query(ContactsContract.Data.CONTENT_URI,
                        null, imWhere, imWhereParams, null);
                if (imCur.moveToFirst()) {
                    String imName = imCur.getString(
                            imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                    String lOrgType= (String) ContactsContract.CommonDataKinds.Im.getTypeLabel(this.getResources(),
                            imCur.getInt(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL)), "");

                    String kkkk=lOrgType;
                    String imType;
                    imType = imCur.getString(
                            imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                    String k=imName;
                    k=imType;
                }


                //For Adderess

                String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                String[] addrWhereParams = new String[]{mContactId,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                Cursor addrCur = lContentResolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
                while(addrCur.moveToNext()) {
                    String poBox = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                    String street = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                    String city = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                    String state = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                    String postalCode = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                    String country = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                    String type = addrCur.getString(
                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                    // Do something with these....

                }

                mPhoneNumberTemp =new ArrayList<>(mPhoneNumer);
                mISNumberOrEmailTemp=new ArrayList<>(mISNumberOrEmail);
                if(mPhoneNumer!=null) {
                    lPhoneNumberRecylerViewAdapter = new PhoneNumberRecylerViewAdapter(this, new ArrayList<>(mPhoneNumer),new ArrayList<>(mISNumberOrEmail));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mPhoneNumberRecyclerView.setLayoutManager(layoutManager);
                    mPhoneNumberRecyclerView.setAdapter(lPhoneNumberRecylerViewAdapter);
                }
                mPhoneNumer.clear();
                mISNumberOrEmail.clear();*/
                break;

        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }



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

    @Override
    public void onClick(View v) {

    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

