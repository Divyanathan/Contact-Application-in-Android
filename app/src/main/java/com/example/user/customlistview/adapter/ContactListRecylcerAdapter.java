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
import com.example.user.customlistview.jdo.ContactJDO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 16/04/17.
 */

public class ContactListRecylcerAdapter  extends RecyclerView.Adapter<ContactListRecylcerAdapter.MyViewHolder> {




    ArrayList<ContactJDO> mContactJDOArrayList;
    ContactJDO mContactJDO;
    Context mContext;

    public ContactListRecylcerAdapter( Context mContext,ArrayList<ContactJDO> mContactJDOArrayList) {
        this.mContactJDOArrayList = mContactJDOArrayList;
        this.mContext = mContext;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mContactNameTextView;
        TextView mContactId;
        ImageView mContactImageView;
        Context mContext;

        public MyViewHolder(View itemView) {

            super(itemView);
            mContext = itemView.getContext();
            mContactNameTextView = (TextView) itemView.findViewById(R.id.name);
            mContactId = (TextView) itemView.findViewById(R.id.contctId);
            mContactImageView =(ImageView) itemView.findViewById(R.id.ContactImage);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout,parent, false);

        //holder=new MyViewHolder(v);
        return new ContactListRecylcerAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        mContactJDO=mContactJDOArrayList.get(position);

        holder.mContactNameTextView.setText(mContactJDO.getmContactName());
        holder.mContactId.setText(mContactJDO.getmCotactId());

        Picasso.with(mContext)
                .load(mContactJDO.getmCotactImage())
                .placeholder(R.drawable.contact_img)
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 1))
                .into(holder.mContactImageView);

    }

    @Override
    public int getItemCount() {
        return mContactJDOArrayList.size();
    }

    public void changePositionOnMove(int pDraggingPosition,int pTargetPosition){
        ContactJDO lDraggingConact=mContactJDOArrayList.get(pDraggingPosition);
        ContactJDO lTargetContact=mContactJDOArrayList.get(pTargetPosition);
        /**
         * swaping the Contacts
         */
        mContactJDOArrayList.set(pDraggingPosition,lTargetContact);
        mContactJDOArrayList.set(pTargetPosition,lDraggingConact);

        notifyItemMoved(pDraggingPosition,pTargetPosition);


    }

}
