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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.IDUSER
import tn.yassin.discovery.Utils.PREF_NAME

class DialogPostOption {
    private lateinit var MySharedPref: SharedPreferences

    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun ShowOptionPopup(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()

        val optionEditPost = view.findViewById<TextView>(R.id.BtnEditPost) as? TextView
        val optionDeletePost = view.findViewById<TextView>(R.id.btnDeletePost) as? TextView
        val btnReportPost = view.findViewById<TextView>(R.id.btnReportPost) as? TextView
        val LayoutReportPost = view.findViewById<LinearLayout>(R.id.LayoutReportPost) as? LinearLayout
        val LayoutEditPost = view.findViewById<LinearLayout>(R.id.LayoutEditPost) as? LinearLayout
        val LayoutDeletePost = view.findViewById<LinearLayout>(R.id.LayoutDeletePost) as? LinearLayout
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val PostedBy = sharedPreference.getString("PostedbyDetail", null)
        MySharedPref =
            context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val idUser = MySharedPref.getString(IDUSER, null)
        /////
        println(" idUser "+idUser)
        println(" PostedBy "+PostedBy)
        /////
        if(idUser==PostedBy)
        {
            //optionEditPost?.visibility = View.GONE
            LayoutReportPost?.visibility = View.GONE
        }
        else{
            LayoutEditPost?.visibility = View.GONE
            LayoutDeletePost?.visibility = View.GONE
        }
        //Show PopUppEditPost
        optionEditPost?.setOnClickListener {
           dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.updatepostuser, null)
            val msg = DialogPostEdit()
            msg.ShowEditPost(context, view)
        }
        optionDeletePost?.setOnClickListener {
            dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.deletepost, null)
            val msg = DialogPostDelete()
            msg.ShowDeletePost(context, view)
        }
        btnReportPost?.setOnClickListener {
            dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.reportpost, null)
            val msg = DialogPostReport()
            msg.ShowReportPost(context, view)
        }

    }
}