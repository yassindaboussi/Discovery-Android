package tn.yassin.discovery.Network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Models.PostsPlaces
import java.util.HashMap

interface AdminApi {
    //
    @POST("api/postadmin/AddPostAdmin")
    fun AddPostAdmin(@Body map : HashMap<String, String>): Call<JsonObject>
    // SHOW All  POSTS
    @POST("api/postadmin/SortbyRate")
    fun PostSortedbyRate(): Call<List<PostsAdmin>>

    @POST("api/postadmin/FindByCategory")
    fun FindByCategory(@Body map : HashMap<String, String>): Call<List<PostsAdmin>>


    @POST("api/postuser/getreportedpost")
    fun GetReportedPost(): Call<List<PostsPlaces>>
}