
package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    public final static Parcelable.Creator<Author> CREATOR = new Creator<Author>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return (new Author[size]);
        }

    };

    protected Author(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(photo);
        dest.writeValue(fullName);
    }

    public int describeContents() {
        return 0;
    }

}
