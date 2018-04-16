package ru.customservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergey-rush on 14.04.2018.
 */
public class Post implements Parcelable {

    private int id;
    private int userId;
    private String title;
    private String body;

    public Post() {
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    private Post(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(title);
        parcel.writeString(body);
    }

    public void readFromParcel(Parcel parcel) {
        id = parcel.readInt();
        userId = parcel.readInt();
        title = parcel.readString();
        body = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
