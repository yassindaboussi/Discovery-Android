package tn.yassin.discovery.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostsAdmin {

    @SerializedName("_id")
    @Expose
    internal var id: String? = null

    @SerializedName("nom")
    @Expose
    internal var nom: String? = null

    @SerializedName("description")
    @Expose
    internal var description: String? = null

    @SerializedName("photo")
    @Expose
    internal var photo: String? = null

    @SerializedName("lieux")
    @Expose
    internal var lieux: String? = null

    @SerializedName("categorie")
    @Expose
    internal var categorie: String? = null

    @SerializedName("rate")
    @Expose
    internal var rate: String? = null

/*    @SerializedName("nbOfrate")
    @Expose
    internal var nbOfrate: String? = null*/

    @SerializedName("__v")
    @Expose
    internal var v: Int? = null
    ///////////////////////////////////////////////////////:
    fun getId(): String? {
        return id
    }
    fun setId(id: String?) {
        this.id = id
    }
    fun getNom(): String? {
        return nom
    }
    fun setNom(nom: String?) {
        this.nom = nom
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
    fun getCategorie(): String? {
        return categorie
    }
    fun setCategorie(Categorie: String?) {
        this.categorie = Categorie
    }
    fun getLieux(): String? {
        return lieux
    }
    fun setLieux(lieux: String?) {
        this.lieux = lieux
    }
    fun getRate(): String? {
        return rate
    }
    fun setRate(rate: String?) {
        this.rate = rate
    }
/*    fun getNbOfRate(): String? {
        return nbOfrate
    }
    fun setNbOfRate(nbOfrate: String?) {
        this.nbOfrate = nbOfrate
    }*/

    fun getV(): Int? {
        return v
    }

    fun setV(v: Int?) {
        this.v = v
    }
}