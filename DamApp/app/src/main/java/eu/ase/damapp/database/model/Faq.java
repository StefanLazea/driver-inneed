package eu.ase.damapp.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "faq",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "idUserFaq"),
        indices = {@Index("application_rating"),@Index("idUserFaq")}

)
public class Faq implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "question")
    public String question;
    @ColumnInfo(name = "question_category")
    public String questionCategory;
    @ColumnInfo(name = "application_rating")
    public float applicationRating;
    @ColumnInfo(name = "idUserFaq")
    public long idUserFaq;

    @Ignore
    public Faq(String question, String questionCategory, float applicationRating, long idUserFaq) {
        this.question = question;
        this.questionCategory = questionCategory;
        this.applicationRating = applicationRating;
        this.idUserFaq = idUserFaq;
    }

    public Faq(long id, String question, String questionCategory, float applicationRating, long idUserFaq) {
        this.id = id;
        this.question = question;
        this.questionCategory = questionCategory;
        this.applicationRating = applicationRating;
        this.idUserFaq = idUserFaq;
    }

    protected Faq(Parcel in) {
        id = in.readLong();
        question = in.readString();
        questionCategory = in.readString();
        applicationRating = in.readFloat();
        idUserFaq = in.readLong();
    }

    public static final Creator<Faq> CREATOR = new Creator<Faq>() {
        @Override
        public Faq createFromParcel(Parcel source) {
            return new Faq(source);
        }

        @Override
        public Faq[] newArray(int size) {
            return new Faq[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(question);
        dest.writeString(questionCategory);
        dest.writeFloat(applicationRating);
        dest.writeLong(idUserFaq);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public float getApplicationRating() {
        return applicationRating;
    }

    public void setApplicationRating(float applicationRating) {
        this.applicationRating = applicationRating;
    }

    public long getIdUserFaq() {
        return idUserFaq;
    }

    public void setIdUserFaq(long idUserFaq) {
        this.idUserFaq = idUserFaq;
    }

    @Override
    public String toString() {
        return "Faq{" +
                "question='" + question + '\'' +
                ", questionCategory='" + questionCategory + '\'' +
                ", applicationRating=" + applicationRating +
                ", idUserFaq=" + idUserFaq +
                '}';
    }
}


