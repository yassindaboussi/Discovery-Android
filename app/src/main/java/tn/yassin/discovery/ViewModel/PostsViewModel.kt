package tn.yassin.discovery.ViewModel

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Dataa.Loginresponse
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.Network.PostUserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.Utils.ReadyFunction

class PostsViewModel : ViewModel() {
    val retrofi: Retrofit = retrofit.getInstance()
    val service: PostUserApi = retrofi.create(PostUserApi::class.java)
    //
    val ReadyFunction = ReadyFunction()
    //
    var UserLiveData: MutableLiveData<Loginresponse> = MutableLiveData()
    val _UserLiveData : LiveData<Loginresponse> = UserLiveData
    //
    var PostsLiveData: MutableLiveData<List<PostsPlaces>> = MutableLiveData()
    val _PostsLiveData : LiveData<List<PostsPlaces>> = PostsLiveData



    fun UploadPostUser(description: RequestBody,postedby: RequestBody, image: MultipartBody.Part, context: Context,username: RequestBody,avatar: RequestBody){
        val progressdialog = ProgressDialog(context)
        progressdialog.setMessage("Please Wait....")
        progressdialog.show();
        val addUser=service.UploadPostUser(description,image,postedby,username,avatar)
        addUser.enqueue(object : Callback<Loginresponse> {
            override fun onResponse(call: Call<Loginresponse>, response: Response<Loginresponse>) {
                if (response.isSuccessful){
                    UserLiveData.postValue(response.body())
                    progressdialog.dismiss();
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())
                    UserLiveData.postValue(response.body())
                    progressdialog.dismiss();
                }
            }
            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<Loginresponse>, t: Throwable) {
                UserLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
                progressdialog.dismiss();
            }
        })
    }



    var experiencesLiveData: MutableLiveData<List<PostsPlaces>?> = MutableLiveData()
    val _experiencesLiveData : LiveData<List<PostsPlaces>?> = experiencesLiveData

    fun ShowMyPosts(){
        val addUser= service.ShowAllPost()
        addUser.enqueue(object : Callback<List<PostsPlaces>> {
            override fun onResponse(call: Call<List<PostsPlaces>>, response: Response<List<PostsPlaces>>) {
                if (response.isSuccessful){
                    experiencesLiveData.setValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    experiencesLiveData.setValue(response.body())
                }

            }
            override fun onFailure(call: Call<List<PostsPlaces>>, t: Throwable) {
                experiencesLiveData.setValue(null)
                Log.i("failure",  t.message.toString())
            }
        })
    }

    }