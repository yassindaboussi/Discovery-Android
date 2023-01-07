package tn.yassin.discovery.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.Activity.Login
import java.io.ByteArrayOutputStream


class ReadyFunction {

    fun isOnline(context:Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

     fun setFadeAnimation(view: View) {
        val FADE_DURATION = 1000 //FADE_DURATION in milliseconds
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

     fun hideKeyboard(view: View,context:Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun showView(view : View) {
        view.visibility = View.VISIBLE
    }
     fun hideView(view : View) {
        view.visibility = View.GONE
    }
    ///////////////////////////////////////////////////////////////////////////
    fun GoToActivityLogin(context: Context,Activity : Activity) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
        Activity.finish()
    }
    fun GoToActivityNavigation(context: Context,Activity : Activity) {
        val intent = Intent(context, Navigation::class.java)
        context.startActivity(intent)
        Activity.finish()
    }
    ///////////////////////////////////////////////////////////////////////////
    fun animationFadeIn(view: View,Duree : Long) {
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = Duree
        view.startAnimation(fadeIn)
    }
    fun animationFadeInOut(view: View,Duree : Long) {
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = Duree
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = Duree
        view.startAnimation(fadeOut)
        view.startAnimation(fadeIn)
    }
    //
/*    fun BlockTouch(Activity : Activity)
    {
        val window: Window = Activity.getWindow()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
    }
    fun ActiveTouch(Activity : Activity)
    {
        val window: Window = Activity.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }*/

    fun TextInputLayout.clearError() {
        error = null
        isErrorEnabled = false
    }

    ////////////////////////:
    fun changeFragment(newFragment: Fragment?, context: Context, Back:String) {
        val transaction: FragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment!!)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(Back)
        transaction.commit()
    }

    fun changeFragmentnull(newFragment: Fragment?, context: Context) {
        val transaction: FragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment!!)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun ScreenShot(view: View): Bitmap {
        val bitmapOne = Bitmap.createBitmap(360, 500, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmapOne)
        canvas.drawColor(Color.parseColor("#ff6366"))
        view.draw(canvas)

        //val waterMark = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon)
        //canvas.drawBitmap(waterMark, 0f, 0f, null)
        return bitmapOne
    }


    fun MergeImages(firstImage: Bitmap, secondImage: Bitmap): Bitmap? { // Image + WaterMark
        val result = Bitmap.createBitmap(firstImage.width, firstImage.height, firstImage.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(firstImage, 0f, 0f, null)
        canvas.drawBitmap(secondImage, 0f, 400f, null)
        return result
    }


    fun instagramShare(ScreenShot: Bitmap,ctx:Context) {
        val attributionLinkUrl = "https://developers.facebok.com"
        val shareIntent = Intent("com.instagram.share.ADD_TO_STORY")
        try{
            val path = MediaStore.Images.Media.insertImage(ctx?.contentResolver, ScreenShot, "", "")
            val uri = Uri.parse(path)
            shareIntent.setDataAndType(uri, "image/*")
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.putExtra("content_url", attributionLinkUrl)

            shareIntent.putExtra("top_background_color", "#33FF33");
            shareIntent.putExtra("bottom_background_color", "#FF00FF");
            ctx?.grantUriPermission("com.instagram.android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //startActivityForResult(shareIntent, 42)
            (ctx as Activity).startActivityForResult(shareIntent, 42)
        }
        catch (e: java.lang.Exception){
            CustomToast(ctx, "App Not installed", "RED").show()
        }
    }

    fun ShareIt(bitmap:Bitmap,context:Context,Package:String) {
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
            val imageUri = Uri.parse(path)
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "image/*"
            waIntent.setPackage(Package)
            waIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            waIntent.putExtra(Intent.EXTRA_TEXT, "You Need to Discovery \nTunisia ❤ \n#Discovery")
            context.startActivity(Intent.createChooser(waIntent, "Share with"))
        } catch (e: java.lang.Exception) {
            Log.e("Error on sharing", "$e ")
            CustomToast(context, "App Not installed", "RED").show()
        }
    }

    fun autoCompleteListAdapter(
        context: Context,
        list: Array<String>,
        autoCompleteTextView: AutoCompleteTextView
    ) {
        val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item, list)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }

}