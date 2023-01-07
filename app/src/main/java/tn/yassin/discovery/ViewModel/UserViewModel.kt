package tn.yassin.discovery.ViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Dataa.Loginresponse
import tn.yassin.discovery.Network.SessionManager
import tn.yassin.discovery.Network.UserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.Views.Activity.ForgetPassword2
import tn.yassin.discovery.Views.Activity.ForgetPassword3
import tn.yassin.discovery.Views.Activity.Login
import tn.yassin.discovery.Views.CustomDialog.CustomDialog


class UserViewModel : ViewModel() {
    val retrofi: Retrofit = retrofit.getInstance()
    val service: UserApi = retrofi.create(UserApi::class.java)
    //
    val ReadyFunction = ReadyFunction()
    //
    var errorMessage = MutableLiveData<String>()
    var UserLiveData: MutableLiveData<Loginresponse> = MutableLiveData()
    val _UserLiveData : LiveData<Loginresponse> = UserLiveData
    //
    private val statusMessage = MutableLiveData<String>()
    private lateinit var MySharedPref: SharedPreferences
    private lateinit var sessionManager: SessionManager
    //

    // Login Méthode 1
    fun login1(loginemail: TextInputEditText, loginpassw:TextInputEditText, context : Context, view : View, Activity : Activity,cbRememberMe: CheckBox) {
        ReadyFunction.showView(view)
        //Block Touch To prevent user click to many on btn login (Too much Request of Login)
        //ReadyFunction.BlockTouch(Activity)
        sessionManager = SessionManager(context)
        //
        val paramObject1 = JSONObject()
        paramObject1.put("email", loginemail.text.toString().trim())
        paramObject1.put("password", loginpassw.text.toString().trim())
        val jsonParser = JsonParser()
        var gsonObject1 = jsonParser.parse(paramObject1.toString()) as JsonObject
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.login1(gsonObject1).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.code() == 423){
                    ReadyFunction().hideView(view) //Hide ProgressBar
                    val factory = LayoutInflater.from(context)
                    val view: View = factory.inflate(R.layout.dialogverifyemail, null)
                    val msg = CustomDialog()
                    msg.ShowCustomDialog(context, view)
                }
                if (response.code() == 200) {
                    var DataBody = response.body()?.toString()
                    if (DataBody != null && !DataBody.isEmpty()) {
                        var json = JsonParser()
                        var UserData = json.parse(DataBody).asJsonObject
                        //
                        println("DataBody "+DataBody)
                        println("UserData "+UserData)

                        val id =UserData.asJsonObject.get("id").asString
                            val userName =UserData.asJsonObject.get("name").asString
                            val userEmail = UserData.asJsonObject.get("email").asString
                            val userBio = UserData.asJsonObject.get("bio").asString
                            val userAvatar = UserData.asJsonObject.get("avatar").asString
                            val token = UserData.asJsonObject.get("token").asString
                            val role = UserData.asJsonObject.get("role").asString
                        MySharedPref = context.getSharedPreferences(
                            PREF_NAME,
                            AppCompatActivity.MODE_PRIVATE
                        );
                        MySharedPref.edit().apply{
                            putString(IDUSER,id)
                            putString(NAMEUSER, userName)
                            putString(EMAILUSER, userEmail)
                            putString(BIOUSER, userBio)
                            putString(AVATARUSER, userAvatar)
                            putString(TOKENUSER,token)
                            putString(ROLEUSER,role)
                            if(cbRememberMe.isChecked) {
                                putString(RememberEmail, loginemail.text.toString().trim())
                                putString(RememberPassword, loginpassw.text.toString().trim())
                            }
                        }.apply()
                       // }
                        CustomToast(context, "Login Successful!","GREEN").show()
                        sessionManager.saveAuthToken(UserData.asJsonObject.get("token").asString)
                        ReadyFunction.GoToActivityNavigation(context,Activity) //GoTo Page Navigation
                        statusMessage.value = "Sign in successful"
                        //Enable touch Again
                        //ReadyFunction.ActiveTouch(Activity)
                        //
                    }
               }
                if(response.code() == 422)
                {
                   ReadyFunction().hideView(view) //Hide ProgressBar
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Email or password is incorrect!","RED").show()
                    statusMessage.value = "please enter all fields"
                    //Enable touch Again
                   // ReadyFunction.ActiveTouch(Activity)
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                ReadyFunction().hideView(view) //Hide ProgressBar
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                //Enable touch Again
                //ReadyFunction.ActiveTouch(Activity)
            }
        })
    }

    fun Register(txtname: TextInputEditText,txtemail: TextInputEditText,txtpass:TextInputEditText,context:Context,Activity : Activity){
        val map: HashMap<String, String> = HashMap()
        map["username"] = txtname.text.toString()
        map["email"] = txtemail.text.toString()
        map["password"] = txtpass.text.toString()
        service.signup(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                //
                val Body = response.body().toString()
                var json = JsonParser()
                var Data = json.parse(Body).asJsonObject
                //val message =Data!!.asJsonObject.get("message").asString
                //
                if (response.code()==202){
                    CustomToast(context, "Success! you've joined Discovery","GREEN").show()
                    SendConfirmEmail(txtemail.text.toString()) // Methode Post To Send Verification Email Of User Need to Confirm
                    //
                    val factory = LayoutInflater.from(context)
                    val view: View = factory.inflate(R.layout.dialogverifyemail, null)
                    val msg = CustomDialog()
                    msg.ShowCustomDialog(context, view)
                    //
                    Handler().postDelayed({
                        ReadyFunction.GoToActivityLogin(context,Activity) //GoTo Page Login
                    }, 4500)
                    //
                }else{
                    CustomToast(context, "This Email Already Exist!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }


    fun SendConfirmEmail(EmailValue: String) {
        val User = Loginresponse()
        User.setEmail(EmailValue)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.SendConfirmEmail(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("A verification email has been sent!")
                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                    }
                }
            } catch (e: Exception)
            {
                println(e.printStackTrace())
                println("Error")
            }
        }
    }


    // Login Méthode 1
    fun SendCodeForgot(EmailForget: String,context : Context, Activity : Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailForget
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.SendCodeForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 205) {
                        MySharedPref = context.getSharedPreferences(
                            PREF_NAME,
                            AppCompatActivity.MODE_PRIVATE
                        );
                        MySharedPref.edit().apply{
                            putString(EMAILFORGET, EmailForget)
                        }.apply()
                        //
                        CustomToast(context, "The Code has been Sent!","GREEN").show()
                        val intent = Intent(context, ForgetPassword2::class.java)
                        context.startActivity(intent)
                        Activity.finish()
                }
                else if (response.code() == 202) {
                    CustomToast(context, "Sorry, This Email Doesn't exist!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }


    fun VerifCodeForgot(EmailValue: String,CodeValue: String,context : Context, Activity : Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailValue
        map["codeForget"] = CodeValue
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.VerifCodeForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {
                    MySharedPref = context.getSharedPreferences(
                        PREF_NAME,
                        AppCompatActivity.MODE_PRIVATE
                    );
                    MySharedPref.edit().apply{
                        putString(CODEFORGET, CodeValue)
                    }.apply()
                    //
                    CustomToast(context, "Reset Your Pasword Now!","GREEN").show()
                    val intent = Intent(context, ForgetPassword3::class.java)
                    context.startActivity(intent)
                    Activity.finish()
                }
                else if (response.code() == 402) {
                    CustomToast(context, "Sorry, The code is incorrect!","RED").show()
                }
                else if (response.code() == 401) {
                    CustomToast(context, "Sorry, Click Resend!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }

    fun ChangePasswordForgot(EmailValue:String,CodeValue:String,NewPassword:String,context:Context,Activity:Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailValue
        map["codeForget"] = CodeValue
        map["password"] = NewPassword
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.ChangePasswordForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {
                    CustomToast(context, "Password has been changed!","GREEN").show()
                    val intent = Intent(context, Login::class.java)
                    context.startActivity(intent)
                    Activity.finish()
                }
                else if (response.code() == 402) {
                    CustomToast(context, "Sorry, The code is incorrect!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }





    fun UploadAvatar(email:RequestBody,image: MultipartBody.Part,context: Context){
        val progressdialog = ProgressDialog(context)
        progressdialog.setMessage("Please Wait....")
        progressdialog.show();
        val addUser=service.postImage(email,image)
        addUser.enqueue(object : Callback<Loginresponse> {
            override fun onResponse(call: Call<Loginresponse>, response: Response<Loginresponse>) {
                if (response.isSuccessful){
                    UserLiveData.postValue(response.body())
                    progressdialog.dismiss();

                    println("New Avatar "+response.body()?.getmsg())
                    ///
                    var DataBody = response.body()?.toString()
                    if (DataBody != null && !DataBody.isEmpty()) {
                        val avatarFromBody = response.body()?.getmsg()
                        MySharedPref = context.getSharedPreferences(
                            PREF_NAME,
                            AppCompatActivity.MODE_PRIVATE
                        );
                        MySharedPref.edit().apply{putString(AVATARUSER, avatarFromBody)}.apply()
                    }
                    ///
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

    // Login Méthode 2 Ne9sa Go To Home
    /*
    @SuppressLint("RestrictedApi")
    fun login2(loginemail: TextView,loginpassw:TextView) {
        val User = Loginresponse()
        User.setEmail(loginemail.text.toString())
        User.setPassword(loginpassw.text.toString())
        //
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.login2(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("Token =============>>>>>>>>>  " + response.body()?.getToken())
                        println("ID =============>>>>>>>>>  " + response.body()?.getID())
                        println("Name =============>>>>>>>>>  " + response.body()?.getName())
                        statusMessage.value = "Sign in successful"
                    } else {
                       // hideProgressBar() //Hide ProgressBar
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                       // CustomToast(this@Login, "Email or password is incorrect!","RED").show()
                        statusMessage.value = "please enter all fields"
                    }
                }
            } catch (e: Exception)
            {
                println(e.printStackTrace())
                println("Error")
            }
        }
    }
*/


}