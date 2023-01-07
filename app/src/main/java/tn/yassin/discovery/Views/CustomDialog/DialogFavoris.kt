package tn.yassin.discovery.Views.CustomDialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleService
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Network.FavoriteApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomToast
import tn.yassin.discovery.Utils.IDUSER
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.discovery.Utils.PlayMusic

class DialogFavoris : LifecycleService()   {
    private lateinit var MySharedPref: SharedPreferences

@OptIn(DelicateCoroutinesApi::class)
fun ShowDetailsPost(context: Context?, view: View) {
    val dialog = Dialog(context!!)
    dialog.setContentView(view)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
    dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
    dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
    dialog.show()
    val sound = PlayMusic()
    sound.SoundNotification(context!!)

    /////
    val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    val NomPlace = sharedPreference.getString("NomPlace", null)
    val LieuxPlace = sharedPreference.getString("LieuxPlace", null)
    val ImagePlace = sharedPreference.getString("ImagePlace", null)
    val RatingPlace = sharedPreference.getString("RatingPlace", null)
    val DescriptionPlace = sharedPreference.getString("DescriptionPlace", null)
    /////
    val Nomtxt = view.findViewById<TextView>(R.id.NomDetails) as? TextView
    val Lieuxtxt = view.findViewById<TextView>(R.id.LieuxDetails) as? TextView
    val Descriptiontxt = view.findViewById<TextView>(R.id.DescriptionDetails) as? TextView
    val imgviewDetail = view.findViewById<ImageView>(R.id.imgviewDetail) as? ImageView
    val ratingBarDetails = view.findViewById<RatingBar>(R.id.ratingBarDetails) as? RatingBar
    /////
    Nomtxt?.text = NomPlace
    Lieuxtxt?.text = LieuxPlace
    Descriptiontxt?.text = DescriptionPlace
    ratingBarDetails!!.rating = RatingPlace!!.toFloat()
    ///
    //
    val ImagePlacesss = ("https://serverdiscovery.onrender.com/imgPosts/" + ImagePlace)
    //println("Imaaage Custom DIalog " + ImagePlace)
    Glide
        .with(context)
        .load(ImagePlacesss)
        .fitCenter()
        .into(imgviewDetail!!);
    /////
    val DetailsMap = view.findViewById<TextView>(R.id.DetailsMap) as? TextView
    val DetailsShare = view.findViewById<TextView>(R.id.DetailsShare) as? TextView
    val CloseDetails = view.findViewById<ImageView>(R.id.CloseDetails) as? ImageView
    val FavoriteHert = view.findViewById<ImageView>(R.id.AddToFavorite) as? ImageView
    ///
    CloseDetails?.setOnClickListener {
        dialog.dismiss()
    }
    DetailsMap?.setOnClickListener {
        //Check App is Not Disabled
      /*  val ai: ApplicationInfo =
            context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0)
        val appStatus = ai.enabled
        if (isAppInstalled(
                "com.google.android.apps.maps",
                context
            ) && appStatus
        ) { // Check if Google Map existe and not disabled
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$NomPlace")
            );
            context.startActivity(browserIntent)
        } else { //Open Map in Browser

       */
         /*   val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/$NomPlace/@36.8049507,10.1529202,16z/data=!4m9!1m2!2m1!1sbanque+du+sang!3m5!1s0x12fd339e90c9f481:0xd9b67502b8aeb544!8m2!3d36.8049507!4d10.1572976!15sCg5iYW5xdWUgZHUgc2FuZ5IBCmJsb29kX2Jhbms")
            );
            context.startActivity(browserIntent)
       // }*/
    }
    ///////////////////////////////////////////

    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val VerifFavorite = settings.getBoolean("VerifFavorite", false)
    println("Favorite From SharedPref ============>>>>>>>>>> "+VerifFavorite)

    // println("Favorite From SharedPref ============>>>>>>>>>> "+VerifFavorite)

    if (VerifFavorite == true) {
        FavoriteHert!!.setImageResource(R.drawable.ic_favorite)
    } else {
        FavoriteHert!!.setImageResource(R.drawable.ic_favorite_border)
    }


    ///////////////////////////////////////////
    FavoriteHert.setOnClickListener {
        if(VerifFavorite == true){
            DeleteFavoriteFromDB(context)
            FavoriteHert!!.setImageResource(R.drawable.ic_favorite_border)
        }else{
            AddFavoriteToDB(context)
            FavoriteHert!!.setImageResource(R.drawable.ic_favorite)
        }
    }
    DetailsShare?.setOnClickListener{
        dialog.cancel()
        dialog.dismiss()
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.optionsharepopup, null)
        val msg = DialogShare()
        msg.ShareToSocial(context, view)

    }

}

fun isAppInstalled(packageName: String, context: Context): Boolean {
    return try {
        val packageManager = context.packageManager
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

///////////////////////////////////////////////////////////////////////////////////
fun AddFavoriteToDB(context: Context) {
    MySharedPref =
        context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
    val idUser = MySharedPref.getString(IDUSER, null)
    //
    val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    val idPost = sharedPreference.getString("IdPost", null)
    //
    val retrofi: Retrofit = retrofit.getInstance()
    val service: FavoriteApi = retrofi.create(FavoriteApi::class.java)

    val map: HashMap<String, String> = HashMap()
    map["idPost"] = idPost.toString()
    map["idUser"] = idUser.toString()
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    service.AddFavorite(map).enqueue(object : Callback<JsonObject> {
        override fun onResponse(
            call: Call<JsonObject>,
            response: Response<JsonObject>
        ) {
            if (response.code() == 200) {
                CustomToast(context, "Favorite Added!", "GREEN").show()
                //
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

///////////////////////////////////////////////////////////////////////////////////
fun DeleteFavoriteFromDB(context: Context) {
    MySharedPref =
        context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
    val idUser = MySharedPref.getString(IDUSER, null)
    //
    val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    val idPost = sharedPreference.getString("IdPost", null)
    //
    val retrofi: Retrofit = retrofit.getInstance()
    val service: FavoriteApi = retrofi.create(FavoriteApi::class.java)

    val map: HashMap<String, String> = HashMap()
    map["idPost"] = idPost.toString()
    map["idUser"] = idUser.toString()
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    service.FavoriteDelete(map).enqueue(object : Callback<JsonObject> {
        override fun onResponse(
            call: Call<JsonObject>,
            response: Response<JsonObject>
        ) {
            if (response.code() == 200) {
                CustomToast(context, "Favorite Deleted!", "GREEN").show()
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

/////////////////// Nest7a9elhom nhayer akher

/*    //////////////////
    val factoryyy = ViewModelProvider.NewInstanceFactory()
    val viewModel: FavorisViewModel = factoryyy.create(FavorisViewModel::class.java)
    val androidFactory =
        ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
    val viewModell: FavorisViewModel = androidFactory.create(FavorisViewModel::class.java)
    //////////////////
    viewModell.VerifFavorisExistOrNot(context)
*/

}