package tn.yassin.discovery.Utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.view.View
import android.widget.Button
import tn.yassin.discovery.R

class CustomDialogs {
    var mMediaPlayer: MediaPlayer? = null
    fun SoundNotification(context: Context?) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.soundialog)
        mMediaPlayer!!.start()
    }
    fun ShowDialogNoConnection(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        SoundNotification(context)
        val btnigotit = view.findViewById<Button>(R.id.BtnGotIt) as? Button
        btnigotit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                dialog.dismiss()
            }
        })
    }
    /////////
    fun ShowDialogInformations(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        SoundNotification(context)
        val btnigotit = view.findViewById<Button>(R.id.BtnOkay) as? Button
        btnigotit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                dialog.dismiss()
                //
                val sharedPref = context.getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("FirstTimeRun", false)
                editor.apply()
                //
            }
        })
    }
}