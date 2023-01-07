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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Network.UserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.Views.Fragement.EditProfil


class DialogChangeName {


    /////////////////////////////////////////////////////////////
    var ReadyFunction = ReadyFunction()
    lateinit var txtEditName: TextInputEditText
    lateinit var LayoutEditName: TextInputLayout
    lateinit var btnCancel: TextView
    lateinit var btnSave: TextView
    lateinit var txtcount: TextView
    lateinit var viewwww: View
    private lateinit var MySharedPref: SharedPreferences


    fun ShowEditName(context: Context?, view: View) {
        viewwww = view
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////////////////////
        btnCancel = view.findViewById(R.id.AnnulerChangeName)
        btnSave = view.findViewById(R.id.SaveChangeName)
        txtEditName = view.findViewById(R.id.txtNameChange)
        LayoutEditName = view.findViewById(R.id.txtChangeNameLayout)
        txtcount = view.findViewById(R.id.txtcountChangeName)
        /////////////////////
        MySharedPref =
            context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val TokenUser = MySharedPref.getString(TOKENUSER, null)
        val IDUser = MySharedPref.getString(IDUSER, null)
        val NameUser = MySharedPref.getString(NAMEUSER, null)
        /////////////////////
        btnCancel.setOnClickListener {
            println("Cancel Clicked!")
            dialog.cancel();
        }
       btnSave.setOnClickListener {
            println("Save Clicked!")
            if (txtEditName.text.toString() == NameUser) {
                txtcount.setText("The Same Name!!")
            } else {
                EditProfil(TokenUser.toString(),IDUser.toString(),txtEditName.text.toString(),"","","",context,dialog)
            }

        }
        //////////////////////
        gettextwathcer(view)
    }

    private fun gettextwathcer(view: View) {
        txtEditName.addTextChangedListener(NametxtWatcher)
    }

    private val NametxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (txtEditName.text?.length == 0) {
                btnSave.setEnabled(false);
                btnSave.setTextColor(Color.parseColor("#CECFD1"));
            } else {
                btnSave.setEnabled(true);
                btnSave.setTextColor(Color.parseColor("#FF0000"))
            }
            txtcount.setText(txtEditName.text?.length.toString() + "/12")
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
        map["username"] = Nom
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
                        putString(NAMEUSER, Nom)
                    }.apply()
                    CustomToast(context, "Changed Done!", "GREEN").show()
                    dialog.cancel();
                    //
                    ReadyFunction.changeFragment(EditProfil(),context,"")
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