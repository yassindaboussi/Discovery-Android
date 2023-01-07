package tn.yassin.oneblood.DataMapList

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.DataMapList.RecommendedViewHolder
import tn.yassin.discovery.Models.Posts
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Network.FavoriteApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.IDUSER
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.ViewModel.FavorisViewModel
import tn.yassin.discovery.Views.CustomDialog.CustomDialog
import tn.yassin.discovery.Views.CustomDialog.DialogFavoris
import java.io.ByteArrayOutputStream


class RecommendedAdapter(var context: Context,val limit: Int) :
    RecyclerView.Adapter<RecommendedViewHolder>() {

    var dataList = mutableListOf<PostsAdmin>()
    var filteredPostsList= ArrayList<PostsAdmin>()
    val ReadyFunction = ReadyFunction()


    internal fun setDataList(PostsArrayList: ArrayList<PostsAdmin>) {
        this.dataList = PostsArrayList
        this.filteredPostsList = PostsArrayList
        notifyDataSetChanged()
    }
    internal fun addDataList(data: PostsAdmin) {
        this.dataList.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recommended, parent, false)
        return RecommendedViewHolder(view)

    }


    //override fun getItemCount() = filteredPostsList.size


    ////////////////////////////////////

    override fun getItemCount(): Int {
        return if (filteredPostsList.size > limit) {
            limit
        } else {
            filteredPostsList.size
        }
    }
    ////////////////////////////////////

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        var data = filteredPostsList[position]

        val ImagePlace = ("https://serverdiscovery.onrender.com/imgPosts/"+filteredPostsList[position].photo)
       // println("Imageeeee ==>>>>> "+ImagePlace)
        val NomPlace = filteredPostsList[position].nom
        val Lieux = filteredPostsList[position].lieux
        val RatingPlace = filteredPostsList[position].rate
        //
        Glide
            .with(context)
            .load(ImagePlace)
            .fitCenter()
            .into(holder.PicRecomm);
        //holder.PicRecomm.setImageResource(ImagePlace!!)
        holder.PlaceName.text = NomPlace
        holder.LieuxPlace.text = Lieux
        holder.ratingBar.rating = RatingPlace!!.toFloat()
        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        //animation Items RecyclerView
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        //holder.itemView.setVisibility(View.VISIBLE)
        holder.itemView.startAnimation(animation)
        //
        holder.itemView.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("IdPost", data.getId())
            editor.putString("ImagePlace", data.getPhoto())
            editor.putString("NomPlace", data.getNom())
            editor.putString("LieuxPlace", data.getLieux())
            editor.putString("DescriptionPlace", data.getDecription())
            editor.putString("RatingPlace", data.getRate())
            editor.apply()  //Save Data
            //println("Ratteeeeeeeeeeeee "+data.getRate())
            ///
            PopUpDetails(holder.itemView)


            //ScreenShot
            val fileOutputStream = ByteArrayOutputStream()
            ReadyFunction.ScreenShot(holder.itemView)!!.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            val compressImage: ByteArray = fileOutputStream.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)
            //println("sEncodedImage ===>>>>>> "+sEncodedImage)
            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
            val editorr = preferencess.edit()
            editorr.putString("ScreenShotAdmin", sEncodedImage)
            editorr.apply()  //Save Data
        }

    }
    fun PopUpDetails(view: View) {
        PopUpDetailsPosts(view.context)
    }

    fun PopUpDetailsPosts(context: Context) {
/*        val factoryyy = ViewModelProvider.NewInstanceFactory()
        val viewModel: FavorisViewModel = factoryyy.create(FavorisViewModel::class.java)*/
        val androidFactory =
            ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
        val viewModell: FavorisViewModel = androidFactory.create(FavorisViewModel::class.java)
        //////////////////
        val Jooobbb = GlobalScope.launch(Dispatchers.Default) {
            viewModell.VerifFavoriteCoroutineScope(context)
            // delay the coroutine by 1sec
                delay(1000)
        }
        //////////////////
        runBlocking {
            Jooobbb.join()
            println("Blooocck")
            //update the UI
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.detailsposts, null)
            val msg = DialogFavoris()
            msg.ShowDetailsPost(context, view)
        }
        //////////////////
    }





}



