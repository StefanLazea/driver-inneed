package eu.ase.damapp.network;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String question;
    private String img;
    private Answer answer;

    public Item(String question, String img, Answer answer) {
        this.question = question;
        this.img = img;
        this.answer = answer;
    }

    protected Item(Parcel in) {
        question = in.readString();
        img = in.readString();
        answer = in.readParcelable(Answer.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Item{" +
                "question='" + question + '\'' +
                ", img='" + img + '\'' +
                ", answer=" + answer +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(img);
        dest.writeParcelable(answer,flags);
    }
}

