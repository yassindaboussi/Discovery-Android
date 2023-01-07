package tn.yassin.discovery.Views.CustomDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

class DialogPostEdit {
    val ReadyFunction = ReadyFunction()
    lateinit var txtEdit: TextInputEditText
    lateinit var SaveChangeDesc: TextView
    lateinit var AnnulerChangeDesc: TextView
    lateinit var txtcountChangeEmail:TextView


    @SuppressLint("RestrictedApi")
    fun ShowEditPost(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val Postid = sharedPreference.getString("IdPostDetail", null)
        val Postdesc = sharedPreference.getString("DescriptionPostDetail", null)
        /////
        SaveChangeDesc = (view.findViewById<TextView>(R.id.SaveChangeDesc) as? TextView)!!
        AnnulerChangeDesc = (view.findViewById<TextView>(R.id.AnnulerChangeDesc) as? TextView)!!
        val txtDescription = view.findViewById<TextView>(R.id.txtDescChange) as? TextView
        txtEdit= view.findViewById<TextInputEditText>(R.id.txtDescChange)
        txtcountChangeEmail= view.findViewById<TextView>(R.id.txtcountChangeEmail)
        /////
        txtDescription!!.text = Postdesc
        SaveChangeDesc.setOnClickListener {
            println("Save Clicked!")
            if (txtEdit.text.toString() == Postdesc) {
                txtcountChangeEmail.setText("The Same Description!!")
            } else {
                EditDescription(Postid.toString(),txtEdit.text.toString(),context,dialog)
            }
        }
        AnnulerChangeDesc.setOnClickListener {
            dialog.cancel()
        }

        //////////////////////
        gettextwathcer(view)
    }

    private fun gettextwathcer(view: View) {
        txtEdit.addTextChangedListener(txtWatcher)
    }

    private val txtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (txtEdit.text?.length == 0) {
                SaveChangeDesc.setEnabled(false);
                SaveChangeDesc.setTextColor(Color.parseColor("#CECFD1"));
            } else {
                SaveChangeDesc.setEnabled(true);
                SaveChangeDesc.setTextColor(Color.parseColor("#FF0000"))
            }
            txtcountChangeEmail.setText(txtEdit.text?.length.toString() + "/250")
        }

        override fun afterTextChanged(s: Editable) {
        }
    }

    @SuppressLint("RestrictedApi")
    fun EditDescription(Postid:String, desc:String, context:Context, dialog: Dialog)
    {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: PostUserApi = retrofi.create(PostUserApi::class.java)
        val Posts = PostsPlaces()
        Posts.setId(Postid)
        Posts.setDecription(desc)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.UpdatePost(Posts)
                withContext(Dispatchers.Main) {
                    if (response!!.isSuccessful) {
                        CustomToast(context, "Updated Successfully!","GREEN").show()
                        dialog.dismiss()
                        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
                        val FromWherePost = sharedPreference.getString("FromWherePost", null)
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