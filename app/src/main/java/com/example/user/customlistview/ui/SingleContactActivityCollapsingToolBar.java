package com.example.user.customlistview.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.user.customlistview.R;
import com.example.user.customlistview.custom.RoundedTransformation;
import com.example.user.customlistview.database.DataBaseHelper;
import com.squareup.picasso.Picasso;

public class SingleContactActivityCollapsingToolBar extends AppCompatActivity  {


    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    String mContactId;

    ImageView mContactImageView;
    RecyclerView mRecyclerView;
    DataBaseHelper mDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact_collapsing_tool_bar);

        mContactImageView=(ImageView) findViewById(R.id.contactImaeForCollapsing);

        mDataBaseHelper= new DataBaseHelper(this, null, null, 1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle("divyanathan");
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(SingleContactActivityCollapsingToolBar.this, R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(SingleContactActivityCollapsingToolBar.this, R.color.white));


        Intent pValueOfTheContactIdFromList = getIntent();
        mContactId = pValueOfTheContactIdFromList.getStringExtra("Id");
       // getLoaderManager().initLoader(1, null, this);
        getTheNameAndImage();
    }


    void getTheNameAndImage()
    {

        Cursor lNameAndImage=mDataBaseHelper.getNameAndImageForSinglecontact(mContactId);
        lNameAndImage.moveToFirst();
        collapsingToolbarLayout.setTitle(lNameAndImage.getString(lNameAndImage.getColumnIndex(mDataBaseHelper.COLUMN_CONTACT_NAME)));
        //collapsingToolbarLayout.setGravity(Gravity.CENTER_HORIZONTAL);

       String lContactName= lNameAndImage.getString(lNameAndImage.getColumnIndex(mDataBaseHelper.COLUMN_CONTACT_PHOTO));
        Picasso.with(this)
                .load(lContactName)
                .placeholder(R.drawable.contact_person_icon)
               // .resize(200, 200)
                //.transform(new RoundedTransformation(100, 1))
                .into(mContactImageView);


    }

}
