/* *
   * Copyright (C) 2017 BaoliYota Tech. Co., Ltd, LLC - All Rights Reserved.
   *
   * Confidential and Proprietary.
   * Unauthorized copying of this file, via any medium is strictly prohibited.
   * */

package com.android.jv.ink.plugintest.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wanchi@coolpad.com
 * @version 1.0, 2017/5/31
 */
public class CyUserInfo implements Parcelable {

    private String name;
    private int userId;
    private String token;
    private int sex;

    public CyUserInfo() {
    }

    public CyUserInfo(String name, int userId, String token, int sex) {
        this.name = name;
        this.userId = userId;
        this.token = token;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.userId);
        dest.writeString(this.token);
        dest.writeInt(this.sex);
    }

    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        userId = dest.readInt();
        token = dest.readString();
        sex = dest.readInt();
    }

    protected CyUserInfo(Parcel in) {
        this.name = in.readString();
        this.userId = in.readInt();
        this.token = in.readString();
        this.sex = in.readInt();
    }

    public static final Creator<CyUserInfo> CREATOR = new Creator<CyUserInfo>() {
        @Override
        public CyUserInfo createFromParcel(Parcel source) {
            return new CyUserInfo(source);
        }

        @Override
        public CyUserInfo[] newArray(int size) {
            return new CyUserInfo[size];
        }
    };

    @Override
    public String toString() {
        return "CyUserInfo{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", sex=" + sex +
                '}';
    }
}

