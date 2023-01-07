package tn.yassin.discovery.Network

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import tn.yassin.discovery.Dataa.Loginresponse
import java.util.*


interface UserApi {
    //Login Méthode 1
    @POST("api/user/signin")
    fun login1(
        @Body body: JsonObject
    ): Call<JsonObject>
    // A suspending function is simply a function that can be paused and resumed at a later time. They can execute a long running operation and wait for it to complete without blocking.
    //Login Méthode 2
    @POST("api/user/signin")
    suspend fun login2(@Body User: Loginresponse): Response<Loginresponse>
    //
    @POST("api/user/singup")
    fun signup(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("api/user/SendConfirmEmail")
    suspend fun SendConfirmEmail(@Body User: Loginresponse): Response<Loginresponse>

    @POST("api/user/SendCodeForgot")
    fun SendCodeForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("api/user/VerifCodeForgot")
    fun VerifCodeForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("api/user/ChangePasswordForgot")
    fun ChangePasswordForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("api/user/EditProfil")
    fun EditProfil(@Body map : HashMap<String, String> ): Call<JsonObject>


    @Multipart
    @POST("api/user/UploadAvatarUser")
    fun postImage(
        @Part("email") email:RequestBody,
        @Part image: MultipartBody.Part,
    ):Call<Loginresponse>

}