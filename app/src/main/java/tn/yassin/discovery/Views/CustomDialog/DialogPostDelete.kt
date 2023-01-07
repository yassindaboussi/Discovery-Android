package tn.yassin.discovery.Views.CustomDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.google.android.material.internal.ContextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.Network.PostUserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomToast
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Views.Fragement.FragmentExplore
import tn.yassin.discovery.Views.Fragement.ProfileFragment

class DialogPostDelete {
    val ReadyFunction = ReadyFunction()

    @SuppressLint("RestrictedApi")
    fun ShowDeletePost(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val Postid = sharedPreference.getString("IdPostDetail", null)
        /////
        val btnYesDelete = view.findViewById<Button>(R.id.btnYesDelete) as? Button
        val btnNoDelete = view.findViewById<Button>(R.id.btnNoDelete) as? Button
        btnNoDelete?.setOnClickListener{
            dialog.dismiss()
        }
        btnYesDelete?.setOnClickListener{
            val retrofi: Retrofit = retrofit.getInstance()
            val service: PostUserApi = retrofi.create(PostUserApi::class.java)
            val Posts = PostsPlaces()
            Posts.setId(Postid)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = service.DeletePost(Posts)
                    withContext(Dispatchers.Main) {
                        if (response!!.isSuccessful) {
                            CustomToast(context, "Deleted Successfully!","GREEN").show()
                            dialog.dismiss()
                            val sharedPreferencee = PreferenceManager.getDefaultSharedPreferences(context)
                            val FromWherePost = sharedPreferencee.getString("FromWherePost", null)
                            if(FromWherePost=="FromExplore") {
                                ReadyFunction.changeFragmentnull(FragmentExplore(), context)
                            }
                            else{
                                ReadyFunction.changeFragmentnull(ProfileFragment(), context)
                            }
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                            println("Message :" + response.errorBody()?.string())
                            CustomToast(context, "Something Went Wrong!", "RED").show()
                        }
                    }
                } catch (e: Exception) {
                    println("Error")
                    println(e.printStackTrace())
                    ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                        dialog.dismiss()
                        // CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                        //ReadyFunction.changeFragmentnull(FragmentExplore(), context)
                    })
                }
            }

        }

    }
}