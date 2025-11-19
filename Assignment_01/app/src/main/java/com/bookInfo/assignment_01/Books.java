package com.bookInfo.assignment_01;

import android.os.Parcel;
import android.os.Parcelable;

public class Books implements Parcelable {
    private String title;
    private String author;
    private String date;
    public Books(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    private Books(Parcel in){
        title = in.readString();
        author = in.readString();
        date = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
