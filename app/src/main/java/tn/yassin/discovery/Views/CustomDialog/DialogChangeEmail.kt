package tn.yassin.discovery.Views.CustomDialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonObject
import tn.yassin.discovery.Network.UserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Utils.*


class DialogChangeEmail {

    /////////////////////////////////////////////////////////////
    lateinit var txtEdit: TextInputEditText
    lateinit var LayoutEdit: TextInputLayout
    lateinit var btnCancel: TextView
    lateinit var btnSave: TextView
    lateinit var txtcount: TextView
    lateinit var viewwww: View
    private lateinit var MySharedPref: SharedPreferences
    var ReadyFunction = ReadyFunction()



    fun ShowEditEmail(context: Context?, view: View) {
        viewwww = view
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////////////////////
        btnCancel = view.findViewById(R.id.AnnulerChangeEmail)
        btnSave = view.findViewById(R.id.SaveChangeEmail)
        txtEdit = view.findViewById(R.id.txtEmailChange)
        LayoutEdit = view.findViewById(R.id.txtChangeEmailLayout)
        txtcount = view.findViewById(R.id.txtcountChangeEmail)
        /////////////////////
        MySharedPref =
            context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val TokenUser = MySharedPref.getString(TOKENUSER, null)
        val IDUser = MySharedPref.getString(IDUSER, null)
        val EmailUser = MySharedPref.getString(EMAILUSER, null)
        /////////////////////
        btnCancel.setOnClickListener {
            println("Cancel Clicked!")
            dialog.cancel();
        }
        btnSave.setOnClickListener {
            println("Save Clicked!")
            if (txtEdit.text.toString() == EmailUser) {
                txtcount.setText("The Same Email!!")
            } else {
                EditProfil(TokenUser.toString(),IDUser.toString(),"",txtEdit.text.toString(),"","",context,dialog)
            }
        }
        //////////////////////
        gettextwathcer(view)
    }

    private fun gettextwathcer(view: View) {
        txtEdit.addTextChangedListener(EmailtxtWatcher)
    }

    private val EmailtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (txtEdit.text?.length == 0) {
                btnSave.setEnabled(false);
                btnSave.setTextColor(Color.parseColor("#CECFD1"));
            } else {
                btnSave.setEnabled(true);
                btnSave.setTextColor(Color.parseColor("#FF0000"))
            }
            txtcount.setText(txtEdit.text?.length.toString() + "/12")
        }

        override fun afterTextChanged(s: Editable) {
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    fun EditProfil(Token:String,ID:String,Nom:String,Email:String,Bio:String,Password:String,context:Context,dialog: Dialog)
    {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)

        val map: HashMap<String, String> = HashMap()
        map["id"] = ID
        map["email"] = Email
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.EditProfil(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == 200) {
                    MySharedPref = context.getSharedPreferences(
                        PREF_NAME,
                        AppCompatActivity.MODE_PRIVATE
                    );
                    MySharedPref.edit().apply{
                        putString(EMAILUSER, Email)
                    }.apply()
                    CustomToast(context, "Changed Done!", "GREEN").show()
                    dialog.cancel();
                    ReadyFunction.changeFragment(tn.yassin.discovery.Views.Fragement.EditProfil(),context,"")
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!", "RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!", "RED").show()
            }
        })
    }


}