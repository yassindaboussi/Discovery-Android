package tn.yassin.discovery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Views.Activity.AutoLogin

class SplashScreen : AppCompatActivity() {
    /////
    private lateinit var textViewSplash: TextView
    val ReadyFunction = ReadyFunction()
    private lateinit var motionLayout: MotionLayout

    /////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, AutoLogin::class.java)
            startActivity(intent)
            finish()
        }, 1500) // 3000 ms = 3 Seconde
    }

   override fun onResume() {
        super.onResume()
       // motionLayout.startLayoutAnimation()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
           hideSystemUIAndNavigation(this)
        }
    }

    private fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

