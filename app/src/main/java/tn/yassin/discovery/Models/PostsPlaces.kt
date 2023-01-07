package tn.yassin.discovery.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostsPlaces {

    @SerializedName("_id")
    @Expose
    internal var id: String? = null

    @SerializedName("datepost")
    @Expose
    internal var datepost: String? = null

    @SerializedName("description")
    @Expose
    internal var description: String? = null

    @SerializedName("photo")
    @Expose
    internal var photo: String? = null

    @SerializedName("video")
    @Expose
    internal var video: String? = null

    @SerializedName("categorie")
    @Expose
    internal var categorie: String? = null

    @SerializedName("postedby")
    @Expose
    internal var postedby: String? = null

    @SerializedName("username")
    @Expose
    internal var username: String? = null

    @SerializedName("avatar")
    @Expose
    internal var avatar: String? = null

    @SerializedName("__v")
    @Expose
    internal var v: Int? = null

    @SerializedName("nblike")
    @Expose
    internal var nblike: String? = null

    @SerializedName("reported")
    @Expose
    internal var reported: String? = null
    ///////////////////////////////////////////////////////:
    fun getDatepost(): String? {
        return datepost
    }
    fun setDatepost(datepost: String?) {
        this.datepost = datepost
    }
    fun getId(): String? {
        return id
    }
    fun setId(id: String?) {
        this.id = id
    }
    fun getDecription(): String? {
        return description
    }
    fun setDecription(Decription: String?) {
        this.description = Decription
    }
    fun getPhoto(): String? {
        return photo
    }
    fun setPhoto(Photo: String?) {
        this.photo = Photo
    }
    fun getVideo(): String? {
        return video
    }
    fun setVideo(Video: String?) {
        this.video = Video
    }
    fun getPostedby(): String? {
        return postedby
    }
    fun setPostedby(postedby: String?) {
        this.postedby = postedby
    }
    fun getV(): Int? {
        return v
    }

    fun setV(v: Int?) {
        this.v = v
    }

  fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }


    fun getAvatar(): String? {
        return avatar
    }

    fun setAvatar(avatar: String?) {
        this.avatar = avatar
    }

    fun getNbLike(): String? {
        return nblike
    }

    fun setNbLike(nblike: String?) {
        this.nblike = nblike
    }

    fun getReport(): String? {
        return reported
    }

    fun setReport(reported: String?) {
        this.reported = reported
    }
}