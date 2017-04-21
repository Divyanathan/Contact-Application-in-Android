package com.example.user.customlistview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.customlistview.R;
import com.example.user.customlistview.custom.RoundedTransformation;
import com.example.user.customlistview.jdo.ContactDetailsJDO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 18/04/17.
 */

public class SingleContactRecyclerAdapter extends RecyclerView.Adapter<SingleContactRecyclerAdapter.MyViewHolder> {


    ArrayList<ContactDetailsJDO> mContactDetailArryList=new ArrayList<ContactDetailsJDO>();

    ContactDetailsJDO mContactDetailsJDO;
    Boolean mIsPhoneLableAssigned =false;
    Boolean misEmailLableAssigned =false;
    Boolean misAddressleAssigned =false;
    Boolean mImLableAssigned =false;
    Boolean mIsWebsiteleAssigned =false;
    Boolean mIsRelationLableAssigned =false;


    Context mContext;

    public SingleContactRecyclerAdapter(Context mContext, ArrayList<ContactDetailsJDO> pContactDetailArryList) {
        mContactDetailArryList=pContactDetailArryList;

        this.mContext = mContext;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView mTitleTextView;
        TextView mValueTextView;
        ImageView mCallImageView;
        ImageView mSmsImageView;

        Context lContext;
        public MyViewHolder(View itemView) {

            super(itemView);
            lContext = itemView.getContext();
            mTitleTextView = (TextView) itemView.findViewById(R.id.label);
            mValueTextView = (TextView) itemView.findViewById(R.id.phoneText);
            mCallImageView = (ImageView) itemView.findViewById(R.id.callImg);
            mSmsImageView = (ImageView) itemView.findViewById(R.id.smsImg);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_phone_nimber_row,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        mContactDetailsJDO=new ContactDetailsJDO();

        mContactDetailsJDO=mContactDetailArryList.get(position);

        holder.mValueTextView.setText(mContactDetailsJDO.getCotactDetailstValue());

        if (mContactDetailsJDO.getCotactDetailstType().equals("phone")) {

            if(!mIsPhoneLableAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                mIsPhoneLableAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }
            Picasso.with(mContext)
                    .load(R.drawable.call_img)
                    //.placeholder(R.drawable.displlay_img_of_contact)
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mCallImageView);

            Picasso.with(mContext)
                    .load(R.drawable.icon_sms_green)
                    // .placeholder()
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mSmsImageView);


        }else if(mContactDetailsJDO.getCotactDetailstType().equals("email")) {


            if(!misEmailLableAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                misEmailLableAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }
            Picasso.with(mContext)
                    .load(R.drawable.call_img)
                    //.placeholder(R.drawable.displlay_img_of_contact)
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mCallImageView);

            holder.mCallImageView.setVisibility(View.GONE);
            Picasso.with(mContext)
                    .load(R.drawable.mail_img)
                    // .placeholder()
                    .resize(800, 800)
                    .transform(new RoundedTransformation(100, 1))
                    .into(holder.mSmsImageView);

        }else if(mContactDetailsJDO.getCotactDetailstType().equals("address")) {

            if(!misAddressleAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                misAddressleAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }

        }else if(mContactDetailsJDO.getCotactDetailstType().equals("im")) {

            if(!mImLableAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                mImLableAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }

        }else if(mContactDetailsJDO.getCotactDetailstType().equals("website")) {

            if(!mIsWebsiteleAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                mIsWebsiteleAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }

        }else if(mContactDetailsJDO.getCotactDetailstType().equals("relation")) {

            if(!mIsRelationLableAssigned){
                holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
                mIsRelationLableAssigned=true;
            }else {
                holder.mTitleTextView.setVisibility(View.GONE);
            }

        }
        else {
            holder.mTitleTextView.setText(mContactDetailsJDO.getCotactDetailstType());
        }

    }

    @Override
    public int getItemCount() {
        return mContactDetailArryList.size();
    }


}

