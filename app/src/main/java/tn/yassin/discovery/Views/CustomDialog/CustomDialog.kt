package tn.yassin.discovery.Views.CustomDialog

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Models.Posts
import tn.yassin.discovery.Network.FavoriteApi
import tn.yassin.discovery.Network.UserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.ViewModel.FavorisViewModel
import tn.yassin.discovery.ViewModel.UserViewModel
import java.lang.reflect.Type
import java.net.HttpURLConnection.HTTP_OK

class CustomDialog() : LifecycleService()  {
    private lateinit var MySharedPref: SharedPreferences
    //
    val sound = PlayMusic()
    fun ShowCustomDialog(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        sound.SoundNotification(context!!)
    }
}