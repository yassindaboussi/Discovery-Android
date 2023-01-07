package tn.yassin.discovery.Views.CustomDialog

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.preference.PreferenceManager
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.PlayMusic
import tn.yassin.discovery.Utils.ReadyFunction

class DialogShare {

    fun ShareToSocial(context: Context?, view: View) {
        val ReadyFunction= ReadyFunction()
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
        val NeedyScreenShot= sharedPreference.getString("ScreenShotAdmin", null)
        /////
        val WhatsAppShare = view.findViewById<ImageView>(R.id.WhatsAppShare) as? ImageView
        val FacebookShare = view.findViewById<ImageView>(R.id.FacebookShare) as? ImageView
        val MessengerShare = view.findViewById<ImageView>(R.id.MessengerShare) as? ImageView
        val TwitterShare = view.findViewById<ImageView>(R.id.TwitterShare) as? ImageView
        val SnapChatShare = view.findViewById<ImageView>(R.id.SnapChatShare) as? ImageView
        val TelegramShare = view.findViewById<ImageView>(R.id.TelegramShare) as? ImageView
        val EmailShare = view.findViewById<ImageView>(R.id.EmailShare) as? ImageView
        val DiscordShare = view.findViewById<ImageView>(R.id.DiscordShare) as? ImageView

        val InstagramShare = view.findViewById<ImageView>(R.id.InstagramShare) as? ImageView
        val btnCloseShare = view.findViewById<TextView>(R.id.btnCloseShare) as? TextView
        /////
        val b: ByteArray = Base64.decode(NeedyScreenShot, Base64.DEFAULT)
        val bitmapImage = BitmapFactory.decodeByteArray(b, 0, b.size)
        val waterMark = BitmapFactory.decodeResource(view.getResources(), R.drawable.watermarkk)
        val FinalImage = ReadyFunction.MergeImages(bitmapImage,waterMark)
        /////
        WhatsAppShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.whatsapp")
        }
        /////
        FacebookShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.facebook.katana")
        }
        /////
        MessengerShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.facebook.orca")
        }
        /////
        InstagramShare?.setOnClickListener {
            ReadyFunction.instagramShare(FinalImage!!,context)
        }
        /////
        TwitterShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.twitter.android")
        }
        /////
        SnapChatShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.snapchat.android")
        }
        /////
        TelegramShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"org.telegram.messenger")
        }
        /////
        EmailShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.google.android.gm")
        }
        /////
        DiscordShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.discord")
        }
        /////
        btnCloseShare?.setOnClickListener {
            dialog.dismiss()
        }
    }
}