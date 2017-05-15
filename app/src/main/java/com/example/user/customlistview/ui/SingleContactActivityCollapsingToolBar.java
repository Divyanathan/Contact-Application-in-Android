package com.example.user.customlistview.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.customlistview.R;
import com.example.user.customlistview.adapter.SingleContactRecyclerAdapter;
import com.example.user.customlistview.custom.RecyclerItemClickListener;
import com.example.user.customlistview.database.ContactDetailsTable;
import com.example.user.customlistview.database.CotactTable;
import com.example.user.customlistview.database.DataBaseHelper;
import com.example.user.customlistview.jdo.ContactDetailsJDO;
import com.example.user.customlistview.utility.UtilityClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.user.customlistview.database.CotactTable.COLUMN_CONTACT_NICK_NAME;
import static com.example.user.customlistview.database.CotactTable.COLUMN_CONTACT_ORGANIZATION;
import static com.example.user.customlistview.database.CotactTable.COLUMN_CONTACT_PHOTO;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NOTES;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_PHONETIC_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_DETAIL_ID;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_DETAIL_TYPE;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_DETAIL_VALUE;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_ADDRESS;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_EMAIL;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_IM;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_PHONE;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_RELATION;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_WEBSITE;


public class SingleContactActivityCollapsingToolBar extends AppCompatActivity implements View.OnClickListener {


    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    String mContactId;

    String mCotactName;
    ImageView mContactImageView;

    DataBaseHelper mDataBaseHelper;


    ArrayList<ContactDetailsJDO> mCotactDetailsArrayList = new ArrayList<ContactDetailsJDO>();

    SingleContactRecyclerAdapter mSingleContactRecyclerAdapter;
    RecyclerView mRecyclerView;


    CotactTable mContactTable;

    ContactDetailsTable mContactDetailsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact_collapsing_tool_bar);


        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        mContactImageView = (ImageView) findViewById(R.id.contactImaeForCollapsing);

        mContactTable=new CotactTable(this);
        mContactTable.open();

        mContactDetailsTable=new ContactDetailsTable(this);
        mContactDetailsTable.open();


        mDataBaseHelper = new DataBaseHelper(this, null, null, 1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle("divyanathan");
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(SingleContactActivityCollapsingToolBar.this, R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(SingleContactActivityCollapsingToolBar.this, R.color.white));


        mRecyclerView = (RecyclerView) findViewById(R.id.singleContactRecyclerView);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override

                    public void onItemClick(View view, final int position) {


                        TextView number = (TextView) view.findViewById(R.id.phoneText);
                        final String lContactValue = number.getText().toString();
                        number.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SingleContactActivityCollapsingToolBar.this, "text" + lContactValue, Toast.LENGTH_SHORT).show();


                            }
                        });
                        ImageView lCallImg = (ImageView) view.findViewById(R.id.callImg);
                        lCallImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + lContactValue));

                                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                                    ensurePermissions();


                                } else {
                                    startActivity(callIntent);
                                }
                            }
                        });
                        ImageView lSmsImg = (ImageView) view.findViewById(R.id.smsImg);
                        lSmsImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                               /* mCotactDetailsArrayList.get(position).getCotactDetailstType()*/

                                if (mCotactDetailsArrayList.get(position).getCotactDetailstType().equals(COLUMN_TYPE_PHONE)) {

                                    Intent intent1 = new Intent("android.intent.action.VIEW");
                                    Uri data = Uri.parse("sms:" + lContactValue);
                                    intent1.setData(data);
                                    startActivity(intent1);

                                } else {

                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    String[] recipients = {lContactValue};
                                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "abc");
                                    intent.putExtra(Intent.EXTRA_TEXT, "def");
                                    intent.putExtra(Intent.EXTRA_CC, "ghi");
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


        getConTactDetails();

        sendTheValueToRecyler();

    }

    void getConTactDetails(){

        mContactDetailsTable.deleteNullValues();
        getContactDetails(UtilityClass.COLUMN_TYPE_PHONE);
        getContactDetails(COLUMN_TYPE_EMAIL);
        getContactDetails(COLUMN_TYPE_ADDRESS);
        getContactDetails(COLUMN_TYPE_IM);
        getContactDetails(COLUMN_TYPE_WEBSITE);
        getContactDetails(COLUMN_TYPE_RELATION);

        getContact();
    }


    void getContact() {

//        mContactTable.open();

        Cursor lNameAndImageCursor = mContactTable.getCotact(mContactId);



        lNameAndImageCursor.moveToFirst();

        mCotactName = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_NAME));

        collapsingToolbarLayout.setTitle(lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_NAME)));

        String lContactImage = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_PHOTO));

        String lPhoneticName = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_PHONETIC_NAME));

        if (lPhoneticName != null && !lPhoneticName.isEmpty() && !lPhoneticName.equals("null")) {

            mCotactDetailsArrayList.add(new ContactDetailsJDO("no_id", COLUMN_CONTACT_PHONETIC_NAME, lPhoneticName));
        }



        String lNickName = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_NICK_NAME));


        if (lNickName != null && !lNickName.isEmpty() && !lNickName.equals("null")) {

            mCotactDetailsArrayList.add(new ContactDetailsJDO("no_id", COLUMN_CONTACT_NICK_NAME, lNickName));


        }

        String lOrganiztion = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_ORGANIZATION));

        if (lOrganiztion != null && !lOrganiztion.isEmpty() && !lOrganiztion.equals("null")) {


            mCotactDetailsArrayList.add(new ContactDetailsJDO("no_id", COLUMN_CONTACT_ORGANIZATION, lOrganiztion));


        }

        String lNotes = lNameAndImageCursor.getString(lNameAndImageCursor.getColumnIndex(COLUMN_CONTACT_NOTES));

//        mContactTable.close();

        if (lNotes != null && !lNotes.isEmpty() && !lNotes.equals("null")) {

            mCotactDetailsArrayList.add(new ContactDetailsJDO("no_id", COLUMN_CONTACT_NOTES, lNotes));

        }

        Picasso.with(this)
                .load(lContactImage)
                .placeholder(R.drawable.contact_person_icon)
                .into(mContactImageView);

    }

    void getContactDetails(String pType) {

        Cursor lContactDetailsCursor = mContactDetailsTable.retriveValue(mContactId, pType);

        if (lContactDetailsCursor.getCount() > 0 && lContactDetailsCursor.moveToFirst()) {

            do {


                mCotactDetailsArrayList.add(new ContactDetailsJDO(
                        lContactDetailsCursor.getString(lContactDetailsCursor.getColumnIndex(COLUMN_DETAIL_ID)),
                        lContactDetailsCursor.getString(lContactDetailsCursor.getColumnIndex(COLUMN_DETAIL_TYPE)),
                        lContactDetailsCursor.getString(lContactDetailsCursor.getColumnIndex(COLUMN_DETAIL_VALUE))));


            } while (lContactDetailsCursor.moveToNext());


        }


    }


    private void ensurePermissions() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {


        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 0);

        }


    }

    void sendTheValueToRecyler() {

        mSingleContactRecyclerAdapter = new SingleContactRecyclerAdapter(this, new ArrayList<ContactDetailsJDO>(mCotactDetailsArrayList));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mSingleContactRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deltebutton:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Are you want to DELETE the contact ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mContactTable.deleteContactint(Integer.parseInt(mContactId));
                                Intent intent1 = new Intent();

                                intent1.putExtra("MESSAGE", "updated successfully");

                                setResult(RESULT_OK, intent1);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });


                AlertDialog alert = builder.create();
                alert.setTitle("Delete Contact Data");
                alert.show();



                break;
            case R.id.editbutton:
                Intent intent = new Intent(SingleContactActivityCollapsingToolBar.this, EditContactActivity.class);
                intent.putExtra("contact_details", mCotactDetailsArrayList);

                intent.putExtra("id", mContactId);
                intent.putExtra("name", mCotactName);
                intent.putExtra("action", "edit");
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String message = data.getStringExtra("MESSAGE");

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


                View view = this.getCurrentFocus();
                if (view != null) {
                 /*   InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/

                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                }
                mCotactDetailsArrayList.clear();

                getConTactDetails();
                sendTheValueToRecyler();

            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToThePreviousActivity();

    }
    public boolean onSupportNavigateUp() {
        navigateToThePreviousActivity();
        return true;
    }
    void navigateToThePreviousActivity(){
        Intent intent = new Intent();

        intent.putExtra("MESSAGE", "updated successfully");
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onClick(View v) {



    }
}
