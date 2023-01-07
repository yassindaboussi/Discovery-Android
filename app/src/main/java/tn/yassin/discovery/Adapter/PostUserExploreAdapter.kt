package tn.yassin.oneblood.DataMapList

//import tn.yassin.discovery.Models.Posts

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import tn.yassin.discovery.DataMapList.PostUserExploreViewHolder
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.CustomDialog.DialogFullimage
import tn.yassin.discovery.Views.CustomDialog.DialogPostOption
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors


class PostUserExploreAdapter(var context: Context) : RecyclerView.Adapter<PostUserExploreViewHolder>() {

    //var dataList = mutableListOf<PostsPlaces>()
    //var filteredPostsList= ArrayList<PostsPlaces>()
    //val ReadyFunction = ReadyFunction()


/*    internal fun setDataList(userArrayList: ArrayList<PostsPlaces>) {
        this.dataList = userArrayList
        this.filteredPostsList = userArrayList
        notifyDataSetChanged()
    }*/

    val filteredPostsList = mutableListOf<PostsPlaces>()

    fun submitList(newData: List<PostsPlaces>) {
        filteredPostsList.clear()
        filteredPostsList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostUserExploreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_postdetail, parent, false)
        return PostUserExploreViewHolder(view)

    }

    override fun getItemCount() = filteredPostsList.size

    override fun onBindViewHolder(holder: PostUserExploreViewHolder, position: Int) {
        var data = filteredPostsList[position]
        val NomUser = filteredPostsList[position].username
        val datepost = filteredPostsList[position].datepost
        val description = filteredPostsList[position].description
        val ImagePostLink =
            ("https://serverdiscovery.onrender.com/imgPosts/" + filteredPostsList[position].photo)
        val ImageAvatarLink =
            ("https://serverdiscovery.onrender.com/imaguser/" + filteredPostsList[position].avatar)
        //println("Image "+ImagePostLink)
        val nblike = filteredPostsList[position].nblike
        Glide
            .with(context)
            .load(ImagePostLink)
            .fitCenter()
            .into(holder.UserMediaPost);
        //////////
        Glide
            .with(context)
            .load(ImageAvatarLink)
            .fitCenter()
            .error(R.drawable.avatar)
            .into(holder.UserPostAvatar);

        //holder.UserPostAvatar.setImageResource(AvatarUser)
        holder.UserPostName.text = NomUser
        holder.UserPostDate.text = datepost
        holder.DescriptionPost.text = description
        holder.txtNbJaimeExp.text = nblike

        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

        //animation Items RecyclerView
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        //holder.itemView.setVisibility(View.VISIBLE)
        holder.itemView.startAnimation(animation)

        holder.PostuserMenu.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("IdPostDetail", data.getId())
            editor.putString("ImagePostDetail", filteredPostsList[position].photo)
            editor.putString("DatePostDetail", data.getDatepost())
            editor.putString("PostedbyDetail", data.getPostedby())
            editor.putString("DescriptionPostDetail", data.getDecription())
            editor.putString("NomUser", data.getUsername())
            editor.putString("AvatarUser", filteredPostsList[position].avatar)
            editor.putString("FromWherePost", "FromExplore")
            editor.apply()  //Save Data
            PopUp(holder.PostuserMenu)
        }


        // Declaring and initializing an Executor and a Handler
        val myExecutor = Executors.newSingleThreadExecutor()
        val myHandler = Handler(Looper.getMainLooper())
        var mImage: Bitmap?
        holder.btnDownloadPost.setOnClickListener {
/*            // onClickDownload(ImageLink)
             myExecutor.execute {
                 println("Download Link "+ImagePostLink)
                 mImage = mLoad(ImagePostLink)
                 myHandler.post {
                    // mImageView.setImageBitmap(mImage)
                     if(mImage!=null){
                         mSaveMediaToStorage(mImage)
                     }
                    // Downloadddd(holder.btnDownloadPost)
                 }
             }*/

            println("src " + ImagePostLink)
            Glide.with(context)
                .load(ImagePostLink)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        val bitmap = (resource as BitmapDrawable).bitmap
                        Toast.makeText(context, "Saving Image...", Toast.LENGTH_SHORT)
                            .show()
                        //saveImage(bitmap)
                        //saveImageee(bitmap,ImagePostLink)
                        mSaveMediaToStorage(bitmap)
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Toast.makeText(
                            context,
                            "Failed to Download Image! Please try again later.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

        }
        holder.UserMediaPost.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("ImagePostDetail", filteredPostsList[position].photo)
            editor.apply()  //Save Data
            //ImagePostLink
            println("Image Clicked !!")
            PopUppp(holder.itemView)
        }
    }

    fun PopUppp(view: View) {
        PopUpFullImage(view.context)
    }

    fun PopUpFullImage(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.dialogfullimage, null)
        val msg = DialogFullimage()
        msg.ShowDialogFullImage(context, view)
    }


    private fun saveImageee(image: Bitmap, imageURL: String) {

        if (!verifyPermissions()!!) {
            return;
        }
        val dirPath =
            Environment.getExternalStorageDirectory().absolutePath + "/" + "Discovery" + "/"

        val storageDir = File(dirPath)

        val imageFileName: String = imageURL.substring(imageURL.lastIndexOf('/') + 1)
        var successDirCreated = false
        if (!storageDir.exists()) {
            successDirCreated = storageDir.mkdir()
        }
        if (successDirCreated) {
            val imageFile = File(storageDir, imageFileName)
            val savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, "Error while saving image!", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Failed to make folder!", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyPermissions(): Boolean? {

        // This will return the current Status
        val permissionExternalMemory =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions((context as Activity)!!, STORAGE_PERMISSIONS, 1)
            return false
        }
        return true
    }

    fun write(bitmap: Bitmap) {
        val outputStream: FileOutputStream
        try {
            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    fun Downloadddd(myDrawable: ImageView) {
        myDrawable.buildDrawingCache()
        val bitmap: Bitmap = myDrawable.getDrawingCache()

        var outStream: FileOutputStream? = null
/*        val sdCard = Environment.getExternalStorageDirectory()
        val dir = File(sdCard.absolutePath + "/YourFolderName")
        dir.mkdirs()*/
        val fileName = String.format("%d.jpg", System.currentTimeMillis())
        val fileDir = context.filesDir
        val outFile = File(fileDir, fileName)
        outStream = FileOutputStream(outFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
    }

    fun PopUpDialog(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.optionspopup, null)
        val msg = DialogPostOption()
        msg.ShowOptionPopup(context, view)
    }

    fun PopUp(view: View) {
        PopUpDialog(view.context)
    }


    // Function to establish connection and load image
    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            //Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            println("Error!")
        }
        return null
    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    // Function to save image on the device.
    // Refer: https://www.geeksforgeeks.org/circular-crop-an-image-and-save-it-to-the-file-in-android/
    private fun mSaveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            try {
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                //fos!!.flush()
                //fos!!.close()
                Toast.makeText(context, "Saved to Gallery", Toast.LENGTH_SHORT).show()
                println("Saved to Gallery!")
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, "Error while saving image!", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }

    }
}











