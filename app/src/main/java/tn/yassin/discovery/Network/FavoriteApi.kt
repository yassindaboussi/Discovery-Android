package tn.yassin.discovery.Network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tn.yassin.discovery.Models.Posts
import tn.yassin.discovery.Models.PostsAdmin

interface FavoriteApi {
    //
    @POST("api/favorite/AddFavorite")
    fun AddFavorite(@Body map : HashMap<String, String>): Call<JsonObject>

    @POST("api/favorite/FavoritefindByUser")
    fun FavoritefindByUser(@Body map : HashMap<String, String>): Call<List<PostsAdmin>>

    @POST("api/favorite/FavoriteDelete")
    fun FavoriteDelete(@Body map : HashMap<String, String>): Call<JsonObject>

    @POST("api/favorite/VerifFavorite")
    fun VerifFavorite(@Body map : HashMap<String, String>): Call<Posts>

    @POST("api/favorite/VerifFavorite")
   suspend fun VerifFavoritesss(@Body map : HashMap<String, String>): Response<Posts>
}