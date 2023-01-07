package tn.yassin.discovery.Network

import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import tn.yassin.discovery.Dataa.Loginresponse
import tn.yassin.discovery.Models.PostsPlaces
import java.util.HashMap

interface PostUserApi {

    // SHOW ONLY MY POSTS
    @POST("api/postuser/GetAllMyPost")
    fun GetAllMyPost(@Body map : HashMap<String, String>): Call<List<PostsPlaces>>

    @GET("api/postuser/all")
    fun ShowAllPost(): Call<List<PostsPlaces>>

    @Multipart
    @POST("api/postuser/AddPostUser")
    fun UploadPostUser(
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("postedby") postedby: RequestBody,
        @Part("username") username: RequestBody,
        @Part("avatar") avatar: RequestBody,
        ):Call<Loginresponse>

    @POST("api/postuser/updatepost")
    suspend fun UpdatePost(@Body Post: PostsPlaces): Response<PostsPlaces>

    @POST("api/postuser/deletepost")
    suspend fun DeletePost(@Body Post: PostsPlaces): Response<PostsPlaces>

    @POST("api/postuser/reportpost")
    suspend fun ReportPost(@Body Post: PostsPlaces): Response<PostsPlaces>

    @POST("api/postuser/CancelReport")
    suspend fun CancelReport(@Body Post: PostsPlaces): Response<PostsPlaces>
}