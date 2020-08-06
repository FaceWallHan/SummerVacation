package com.start.head.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Fruit extends LitePalSupport implements Parcelable {
    private int typeId;
    private String name;

    protected Fruit(Parcel in) {
        //读取的顺序一定要和写出的顺序完全相同
        name = in.readString();
        typeId = in.readInt();
    }

    public static final Parcelable.Creator<Fruit> CREATOR = new Parcelable.Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fruit(int typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public Fruit() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);//写出name
        parcel.writeInt(typeId);//写出typeId
    }
}
