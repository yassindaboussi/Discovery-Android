package tn.yassin.discovery.Views.CustomDialog

import android.app.Dialog
import android.content.Context
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.ZoomImageView

class DialogFullimage {

    //
    fun ShowDialogFullImage(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.GREEN)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.CENTER;
        //dialog.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow()?.setDimAmount(0.8f);
        dialog.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show()
        //////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val ImagePlace = sharedPreference.getString("ImagePostDetail", null)
        val ImagePlacesss = ("https://serverdiscovery.onrender.com/imgPosts/" + ImagePlace)
        val fullimage = view.findViewById<ImageView>(R.id.fullimage) as? ImageView
        //fullimage.
        /////
        println("Finaaaalll "+ImagePlacesss)
        Glide
            .with(context)
            .load(ImagePlacesss)
            .error(R.drawable.avatar)
            .into(fullimage!!);
        //fullimage!!.setImageResource(R.drawable.img_beach);
    }


}