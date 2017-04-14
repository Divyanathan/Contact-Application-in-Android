package com.example.user.customlistview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.customlistview.Custom.RoundedTransformation;
import com.example.user.customlistview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 13/04/17.
 */

public class PhoneNumberRecylerViewAdapter extends RecyclerView.Adapter<PhoneNumberRecylerViewAdapter.MyViewHolder> {


    //MyViewHolder holder;

   // String[] mPhoneNumber;
    ArrayList<String> mPhoneNumber=new ArrayList<String>();
    ArrayList<String> mIsNumberEmail=new ArrayList<String>();
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mPhoneNumberText;
        ImageView mCallImage;
        ImageView mSmsImage;
        Context lContext;

        public MyViewHolder(View itemView) {

            super(itemView);
            lContext = itemView.getContext();
            mPhoneNumberText = (TextView) itemView.findViewById(R.id.phoneText);
            mCallImage = (ImageView) itemView.findViewById(R.id.callImg);
            mSmsImage = (ImageView) itemView.findViewById(R.id.smsImg);

        }
    }


    public PhoneNumberRecylerViewAdapter(Context pContext, ArrayList<String> pPhoneNumber,ArrayList<String> pIsnumberOrMail){
        mContext=pContext;
        mPhoneNumber=pPhoneNumber;
        mIsNumberEmail=pIsnumberOrMail;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_phone_nimber_row,parent, false);

         //holder=new MyViewHolder(v);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (mIsNumberEmail.get(position).equals("number")) {
            holder.mPhoneNumberText.setText(mPhoneNumber.get(position));
            Picasso.with(mContext)
                    .load(R.drawable.call_img)
                    //.placeholder(R.drawable.displlay_img_of_contact)
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mCallImage);

            Picasso.with(mContext)
                    .load(R.drawable.icon_sms_green)
                    // .placeholder()
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mSmsImage);


        }else {
            holder.mPhoneNumberText.setText(mPhoneNumber.get(position));
            Picasso.with(mContext)
                    .load(R.drawable.call_img)
                    //.placeholder(R.drawable.displlay_img_of_contact)
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mCallImage);

                    holder.mCallImage.setVisibility(View.GONE);
            Picasso.with(mContext)
                    .load(R.drawable.mail_img)
                    // .placeholder()
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mSmsImage);

        }
    }

    @Override
    public int getItemCount() {
        return mPhoneNumber.size();
    }
}
