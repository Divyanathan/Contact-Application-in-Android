package com.example.user.customlistview.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.customlistview.R;
import com.example.user.customlistview.database.ContactDetailsTable;
import com.example.user.customlistview.database.CotactTable;
import com.example.user.customlistview.database.DataBaseHelper;
import com.example.user.customlistview.jdo.ContactDetailsJDO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.user.customlistview.database.DataBaseHelper.COLUMN_CONTACT_NOTES;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_NICK_NAME;
import static com.example.user.customlistview.utility.UtilityClass.COLUMN_CONTACT_ORGANIZATION;
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

public class EditContactActivity extends AppCompatActivity {

    ArrayList<ContactDetailsJDO> mContactDetails = new ArrayList<ContactDetailsJDO>();


    LinearLayout mEditLearLayout;

    TextView[] mTitleTextView;
    EditText[] mValueEditText;
    TextView mNameTextView;
    EditText mNameEditText;
    String mContactId;
    String mContactName;
    String mActionToPerfom;

    String mOldValue = "";
    String mNewValue = "";

    int mTextviewLength = 0;
    int mEditTextViewLength = 0;

    Boolean isAutoFocusWant = false;
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

    CotactTable mContactTable;
    ContactDetailsTable mContactDetailsTable;

    JSONArray mOldJasonArray = new JSONArray();
    JSONObject mActualJasonObject = new JSONObject();

    JSONArray mNewJosanArray = new JSONArray();
    JSONObject mNewJasonObject = new JSONObject();

    String mOldContactSting = "";
    String mNewContactString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.activity_edit_contact);


        mContactTable = new CotactTable(this);
        mContactTable.open();
        mContactDetailsTable = new ContactDetailsTable(this);
        mContactDetailsTable.open();

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

                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_PHONE, "", mContactId);


                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_PHONE, "");
                addPhoneDynamically(lDynamicLayout);

            }
        });

        mAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_EMAIL, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_EMAIL, "");

                addEmailDynamilcally(lDynamicLayout);

            }
        });

        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_ADDRESS, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_ADDRESS, "");

                addAddressDynamilcally(lDynamicLayout);

            }
        });

        mAddwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_WEBSITE, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_WEBSITE, "");

                addWebsiteDynamilcally(lDynamicLayout);

            }
        });

        mAddIML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_IM, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_IM, "");

                addIMDynamilcally(lDynamicLayout);

            }
        });


        mAddRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFocusWant = true;
                mContactDetailsTable.addContactDetails(COLUMN_TYPE_RELATION, "", mContactId);

                LinearLayout lDynamicLayout = addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_RELATION, "");

                addRelationDynamilcally(lDynamicLayout);

            }
        });

        mActionToPerfom = getIntent().getStringExtra("action");

        if (mActionToPerfom.equals("edit")) {

            mContactId = getIntent().getStringExtra("id");
            mContactName = getIntent().getStringExtra("name");

            mContactDetails = (ArrayList<ContactDetailsJDO>) getIntent().getSerializableExtra("contact_details");

            getValue();

        } else {

            mContactId = "" + mContactTable.getContactId();


            mIdTextArrayList.clear();
            mTypeArrayList.clear();
            mValueArralist.clear();
            mContactDetailsTable.addContactDetails(COLUMN_TYPE_PHONE, "", mContactId);
            addPhoneDynamically(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_PHONE, ""));

            mContactDetailsTable.addContactDetails(COLUMN_TYPE_ADDRESS, "", mContactId);
            addAddressDynamilcally(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_ADDRESS, ""));

            mContactDetailsTable.addContactDetails(COLUMN_TYPE_EMAIL, "", mContactId);
            addEmailDynamilcally(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_EMAIL, ""));

            mContactDetailsTable.addContactDetails(COLUMN_TYPE_IM, "", mContactId);
            addIMDynamilcally(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_IM, ""));

            mContactDetailsTable.addContactDetails(COLUMN_TYPE_WEBSITE, "", mContactId);
            addWebsiteDynamilcally(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_WEBSITE, ""));

            mContactDetailsTable.addContactDetails(COLUMN_TYPE_RELATION, "", mContactId);
            addRelationDynamilcally(addDynamicLayout(mContactDetailsTable.getContactDetailId(), COLUMN_TYPE_RELATION, ""));

            getOldValues();


        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEditLearLayout = (LinearLayout) findViewById(R.id.edit_layout);


        mOldContactSting = mNameEditText.getText().toString() + mPhoneticEditText.getText().toString() +
                mNotesEditText.getText().toString() + mOrgEditTExt.getText().toString() + mNickNameEditText.getText().toString();

    }


    void getValue() {

        mNameEditText.setText(mContactName);
        mNameTextView.setText("name");


        mTitleTextView = new TextView[mContactDetails.size()];
        mValueEditText = new EditText[mContactDetails.size()];


        ImageView lTest = new ImageView(EditContactActivity.this);


        for (int i = 0; i < mContactDetails.size(); i++) {

            mContactDetailsJDO = mContactDetails.get(i);


            try {

                mActualJasonObject.put(COLUMN_DETAIL_ID, mContactDetailsJDO.getCotactDetailstId());
                mActualJasonObject.put(COLUMN_DETAIL_TYPE, mContactDetailsJDO.getCotactDetailstType());
                mActualJasonObject.put(COLUMN_DETAIL_VALUE, mContactDetailsJDO.getCotactDetailstValue());
                mOldJasonArray.put(mActualJasonObject);
                mOldValue = mOldValue + mOldJasonArray.toString();


            } catch (Exception e) {

                e.printStackTrace();

            }
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

        int lPadding_in_dp = 15;  // 6 dps
        final float scale = getResources().getDisplayMetrics().density;
        int lPadding_in_px = (int) (lPadding_in_dp * scale + 0.5f);

        final LinearLayout lDynamicLayout = new LinearLayout(this);
        lDynamicLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lDynamicLayout.setPadding(0, lPadding_in_dp, 0, 0);
        lDynamicLayout.setOrientation(LinearLayout.HORIZONTAL);


        TextView lIdTextView = new TextView(this);
        lIdTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lIdTextView.setText(pContacDetailtId);
        lIdTextView.setVisibility(View.GONE);
        lDynamicLayout.addView(lIdTextView);

        mIdTextArrayList.add(lIdTextView);
        mTypeArrayList.add(pType);

        EditText lValue = new EditText(this);
        lValue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 8));

        if (pType.equals(COLUMN_TYPE_PHONE)) {

            lValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        }


        lValue.setBackgroundResource(R.drawable.edit_text);
        lValue.setText(pValue);
        lValue.requestFocus();

        if (isAutoFocusWant) {
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        lDynamicLayout.addView(lValue);
        mValueArralist.add(lValue);

        ImageView lDeleteImage = new ImageView(this);

        lDeleteImage.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        lDeleteImage.setId(mImageIdToDelete++);
        Drawable lDrawbleDeleteImage = getResources().getDrawable(R.drawable.ic_remove_black_24dp);
        lDeleteImage.setImageDrawable(lDrawbleDeleteImage);

        if (!pType.equals(COLUMN_CONTACT_ORGANIZATION) && !pType.equals(COLUMN_CONTACT_NOTES) && !pType.equals(COLUMN_CONTACT_NICK_NAME) && !pType.equals(COLUMN_CONTACT_PHONETIC_NAME)) {

            lDynamicLayout.addView(lDeleteImage);

        }

        lDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lContactId = mIdTextArrayList.get(v.getId()).getText().toString();

                mContactDetailsTable.deleteContactDetails(Integer.parseInt(lContactId));

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

    void getOldValues(){

        for (int i = 0; i < mIdTextArrayList.size(); i++) {


            String lContactDetailId = mIdTextArrayList.get(i).getText().toString();
            String lType = mTypeArrayList.get(i);
            String lValue = mValueArralist.get(i).getText().toString();

            try {

                mActualJasonObject.put(COLUMN_DETAIL_ID, lContactDetailId);
                mActualJasonObject.put(COLUMN_DETAIL_TYPE, lType);
                mActualJasonObject.put(COLUMN_DETAIL_VALUE, lValue);
                mOldJasonArray.put(mActualJasonObject);
                mOldValue = mOldValue + mOldJasonArray.toString();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }


    }

    void getTheNewValue() {


        for (int i = 0; i < mIdTextArrayList.size(); i++) {


            String lContactDetailId = mIdTextArrayList.get(i).getText().toString();
            String lType = mTypeArrayList.get(i);
            String lValue = mValueArralist.get(i).getText().toString();

            try {

                mNewJasonObject.put(COLUMN_DETAIL_ID, lContactDetailId);
                mNewJasonObject.put(COLUMN_DETAIL_TYPE, lType);
                mNewJasonObject.put(COLUMN_DETAIL_VALUE, lValue);
                mNewJosanArray.put(mNewJasonObject);
                mNewValue = mNewValue + mNewJosanArray.toString();

            } catch (Exception e) {

                e.printStackTrace();
            }


            if (lValue != null && !lValue.isEmpty()) {
                mContactDetailsTable.updateContactDeatails(lContactDetailId, lValue);

//                        mCotactDetailsArrayList.add(new ContactDetailsJDO(lContactDetailId, mTypeArrayList.get(i), lValue));

            } else {

                mContactDetailsTable.deleteContactDetails(Integer.parseInt(lContactDetailId));
            }

        }
        mNewContactString = mNameEditText.getText().toString() + mPhoneticEditText.getText().toString() +
                mNotesEditText.getText().toString() + mOrgEditTExt.getText().toString() + mNickNameEditText.getText().toString();

        mIdTextArrayList.clear();
        mTypeArrayList.clear();
        mValueArralist.clear();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button:


                getTheNewValue();

                if (mActionToPerfom.equals("edit")) {

                    mContactTable.updateContact(mContactId,
                            mNameEditText.getText().toString(),
                            mOrgEditTExt.getText().toString(),
                            mNickNameEditText.getText().toString(),
                            mPhoneticEditText.getText().toString(),
                            mNotesEditText.getText().toString());

                    if (mOldValue.equals(mNewValue) && mNewContactString.equals(mOldContactSting)) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                    } else {
//                        Toast.makeText(this, "different", Toast.LENGTH_SHORT).show();
                    }

                } else {


                    mContactTable.insertContatct(mContactId,
                            mNameEditText.getText().toString(),
                            mOrgEditTExt.getText().toString(),
                            mNickNameEditText.getText().toString(),
                            mPhoneticEditText.getText().toString(),
                            mNotesEditText.getText().toString());

                    if (mOldValue.equals(mNewValue) && mNewContactString!=null && !mNewContactString.isEmpty() ){
                        customDialogBox();
                    }
                     else {

                        mContactTable.open();
                        mContactTable.deleteContactint(Integer.parseInt(mContactId));
                        finish();
                        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                    }
                }

                mContactDetailsTable.deleteNullValues();


                Intent intent = new Intent();

                if (mActionToPerfom.equals("edit")) {
                    intent.putExtra("MESSAGE", "updated successfully");
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    intent.putExtra("MESSAGE", "Added successfully");
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                }


                break;
        }
        return super.onOptionsItemSelected(item);

    }


    public boolean onSupportNavigateUp() {


        if (mActionToPerfom.equals("edit")) {
            getTheNewValue();
            if (mOldValue.equals(mNewValue) && mNewContactString.equals(mOldContactSting)) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }else {
                customDialogBox();
            }

        }else {
            getTheNewValue();

            if (mOldValue.equals(mNewValue) && mNewContactString!=null && !mNewContactString.isEmpty() ){
                customDialogBox();
            }
            else {

                mContactTable.open();
                mContactTable.deleteContactint(Integer.parseInt(mContactId));
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
        }

        return true;

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (mActionToPerfom.equals("edit")) {
            getTheNewValue();
            if (mOldValue.equals(mNewValue) && mNewContactString.equals(mOldContactSting)) {
                finish();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }else {

                customDialogBox();

            }

        }else {
            getTheNewValue();

            if (mOldValue.equals(mNewValue) && mNewContactString!=null && !mNewContactString.isEmpty() ){
                customDialogBox();
            }
            else {

                mContactTable.open();
                mContactTable.deleteContactint(Integer.parseInt(mContactId));
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
        }


    }

    void customDialogBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Want to quite DATA Didn't saved yet ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        if (mActionToPerfom.equals("edit")) {
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.setTitle("Save Data");
        alert.show();
    }
}
