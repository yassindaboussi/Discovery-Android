package tn.yassin.discovery.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")  var id: String,
    @SerializedName("name") var userName: String,
    @SerializedName("email") var userEmail: String,
    @SerializedName("bio") val userbio: String,
    @SerializedName("avatar") val userAvatar: String,
    @SerializedName("token") val token:String,
) :  Serializable


