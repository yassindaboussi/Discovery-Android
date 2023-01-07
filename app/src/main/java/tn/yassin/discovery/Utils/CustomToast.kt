package tn.yassin.discovery.Utils
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import tn.yassin.discovery.R


class CustomToast(private val context: Context, private val message: String,Color:String) :
    Toast(context) {
    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_toast, null)
        val txtMsg = view.findViewById<TextView>(R.id.toast_text)
        val button_accent_border= view.findViewById<FrameLayout>(R.id.button_accent_border)
        if(Color=="GREEN"){button_accent_border.setBackgroundColor(android.graphics.Color.parseColor("#3EAA56"));}
        if(Color=="RED"){button_accent_border.setBackgroundColor(android.graphics.Color.parseColor("#F34336"));}
        txtMsg.setText(message)
        setView(view)
        setGravity(Gravity.BOTTOM, 0, 150)
        duration = LENGTH_SHORT

    }
}