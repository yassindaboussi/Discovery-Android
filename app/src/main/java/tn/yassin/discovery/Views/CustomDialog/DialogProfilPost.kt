package tn.yassin.discovery.Views.CustomDialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.PlayMusic

class DialogProfilPost {
    private lateinit var MySharedPref: SharedPreferences

    fun ShowFullPost(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.CENTER;
        dialog.show()
        val sound = PlayMusic()
       // sound.SoundNotification(context!!)
        /////
        val txtDatePost = view.findViewById<TextView>(R.id.DatePosttt) as? TextView
        val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionnn) as? TextView
        val imgviewFull = view.findViewById<ImageView>(R.id.fullimageProfile) as? ImageView
        val btnDownloaddd = view.findViewById<ImageView>(R.id.btnDownloaddd) as? ImageView
        val btnLikeee = view.findViewById<ImageView>(R.id.btnLikeee) as? ImageView
        val txtNbJaime = view.findViewById<TextView>(R.id.txtNbJaime) as? TextView
        val PostuserMenuuu = view.findViewById<ImageView>(R.id.PostuserMenuuu) as? ImageView
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val IdPostDetail = sharedPreference.getString("IdPostDetail", null)
        val PostedbyDetail = sharedPreference.getString("PostedbyDetail", null)
        val DatePost = sharedPreference.getString("DatePostDetail", null)
        val ImageLink = sharedPreference.getString("ImagePostDetail", null)
        val Description = sharedPreference.getString("DescriptionPostDetail", null)
        val NbLike = sharedPreference.getString("NbLikePostDetail", null)
        //
        Glide
            .with(context)
            .load(ImageLink)
            .fitCenter()
            .into(imgviewFull!!);
        /////
        txtDescription?.text = Description
        txtDatePost?.text = DatePost
        txtNbJaime?.text = NbLike
        PostuserMenuuu!!.setOnClickListener{
            dialog.cancel()
            dialog.dismiss()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.optionspopup, null)
            val msg = DialogPostOption()
            msg.ShowOptionPopup(context, view)
        }
    }
}