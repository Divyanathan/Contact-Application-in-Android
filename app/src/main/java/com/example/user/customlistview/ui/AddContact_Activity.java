package com.example.user.customlistview.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.customlistview.R;
import com.example.user.customlistview.database.DataBaseHelper;

import java.util.ArrayList;

import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NICK_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NOTES;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_ORGANIZATION;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_PHONETIC_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_ADDRESS;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_EMAIL;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_IM;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_PHONE;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_RELATION;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_WEBSITE;

public class AddContact_Activity extends AppCompatActivity {


    LinearLayout mPhoneLayout, mEmailLayout, mAddressLayout, mIMLayout, mwebsiteLayout;
    LinearLayout mRelationLayout, mOrgLayout, mNicknameLayout, mNotesLayout, mPhoneticLayout;

    TextView mPhoneLable, mEmailLable, mAddressLable, mIMLable, mwebsiteLable;
    TextView mRelationLabel, mOrgLabel, mNicknameLabel, mNotesLabel, mPhoneticLable;

    ImageView mAddPhone, mAddEmail, mAddAddress, mAddIML, mAddwebsite,mAddRelation;

    EditText mOrgEditTExt,mNotesEditText,mPhoneticEditText,mNickNameEditText;

    TextView mNameTextView;
    EditText mNameEditText;

    DataBaseHelper mDataBaseHelper;

    ArrayList<String> mIdTextArrayList = new ArrayList<String>();
    ArrayList<EditText> mValueArralist = new ArrayList<EditText>();
    ArrayList<String> mTypeArrayList = new ArrayList<String>();

    String mContactId;

    int mImageIdToDelete=0;
    Boolean isSaveButtonPressed=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDataBaseHelper = new DataBaseHelper(this, null, null, 1);

        mContactId=""+mDataBaseHelper.getContactId();

        mNameTextView = (TextView) findViewById(R.id.name_text_vieww);
        mNameEditText = (EditText) findViewById(R.id.name_edit_textt);


        mPhoneLayout = (LinearLayout) findViewById(R.id.phone_layoutt);

        mEmailLayout = (LinearLayout) findViewById(R.id.eamil_layoutt);

        mAddressLayout = (LinearLayout) findViewById(R.id.address_layoutt);

        mIMLayout = (LinearLayout) findViewById(R.id.im_layoutt);

        mwebsiteLayout = (LinearLayout) findViewById(R.id.websit_layoutt);

        mRelationLayout = (LinearLayout) findViewById(R.id.relation_layoutt);

        mNotesLayout = (LinearLayout) findViewById(R.id.notes_layoutt);

        mNicknameLayout = (LinearLayout) findViewById(R.id.nickname_layoutt);

        mOrgLayout = (LinearLayout) findViewById(R.id.org_layoutt);

        mPhoneticLayout = (LinearLayout) findViewById(R.id.phonetic_layoutt);

        mPhoneLable = (TextView) findViewById(R.id.phoneLablee);

        mEmailLable = (TextView) findViewById(R.id.emailLablee);

        mAddressLable = (TextView) findViewById(R.id.addressLablee);

        mIMLable = (TextView) findViewById(R.id.imLablee);

        mwebsiteLable = (TextView) findViewById(R.id.websiteLablee);

        mRelationLabel = (TextView) findViewById(R.id.relationLablee);

        mNotesLabel = (TextView) findViewById(R.id.notesLablee);

        mNicknameLabel = (TextView) findViewById(R.id.nicknameLablee);

        mOrgLabel = (TextView) findViewById(R.id.orgLablee);

        mPhoneticLable = (TextView) findViewById(R.id.phoneticLablee);


        mAddPhone = (ImageView) findViewById(R.id.addphonee);

        mAddEmail = (ImageView) findViewById(R.id.addemaill);

        mAddAddress = (ImageView) findViewById(R.id.addAddresss);

        mAddIML = (ImageView) findViewById(R.id.addimm);

        mAddwebsite = (ImageView) findViewById(R.id.addwebsitee);

        mAddRelation = (ImageView) findViewById(R.id.addrelationn);



        mOrgEditTExt=(EditText) findViewById(R.id.orgEditTextt);

        mNotesEditText=(EditText) findViewById(R.id.notesEditTextt);

        mPhoneticEditText=(EditText) findViewById(R.id.phoneticEditTextt);

        mNickNameEditText=(EditText) findViewById(R.id.nickNameEditTextt);



        mAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_PHONE, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_PHONE, "");
                addPhoneDynamically(lDynamicLayout);

            }
        });

        mAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_EMAIL, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_EMAIL, "");

                addEmailDynamilcally(lDynamicLayout);

            }
        });

        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_ADDRESS, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_ADDRESS, "");

                addAddressDynamilcally(lDynamicLayout);

            }
        });

        mAddwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_WEBSITE, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_WEBSITE, "");

                addWebsiteDynamilcally(lDynamicLayout);

            }
        });

        mAddIML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_IM, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_IM, "");

                addIMDynamilcally(lDynamicLayout);

            }
        });


        mAddRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBaseHelper.addContactDetails(COLUMN_TYPE_RELATION, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mDataBaseHelper.getContactDetailId(), COLUMN_TYPE_RELATION, "");

                addRelationDynamilcally(lDynamicLayout);

            }
        });


    }

    LinearLayout addDynamicLayout(String pContacDetailtId, final String pType, String pValue) {


        mIdTextArrayList.add(pContacDetailtId);
        mTypeArrayList.add(pType);

        final LinearLayout lDynamicLayout = new LinearLayout(this);
        lDynamicLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lDynamicLayout.setOrientation(LinearLayout.HORIZONTAL);


        EditText lValue = new EditText(this);
        lValue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 8));

        if(pType.equals(COLUMN_TYPE_PHONE)) {

            lValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        lValue.setText(pValue);
        lDynamicLayout.addView(lValue);
        mValueArralist.add(lValue);

        ImageView lDeleteImage = new ImageView(this);

        lDeleteImage.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        lDeleteImage.setPadding(0, 20, 0, 0);
        lDeleteImage.setId(mImageIdToDelete++);
        Drawable res = getResources().getDrawable(R.drawable.ic_remove_black_24dp);
        lDeleteImage.setImageDrawable(res);

        if(!pType.equals(COLUMN_CONTACT_ORGANIZATION) && !pType.equals(COLUMN_CONTACT_NOTES) && !pType.equals(COLUMN_CONTACT_NICK_NAME) && !pType.equals(COLUMN_CONTACT_PHONETIC_NAME)){

            lDynamicLayout.addView(lDeleteImage);

        }

        lDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lContactId = mIdTextArrayList.get(v.getId());

                mDataBaseHelper.deleteContactDetails(Integer.parseInt(lContactId));

                lDynamicLayout.setVisibility(View.GONE);


            }
        });

        return lDynamicLayout;
    }

    void addPhoneDynamically(LinearLayout pDynamicLayout) {

        mPhoneLayout.addView(pDynamicLayout);
        mPhoneLayout.setVisibility(View.VISIBLE);



    }

    void addEmailDynamilcally(LinearLayout pDynamicLayout) {
        mEmailLayout.addView(pDynamicLayout);
        mEmailLayout.setVisibility(View.VISIBLE);


    }

    void addAddressDynamilcally(LinearLayout pDynamicLayout) {

        mAddressLayout.addView(pDynamicLayout);
        mAddressLayout.setVisibility(View.VISIBLE);


    }

    void addIMDynamilcally(LinearLayout pDynamicLayout) {

        mIMLayout.addView(pDynamicLayout);
        mIMLayout.setVisibility(View.VISIBLE);


    }

    void addWebsiteDynamilcally(LinearLayout pDynamicLayout) {

        mwebsiteLayout.addView(pDynamicLayout);
        mwebsiteLayout.setVisibility(View.VISIBLE);


    }


    void addRelationDynamilcally(LinearLayout pDynamicLayout) {

        mRelationLayout.addView(pDynamicLayout);
        mRelationLayout.setVisibility(View.VISIBLE);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button:

                for (int i = 0; i < mIdTextArrayList.size(); i++) {

                    String lContactDetailId = mIdTextArrayList.get(i).toString();

                    String lValue=mValueArralist.get(i).getText().toString();

                    if(lValue!=null && !lValue.isEmpty())

                        mDataBaseHelper.updateContactDeatails(lContactDetailId, lValue);

                    else
                    {
                        mDataBaseHelper.deleteContactDetails(Integer.parseInt(lContactDetailId));
                    }

                }

                mDataBaseHelper.insertContatct(mContactId,
                        mNameEditText.getText().toString(),
                        mOrgEditTExt.getText().toString(),
                        mNickNameEditText.getText().toString(),
                        mPhoneticEditText.getText().toString(),
                        mNotesEditText.getText().toString());

                mDataBaseHelper.deleteNullValues();

                Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent();
                intent.putExtra("MESSAGE", "Contact Added");
                setResult(RESULT_OK, intent);
                this.finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
