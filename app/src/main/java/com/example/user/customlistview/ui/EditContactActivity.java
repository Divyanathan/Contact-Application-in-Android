package com.example.user.customlistview.ui;

import android.content.Intent;
import android.database.Cursor;
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
import com.example.user.customlistview.jdo.ContactDetailsJDO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.user.customlistview.database.DataBaseHelper.COLUMN_CONTACT_NOTES;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NICK_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_ORGANIZATION;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_PHONETIC_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_ADDRESS;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_EMAIL;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_IM;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_PHONE;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_RELATION;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_TYPE_WEBSITE;

public class EditContactActivity extends AppCompatActivity {

    ArrayList<ContactDetailsJDO> mContactDetails = new ArrayList<ContactDetailsJDO>();


    LinearLayout mEditLearLayout;

    TextView[] mTitleTextView;
    EditText[] mValueEditText;
    TextView mNameTextView;
    EditText mNameEditText;
    String mContactId;
    String mContactName;

    int mTextviewLength = 0;
    int mEditTextViewLength = 0;

    ArrayList<TextView> mIdTextArrayList = new ArrayList<TextView>();
    ArrayList<EditText> mValueArralist = new ArrayList<EditText>();
    ArrayList<String> mTypeArrayList = new ArrayList<String>();


    LinearLayout mPhoneLayout, mEmailLayout, mAddressLayout, mIMLayout, mwebsiteLayout;
    LinearLayout mRelationLayout, mOrgLayout, mNicknameLayout, mNotesLayout, mPhoneticLayout;

    TextView mPhoneLable, mEmailLable, mAddressLable, mIMLable, mwebsiteLable;
    TextView mRelationLabel, mOrgLabel, mNicknameLabel, mNotesLabel, mPhoneticLable;

    ImageView mAddPhone, mAddEmail, mAddAddress, mAddIML, mAddwebsite, mAddRelation;

    EditText mOrgEditTExt, mNotesEditText, mPhoneticEditText, mNickNameEditText;

    int mImageIdToDelete = 0;


    ContactDetailsJDO mContactDetailsJDO;
    DataBaseHelper mDataBaseHelper;

    ArrayList<ContactDetailsJDO> mCotactDetailsArrayList = new ArrayList<ContactDetailsJDO>();

    Boolean isSaveButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);


        mContactDetails = (ArrayList<ContactDetailsJDO>) getIntent().getSerializableExtra("contact_details");


        mDataBaseHelper = new DataBaseHelper(this, null, null, 1);

        mNameTextView = (TextView) findViewById(R.id.name_text_view);
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);


        mPhoneLayout = (LinearLayout) findViewById(R.id.phone_layout);

        mEmailLayout = (LinearLayout) findViewById(R.id.eamil_layout);

        mAddressLayout = (LinearLayout) findViewById(R.id.address_layout);

        mIMLayout = (LinearLayout) findViewById(R.id.im_layout);

        mwebsiteLayout = (LinearLayout) findViewById(R.id.websit_layout);

        mRelationLayout = (LinearLayout) findViewById(R.id.relation_layout);

        mNotesLayout = (LinearLayout) findViewById(R.id.notes_layout);

        mNicknameLayout = (LinearLayout) findViewById(R.id.nickname_layout);

        mOrgLayout = (LinearLayout) findViewById(R.id.org_layout);

        mPhoneticLayout = (LinearLayout) findViewById(R.id.phonetic_layout);


        mPhoneLable = (TextView) findViewById(R.id.phoneLable);

        mEmailLable = (TextView) findViewById(R.id.emailLable);

        mAddressLable = (TextView) findViewById(R.id.addressLable);

        mIMLable = (TextView) findViewById(R.id.imLable);

        mwebsiteLable = (TextView) findViewById(R.id.websiteLable);

        mRelationLabel = (TextView) findViewById(R.id.relationLable);

        mNotesLabel = (TextView) findViewById(R.id.notesLable);

        mNicknameLabel = (TextView) findViewById(R.id.nicknameLable);

        mOrgLabel = (TextView) findViewById(R.id.orgLable);

        mPhoneticLable = (TextView) findViewById(R.id.phoneticLable);


        mAddPhone = (ImageView) findViewById(R.id.addphone);

        mAddEmail = (ImageView) findViewById(R.id.addemail);

        mAddAddress = (ImageView) findViewById(R.id.addAddress);

        mAddIML = (ImageView) findViewById(R.id.addim);

        mAddwebsite = (ImageView) findViewById(R.id.addwebsite);

        mAddRelation = (ImageView) findViewById(R.id.addrelation);


        mOrgEditTExt = (EditText) findViewById(R.id.orgEditText);
        mNotesEditText = (EditText) findViewById(R.id.notesEditText);
        mPhoneticEditText = (EditText) findViewById(R.id.phoneticEditText);
        mNickNameEditText = (EditText) findViewById(R.id.nickNameEditText);

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


        mContactId = getIntent().getStringExtra("id");
        mContactName = getIntent().getStringExtra("name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEditLearLayout = (LinearLayout) findViewById(R.id.edit_layout);


        getValue();
    }


    void getValue() {

        mNameEditText.setText(mContactName);
        mNameTextView.setText("name");


        mTitleTextView = new TextView[mContactDetails.size()];
        mValueEditText = new EditText[mContactDetails.size()];


        ImageView lTest = new ImageView(EditContactActivity.this);


        int lPadding_in_dp = 6;  // 6 dps
        final float scale = getResources().getDisplayMetrics().density;
        int lPadding_in_px = (int) (lPadding_in_dp * scale + 0.5f);


        for (int i = 0; i < mContactDetails.size(); i++) {

            mContactDetailsJDO = mContactDetails.get(i);


/*
            mTitleTextView[i]=new TextView(this);
*/
//            mTitleTextView[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            mTitleTextView[i].setTextSize(15f);
//            mTitleTextView[i].setPadding(lPadding_in_px, 10, 0, 0);
//            mTitleTextView[i].setTextColor(Color.parseColor("#40C4FF"));
//            mTitleTextView[i].setText(mContactDetailsJDO.getCotactDetailstType());
//            mEditLearLayout.addView(mTitleTextView[i]);
//
//            mValueEditText[i] = new EditText(this);
//            mValueEditText[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            mValueEditText[i].setTextSize(22f);
//            mValueEditText[i].setPadding(lPadding_in_px, 0, 0, 50);
//            mValueEditText[i].setText(mContactDetailsJDO.getCotactDetailstValue());
//            mEditLearLayout.addView(mValueEditText[i]);
//

            String lContactValue = "";

            if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_ADDRESS) || mContactDetailsJDO.getCotactDetailstType().equals("org")) {

                String lValue[] = mContactDetailsJDO.getCotactDetailstValue().split(":");
                for (int j = 0; j < lValue.length; j++) {

                    lContactValue = lContactValue + lValue[j] + "\n";
                }
            } else {
                lContactValue = mContactDetailsJDO.getCotactDetailstValue();
            }

            lContactValue = lContactValue.trim();
            LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsJDO.getCotactDetailstId(),
                    mContactDetailsJDO.getCotactDetailstType(), lContactValue);


            if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_PHONE)) {

                addPhoneDynamically(lDynamicLayout);


            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_EMAIL)) {

                addEmailDynamilcally(lDynamicLayout);

            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_ADDRESS)) {

                addAddressDynamilcally(lDynamicLayout);


            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_IM)) {

                addIMDynamilcally(lDynamicLayout);

            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_WEBSITE)) {


                addWebsiteDynamilcally(lDynamicLayout);

            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_TYPE_RELATION)) {

                addRelationDynamilcally(lDynamicLayout);


            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_CONTACT_NICK_NAME)) {


                mNickNameEditText.setText(lContactValue);

            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_CONTACT_PHONETIC_NAME)) {

                mPhoneticEditText.setText(lContactValue);


            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_CONTACT_ORGANIZATION)) {


                mOrgEditTExt.setText(lContactValue);

            } else if (mContactDetailsJDO.getCotactDetailstType().equals(COLUMN_CONTACT_NOTES)) {

                mNotesEditText.setText(lContactValue);

            }


        }

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


    LinearLayout addDynamicLayout(String pContacDetailtId, final String pType, String pValue) {

        final LinearLayout lDynamicLayout = new LinearLayout(this);
        lDynamicLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lDynamicLayout.setOrientation(LinearLayout.HORIZONTAL);


        TextView lIdTextView = new TextView(this);
        lIdTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lIdTextView.setText(pContacDetailtId);
        lIdTextView.setVisibility(View.INVISIBLE);
        lDynamicLayout.addView(lIdTextView);

        mIdTextArrayList.add(lIdTextView);
        mTypeArrayList.add(pType);

        EditText lValue = new EditText(this);
        lValue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 8));

        if (pType.equals(COLUMN_TYPE_PHONE)) {

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

        if (!pType.equals(COLUMN_CONTACT_ORGANIZATION) && !pType.equals(COLUMN_CONTACT_NOTES) && !pType.equals(COLUMN_CONTACT_NICK_NAME) && !pType.equals(COLUMN_CONTACT_PHONETIC_NAME)) {

            lDynamicLayout.addView(lDeleteImage);

        }

        lDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lContactId = mIdTextArrayList.get(v.getId()).getText().toString();

                mDataBaseHelper.deleteContactDetails(Integer.parseInt(lContactId));

                lDynamicLayout.setVisibility(View.GONE);


            }
        });

        return lDynamicLayout;
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

                    String lContactDetailId = mIdTextArrayList.get(i).getText().toString();


                    String lValue = mValueArralist.get(i).getText().toString();
                    if (lValue != null && !lValue.isEmpty()) {
                        mDataBaseHelper.updateContactDeatails(lContactDetailId, mTypeArrayList.get(i), lValue);

//                        mCotactDetailsArrayList.add(new ContactDetailsJDO(lContactDetailId, mTypeArrayList.get(i), lValue));

                    } else {

                        mDataBaseHelper.deleteContactDetails(Integer.parseInt(lContactDetailId));
                    }

                }

                mDataBaseHelper.updateContact(mContactId,
                        mNameEditText.getText().toString(),
                        mOrgEditTExt.getText().toString(),
                        mNickNameEditText.getText().toString(),
                        mPhoneticEditText.getText().toString(),
                        mNotesEditText.getText().toString());


                mDataBaseHelper.deleteNullValues();


                isSaveButtonPressed = true;

                Intent intent = new Intent();

                intent.putExtra("MESSAGE", "updated successfully");

                setResult(RESULT_OK, intent);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    public boolean onSupportNavigateUp() {

        finish();
        return true;

    }
}
