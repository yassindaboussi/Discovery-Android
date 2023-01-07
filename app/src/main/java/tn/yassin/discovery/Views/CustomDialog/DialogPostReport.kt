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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText
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

class DialogPostReport {
    val ReadyFunction = ReadyFunction()

    @SuppressLint("RestrictedApi")
    fun ShowReportPost(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val Postid = sharedPreference.getString("IdPostDetail", null)
        val btnSendReport = view.findViewById<Button>(R.id.btnSendReport) as? Button
        val btnCancelReport = view.findViewById<Button>(R.id.btnCancelReport) as? Button
        val checkBoxReport = view.findViewById<CheckBox>(R.id.checkBoxReport) as? CheckBox
        btnSendReport!!.setOnClickListener {
            println("Save Clicked!")
            if (checkBoxReport!!.isChecked) {
                ReportThisPost(Postid.toString(),context,dialog)
            } else {
                val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
                checkBoxReport.startAnimation(shake)
            }
        }
        btnCancelReport!!.setOnClickListener {
            dialog.cancel()
        }
    }



    @SuppressLint("RestrictedApi")
    fun ReportThisPost(Postid:String,context:Context, dialog: Dialog)
    {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: PostUserApi = retrofi.create(PostUserApi::class.java)
        val Posts = PostsPlaces()
        Posts.setId(Postid)
        Posts.setReport("YES")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.ReportPost(Posts)
                withContext(Dispatchers.Main) {
                    if (response!!.isSuccessful) {
                        CustomToast(context, "Reported Successfully!","GREEN").show()
                        dialog.dismiss()
                        //ReadyFunction.changeFragmentnull(FragmentExplore(), context)
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