package ir.sanatisharif.android.konkur96.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamad on 2/5/2019.
 */

public class Users {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("media_type")
    @Expose
    private Integer mediaType;
    @SerializedName("media_name")
    @Expose
    private String mediaName;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("liked")
    @Expose
    private Integer liked;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("isDeleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("userName")
    @Expose
    private String userName;
    private final static long serialVersionUID = 3774137690317975166L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Users withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users withContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Users withMediaType(Integer mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Users withMediaName(String mediaName) {
        this.mediaName = mediaName;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Users withCreated(String created) {
        this.created = created;
        return this;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Users withLiked(Integer liked) {
        this.liked = liked;
        return this;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Users withComments(Integer comments) {
        this.comments = comments;
        return this;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Users withIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Users withIsActive(Integer isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Users withUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
