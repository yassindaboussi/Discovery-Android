package tn.yassin.oneblood.DataMapList

//import tn.yassin.discovery.Models.Posts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.*
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.yassin.discovery.DataMapList.AdminReportViewHolder
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.CustomDialog.DialogAdminPostReported
import tn.yassin.discovery.Views.CustomDialog.DialogFullimage
import java.util.concurrent.Executors


class AdminReportAdapter(var context: Context) : RecyclerView.Adapter<AdminReportViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminReportViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reported, parent, false)
        return AdminReportViewHolder(view)
    }

    override fun getItemCount() = filteredPostsList.size

    override fun onBindViewHolder(holder: AdminReportViewHolder, position: Int) {
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
       // holder.txtNbJaimeExp.text = nblike

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
        //++++holder.btnDownloadPost.visibility= View.GONE
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


    fun PopUpDialog(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.adminreportoption, null)
        val msg = DialogAdminPostReported()
        msg.ShowOptionReported(context, view)
    }

    fun PopUp(view: View) {
        PopUpDialog(view.context)
    }


}











