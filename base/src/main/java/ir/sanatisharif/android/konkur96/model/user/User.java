package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("nameSlug")
    @Expose
    private Object nameSlug;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("mobile_verified_at")
    @Expose
    private Object mobileVerifiedAt;
    @SerializedName("whatsapp")
    @Expose
    private Object whatsapp;
    @SerializedName("skype")
    @Expose
    private Object skype;
    @SerializedName("nationalCode")
    @Expose
    private String nationalCode;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("lockProfile")
    @Expose
    private Integer lockProfile;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("province")
    @Expose
    private Object province;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("postalCode")
    @Expose
    private Object postalCode;
    @SerializedName("school")
    @Expose
    private Object school;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("bio")
    @Expose
    private Object bio;
    @SerializedName("introducedBy")
    @Expose
    private Object introducedBy;
    @SerializedName("bloodtype_id")
    @Expose
    private Object bloodtypeId;
    @SerializedName("allergy")
    @Expose
    private Object allergy;
    @SerializedName("medicalCondition")
    @Expose
    private Object medicalCondition;
    @SerializedName("diet")
    @Expose
    private Object diet;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    private int gender_id;
    private int major_id;

    protected User(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.nameSlug = in.readValue((Object.class.getClassLoader()));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.emailVerifiedAt = in.readValue((Object.class.getClassLoader()));
        this.mobileVerifiedAt = in.readValue((Object.class.getClassLoader()));
        this.whatsapp = in.readValue((Object.class.getClassLoader()));
        this.skype = in.readValue((Object.class.getClassLoader()));
        this.nationalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.lockProfile = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.province = in.readValue((Object.class.getClassLoader()));
        this.city = in.readValue((Object.class.getClassLoader()));
        this.address = in.readValue((Object.class.getClassLoader()));
        this.postalCode = in.readValue((Object.class.getClassLoader()));
        this.school = in.readValue((Object.class.getClassLoader()));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.bio = in.readValue((Object.class.getClassLoader()));
        this.introducedBy = in.readValue((Object.class.getClassLoader()));
        this.bloodtypeId = in.readValue((Object.class.getClassLoader()));
        this.allergy = in.readValue((Object.class.getClassLoader()));
        this.medicalCondition = in.readValue((Object.class.getClassLoader()));
        this.diet = in.readValue((Object.class.getClassLoader()));
        this.info = ((Info) in.readValue((Info.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.major_id = ((int) in.readValue((String.class.getClassLoader())));
        this.gender_id = ((int) in.readValue((String.class.getClassLoader())));
    }

    public User() {
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

    public Object getNameSlug() {
        return nameSlug;
    }

    public void setNameSlug(Object nameSlug) {
        this.nameSlug = nameSlug;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Object getMobileVerifiedAt() {
        return mobileVerifiedAt;
    }

    public void setMobileVerifiedAt(Object mobileVerifiedAt) {
        this.mobileVerifiedAt = mobileVerifiedAt;
    }

    public Object getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Object whatsapp) {
        this.whatsapp = whatsapp;
    }

    public Object getSkype() {
        return skype;
    }

    public void setSkype(Object skype) {
        this.skype = skype;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getLockProfile() {
        return lockProfile;
    }

    public void setLockProfile(Integer lockProfile) {
        this.lockProfile = lockProfile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(Object school) {
        this.school = school;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getIntroducedBy() {
        return introducedBy;
    }

    public void setIntroducedBy(Object introducedBy) {
        this.introducedBy = introducedBy;
    }

    public Object getBloodtypeId() {
        return bloodtypeId;
    }

    public void setBloodtypeId(Object bloodtypeId) {
        this.bloodtypeId = bloodtypeId;
    }

    public Object getAllergy() {
        return allergy;
    }

    public void setAllergy(Object allergy) {
        this.allergy = allergy;
    }

    public Object getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(Object medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public Object getDiet() {
        return diet;
    }

    public void setDiet(Object diet) {
        this.diet = diet;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(nameSlug);
        dest.writeValue(password);
        dest.writeValue(mobile);
        dest.writeValue(emailVerifiedAt);
        dest.writeValue(mobileVerifiedAt);
        dest.writeValue(whatsapp);
        dest.writeValue(skype);
        dest.writeValue(nationalCode);
        dest.writeValue(lockProfile);
        dest.writeValue(photo);
        dest.writeValue(province);
        dest.writeValue(city);
        dest.writeValue(address);
        dest.writeValue(postalCode);
        dest.writeValue(school);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(email);
        dest.writeValue(bio);
        dest.writeValue(introducedBy);
        dest.writeValue(bloodtypeId);
        dest.writeValue(allergy);
        dest.writeValue(medicalCondition);
        dest.writeValue(diet);
        dest.writeValue(info);
        dest.writeValue(fullName);
        dest.writeValue(gender_id);
        dest.writeValue(major_id);
    }

    public int describeContents() {
        return 0;
    }


}
