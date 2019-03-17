
package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info implements Parcelable
{

    @SerializedName("major")
    @Expose
    private Major major;
    @SerializedName("grade")
    @Expose
    private Object grade;
    @SerializedName("gender")
    @Expose
    private Gender gender;
    @SerializedName("completion")
    @Expose
    private int completion;
    @SerializedName("wallet")
    @Expose
    private Object wallet;
    public final static Creator<Info> CREATOR = new Creator<Info>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        public Info[] newArray(int size) {
            return (new Info[size]);
        }

    }
    ;

    protected Info(Parcel in) {
        this.major = ((Major) in.readValue((Major.class.getClassLoader())));
        this.grade = ((Object) in.readValue((Object.class.getClassLoader())));
        this.gender = ((Gender) in.readValue((Object.class.getClassLoader())));
        this.completion = ((int) in.readValue((int.class.getClassLoader())));
        this.wallet = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Info() {
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Info withMajor(Major major) {
        this.major = major;
        return this;
    }

    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
        this.grade = grade;
    }

    public Info withGrade(Gender grade) {
        this.grade = grade;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Info withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public Info withCompletion(int completion) {
        this.completion = completion;
        return this;
    }

    public Object getWallet() {
        return wallet;
    }

    public void setWallet(Object wallet) {
        this.wallet = wallet;
    }

    public Info withWallet(Object wallet) {
        this.wallet = wallet;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(major);
        dest.writeValue(grade);
        dest.writeValue(gender);
        dest.writeValue(completion);
        dest.writeValue(wallet);
    }

    public int describeContents() {
        return  0;
    }

}
