package tn.yassin.discovery.Views.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.Dataa.Loginresponse
import tn.yassin.discovery.Network.UserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomToast

class AutoLogin : AppCompatActivity() {
    //
    private lateinit var MySharedPref: SharedPreferences
    private lateinit var llProgressBar: ProgressBar
    private lateinit var NameAutoLogin : TextView
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auto_login)

        initView()
        //
        val RememberEmail = MySharedPref.getString(RememberEmail, null)
        val RememberPassword = MySharedPref.getString(RememberPassword, null)
        val nameUser = MySharedPref.getString(NAMEUSER, null)
        //
           llProgressBar.visibility = View.VISIBLE
           NameAutoLogin.text = nameUser
        //
        if ((RememberEmail != null && RememberPassword != null) && (RememberEmail.isNotEmpty() && RememberPassword.isNotEmpty())) {
            ServiceLogin(RememberEmail, RememberPassword)
        } else {
            GoToLogin(this@AutoLogin)
            llProgressBar.visibility = View.GONE
        }
    }

    fun initView()
    {
        llProgressBar= findViewById(R.id.progressBar)
        NameAutoLogin = findViewById(R.id.NameAutoLogin)
        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    @SuppressLint("RestrictedApi")
    fun ServiceLogin(email: String, password: String) {
        // sessionManager = SessionManager(this)
        // Create Retrofit
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)
        val User = Loginresponse()
        User.setEmail(email)
        User.setPassword(password)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.login2(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("Token =============>>>>>>>>>  " + response.body()?.getToken())
                        println("ID =============>>>>>>>>>  " + response.body()?.getID())
                        println("Name =============>>>>>>>>>  " + response.body()?.getName())
                        MySharedPref.edit().apply {
                            putString(IDUSER, response.body()?.getID())
                            putString(NAMEUSER, response.body()?.getName())
                            putString(EMAILUSER, response.body()?.getEmail())
                            putString(BIOUSER, response.body()?.getBio())
                            putString(AVATARUSER, response.body()?.getAvatar())
                            putString(TOKENUSER, response.body()?.getToken())
                            putString(ROLEUSER, response.body()?.getRole())
                        }.apply()

                        //Sleep lehna chnjem ProgressBar tchef l'animation t3ha
                        Handler().postDelayed({
                            CustomToast(this@AutoLogin, "Login Successful!", "GREEN").show()
                            GoToHome(this@AutoLogin) //GoTo Page Home
                        }, 2000) // 3000 ms = 3 Seconde
                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                        //
                        MySharedPref.edit().apply {
                            putString(RememberEmail, "")
                            putString(RememberPassword, "")
                        }.apply()
                        //
                        CustomToast(this@AutoLogin, "Email or password is incorrect!", "RED").show()
                        GoToLogin(this@AutoLogin)
                    }
                }
            } catch (e: Exception) {
                println(e.printStackTrace())
                println("Error")
                ContextUtils.getActivity(this@AutoLogin)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(this@AutoLogin, "Sorry, Something Goes Wrong!", "RED").show()
                    GoToLogin(this@AutoLogin)
                })
            }
        }
    }

    fun GoToHome(context: Context) {
        val intent = Intent(context, Navigation::class.java)
        context.startActivity(intent)
        finish()
    }

    fun GoToLogin(context: Context) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
        finish()
    }
}