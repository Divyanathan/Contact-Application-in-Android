package com.example.user.customlistview.jdo;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by user on 18/04/17.
 */

public class ContactJDO implements Serializable,Comparable<ContactJDO> {

    String mCotactId;
    String mContactName;
    String mCotactImage;
    String mCotactOrganization;
    String mCotactNotes;
    String mCotactNickname;
    String mCotactPhoneticname;


    public ContactJDO(String mCotactId, String mContactName, String mCotactImage,
                      String mCotactOrganization, String mCotactNotes,
                      String mCotactNickname, String mCotactPhoneticname) {
        this.mCotactId = mCotactId;
        this.mContactName = mContactName;
        this.mCotactImage = mCotactImage;
        this.mCotactOrganization = mCotactOrganization;
        this.mCotactNotes = mCotactNotes;
        this.mCotactNickname = mCotactNickname;
        this.mCotactPhoneticname = mCotactPhoneticname;
    }

    public ContactJDO() {
    }

    public String getmCotactId() {
        return mCotactId;
    }

    public String getmContactName() {
        return mContactName;
    }

    public String getmCotactImage() {
        return mCotactImage;
    }

    public String getmCotactOrganization() {
        return mCotactOrganization;
    }

    public String getmCotactNotes() {
        return mCotactNotes;
    }

    public String getmCotactNickname() {
        return mCotactNickname;
    }

    public String getmCotactPhoneticname() {
        return mCotactPhoneticname;
    }

    public void setmCotactId(String mCotactId) {
        this.mCotactId = mCotactId;
    }

    public void setmContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public void setmCotactImage(String mCotactImage) {
        this.mCotactImage = mCotactImage;
    }

    public void setmCotactOrganization(String mCotactOrganization) {
        this.mCotactOrganization = mCotactOrganization;
    }

    public void setmCotactNotes(String mCotactNotes) {
        this.mCotactNotes = mCotactNotes;
    }

    public void setmCotactNickname(String mCotactNickname) {
        this.mCotactNickname = mCotactNickname;
    }

    public void setmCotactPhoneticname(String mCotactPhoneticname) {
        this.mCotactPhoneticname = mCotactPhoneticname;
    }


    @Override
    public int compareTo(@NonNull ContactJDO o) {
        return this.getmContactName().toLowerCase().compareTo(o.getmContactName().toLowerCase());
    }
}
