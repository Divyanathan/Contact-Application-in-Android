package com.example.user.customlistview.jdo;

import java.io.Serializable;

/**
 * Created by user on 18/04/17.
 */

public class ContactDetailsJDO implements Serializable {
    String mCotactDetailstId;
    String mCotactDetailstType;
    String mCotactDetailstValue;
    String mCotactId;

    public ContactDetailsJDO() {
    }

    public ContactDetailsJDO(String pCotactDetailstId, String pCotactDetailstType, String pCotactDetailstValue, String pCotactId) {
        mCotactDetailstId = pCotactDetailstId;
        mCotactDetailstType = pCotactDetailstType;
        mCotactDetailstValue = pCotactDetailstValue;
        mCotactId = pCotactId;
    }

    public ContactDetailsJDO(String mCotactDetailstId, String mCotactDetailstType, String mCotactDetailstValue) {
        this.mCotactDetailstId = mCotactDetailstId;
        this.mCotactDetailstType = mCotactDetailstType;
        this.mCotactDetailstValue = mCotactDetailstValue;
    }

    public String getCotactDetailstId() {
        return mCotactDetailstId;
    }

    public void setCotactDetailstId(String pCotactDetailstId) {
        mCotactDetailstId = pCotactDetailstId;
    }

    public String getCotactDetailstType() {
        return mCotactDetailstType;
    }

    public void setCotactDetailstType(String pCotactDetailstType) {
        mCotactDetailstType = pCotactDetailstType;
    }

    public String getCotactDetailstValue() {
        return mCotactDetailstValue;
    }

    public void setCotactDetailstValue(String pCotactDetailstValue) {
        mCotactDetailstValue = pCotactDetailstValue;
    }

    public String getCotactId() {
        return mCotactId;
    }

    public void setCotactId(String pCotactId) {
        mCotactId = pCotactId;
    }
}
