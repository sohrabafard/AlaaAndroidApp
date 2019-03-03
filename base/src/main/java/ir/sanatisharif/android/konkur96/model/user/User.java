
package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

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
    @SerializedName("password")
    @Expose
    private String password;
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
    @SerializedName("lockProfile")
    @Expose
    private int lockProfile;
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
    private Object email;
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
    @SerializedName("gender")
    @Expose
    private Object gender;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;

    protected User(Parcel in) {
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.nameSlug = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.emailVerifiedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mobileVerifiedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.whatsapp = ((Object) in.readValue((Object.class.getClassLoader())));
        this.skype = ((Object) in.readValue((Object.class.getClassLoader())));
        this.nationalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.lockProfile = ((int) in.readValue((int.class.getClassLoader())));
        this.province = ((Object) in.readValue((Object.class.getClassLoader())));
        this.city = ((Object) in.readValue((Object.class.getClassLoader())));
        this.address = ((Object) in.readValue((Object.class.getClassLoader())));
        this.postalCode = ((Object) in.readValue((Object.class.getClassLoader())));
        this.school = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bio = ((Object) in.readValue((Object.class.getClassLoader())));
        this.introducedBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bloodtypeId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.allergy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.medicalCondition = ((Object) in.readValue((Object.class.getClassLoader())));
        this.diet = ((Object) in.readValue((Object.class.getClassLoader())));
        this.info = ((Info) in.readValue((Info.class.getClassLoader())));
        this.gender = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Object getNameSlug() {
        return nameSlug;
    }

    public void setNameSlug(Object nameSlug) {
        this.nameSlug = nameSlug;
    }

    public User withNameSlug(Object nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public User withEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
        return this;
    }

    public Object getMobileVerifiedAt() {
        return mobileVerifiedAt;
    }

    public void setMobileVerifiedAt(Object mobileVerifiedAt) {
        this.mobileVerifiedAt = mobileVerifiedAt;
    }

    public User withMobileVerifiedAt(Object mobileVerifiedAt) {
        this.mobileVerifiedAt = mobileVerifiedAt;
        return this;
    }

    public Object getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Object whatsapp) {
        this.whatsapp = whatsapp;
    }

    public User withWhatsapp(Object whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public Object getSkype() {
        return skype;
    }

    public void setSkype(Object skype) {
        this.skype = skype;
    }

    public User withSkype(Object skype) {
        this.skype = skype;
        return this;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public User withNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        return this;
    }

    public int getLockProfile() {
        return lockProfile;
    }

    public void setLockProfile(int lockProfile) {
        this.lockProfile = lockProfile;
    }

    public User withLockProfile(int lockProfile) {
        this.lockProfile = lockProfile;
        return this;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public User withProvince(Object province) {
        this.province = province;
        return this;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public User withCity(Object city) {
        this.city = city;
        return this;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public User withAddress(Object address) {
        this.address = address;
        return this;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public User withPostalCode(Object postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(Object school) {
        this.school = school;
    }

    public User withSchool(Object school) {
        this.school = school;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public User withEmail(Object email) {
        this.email = email;
        return this;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public User withBio(Object bio) {
        this.bio = bio;
        return this;
    }

    public Object getIntroducedBy() {
        return introducedBy;
    }

    public void setIntroducedBy(Object introducedBy) {
        this.introducedBy = introducedBy;
    }

    public User withIntroducedBy(Object introducedBy) {
        this.introducedBy = introducedBy;
        return this;
    }

    public Object getBloodtypeId() {
        return bloodtypeId;
    }

    public void setBloodtypeId(Object bloodtypeId) {
        this.bloodtypeId = bloodtypeId;
    }

    public User withBloodtypeId(Object bloodtypeId) {
        this.bloodtypeId = bloodtypeId;
        return this;
    }

    public Object getAllergy() {
        return allergy;
    }

    public void setAllergy(Object allergy) {
        this.allergy = allergy;
    }

    public User withAllergy(Object allergy) {
        this.allergy = allergy;
        return this;
    }

    public Object getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(Object medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public User withMedicalCondition(Object medicalCondition) {
        this.medicalCondition = medicalCondition;
        return this;
    }

    public Object getDiet() {
        return diet;
    }

    public void setDiet(Object diet) {
        this.diet = diet;
    }

    public User withDiet(Object diet) {
        this.diet = diet;
        return this;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public User withInfo(Info info) {
        this.info = info;
        return this;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public User withGender(Object gender) {
        this.gender = gender;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(nameSlug);
        dest.writeValue(mobile);
        dest.writeValue(emailVerifiedAt);
        dest.writeValue(mobileVerifiedAt);
        dest.writeValue(whatsapp);
        dest.writeValue(skype);
        dest.writeValue(nationalCode);
        dest.writeValue(lockProfile);
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
        dest.writeValue(gender);
    }

    public int describeContents() {
        return  0;
    }

}
