package com.example.user.customlistview.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.customlistview.Custom.RoundedTransformation;
import com.example.user.customlistview.R;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 09/04/17.
 */

public class CustomListViewAdapter extends ArrayAdapter<String> {
    String[] mContactName;
    String[] mContactId;
    String[] mContactImageUri;
    Activity mContext;




    public CustomListViewAdapter(Activity context, String[] pContactName, String[] pContactId,String[] pContactImageUri) {
        // super();

        super(context, R.layout.list_layout, pContactName);
        mContactId=new String[pContactId.length];
        mContactName=new String[pContactId.length];
        mContactImageUri=new String[pContactId.length];

        mContext = context;
        mContactName = pContactName;
        mContactId = pContactId;
        mContactImageUri=pContactImageUri;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_layout, null, true);
        TextView lContactNameTextView = (TextView) listItemView.findViewById(R.id.name);
        TextView lMobileNumberTextView = (TextView) listItemView.findViewById(R.id.no);
        ImageView lContactImageView=(ImageView) listItemView.findViewById(R.id.ContactImage);

        lContactNameTextView.setText(mContactName[position]);
        lMobileNumberTextView.setText(mContactId[position]);


        String limgUri=mContactImageUri[position];

            Picasso.with(getContext())
                    .load(mContactImageUri[position])
                    .placeholder(R.drawable.contact_img)
                    .resize(200, 200)
                    .transform(new RoundedTransformation(100, 1))
                    .into(lContactImageView);

        return listItemView;
    }


}
