package eu.ase.damapp.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    private int imgDrawable;
    private String name;
    private float rating;

    public Category(int imgDrawable, String name, float rating) {
        this.imgDrawable = imgDrawable;
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(this.rating);
    }

    private Category(Parcel in) {
        this.name = in.readString();
        this.rating = in.readFloat();
    }

    public static Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            //todo create a constructor that has Parcel as param
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(int imgDrawable) {
        this.imgDrawable = imgDrawable;
    }
}
