package tn.yassin.oneblood.DataMapList

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.yassin.discovery.DataMapList.PostUserViewHolder
//import tn.yassin.discovery.Models.Posts
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.CustomDialog.DialogPostOption
import tn.yassin.discovery.Views.CustomDialog.DialogProfilPost


class PostUserAdapter(var context: Context) : RecyclerView.Adapter<PostUserViewHolder>(),
    Filterable {

    var dataList = mutableListOf<PostsPlaces>()
    var filteredPostsList= ArrayList<PostsPlaces>()
    //val ReadyFunction = ReadyFunction()


    internal fun setDataList(userArrayList: ArrayList<PostsPlaces>) {
        this.dataList = userArrayList
        this.filteredPostsList = userArrayList
        notifyDataSetChanged()
    }
    internal fun addDataList(data: PostsPlaces) {
        this.dataList.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostUserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostUserViewHolder(view)

    }


    override fun getItemCount() = filteredPostsList.size

     override fun onBindViewHolder(holder: PostUserViewHolder, position: Int) {
         var data = filteredPostsList[position]
         val NomUser = "nameuser"
        val datepost = filteredPostsList[position].datepost
         val description = filteredPostsList[position].description
         val ImageLink = ("https://serverdiscovery.onrender.com/imgPosts/"+filteredPostsList[position].photo)
         //val AvatarUser = "Avatar".toInt()
         Glide
             .with(context)
             .load(ImageLink)
             .fitCenter()
             .into(holder.itempostimg);

/*         //holder.UserPostAvatar.setImageResource(AvatarUser)
         holder.UserPostName.text = NomUser
         holder.UserPostDate.text = datepost
         holder.DescriptionPost.text = description

        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

        //animation Items RecyclerView
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        //holder.itemView.setVisibility(View.VISIBLE)
        holder.itemView.startAnimation(animation)

         holder.PostuserMenu.setOnClickListener {
             PopUp(holder.PostuserMenu)
         }*/

         holder.itemView.setOnClickListener {
             val preferences = PreferenceManager.getDefaultSharedPreferences(context)
             val editor = preferences.edit()
             editor.putString("IdPostDetail", data.getId())
             editor.putString("ImagePostDetail", ImageLink)
             editor.putString("DatePostDetail", data.getDatepost())
             editor.putString("PostedbyDetail", data.getPostedby())
             editor.putString("DescriptionPostDetail", data.getDecription())
             editor.putString("NbLikePostDetail", data.getNbLike())
             //editor.putString("NomUser", data.getNomUser())
             //editor.putString("AvatarUser", data.getAvatarUser())
             editor.putString("FromWherePost", "FromProfil")
             editor.apply()  //Save Data
             //
             PopUpFull(holder.itemView)
         }

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


    fun PopUpFullDialog(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.dialogprofilpost, null)
        val msg = DialogProfilPost()
        msg.ShowFullPost(context, view)
    }

    fun PopUpFull(view: View) {
        PopUpFullDialog(view.context)
    }
    override fun getFilter(): Filter? {
        return object : Filter() {
            protected override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val searchString = charSequence.toString()
                if (searchString.isEmpty()) {
                    filteredPostsList = dataList as ArrayList<PostsPlaces>
                } else {
                    val tempFilteredList: ArrayList<PostsPlaces> = ArrayList()
                    for (Data in dataList) {
                        //*** search Options
                        if (searchString != "wadasxsqsqfjhf") {
                            if (Data.datepost!!.toLowerCase()
                                    .contains(searchString) || Data.categorie!!.toLowerCase()
                                    .contains(searchString)
                            ) {
                                tempFilteredList.add(Data)
                            }
                        }
                        if (searchString == "wadasxsqsqfjhf") {
                            // Search filtre akher
                        }
                    }
                        filteredPostsList = tempFilteredList
                    }
                    ////////////
                    ////////////
                    val filterResults = FilterResults()
                    filterResults.values = filteredPostsList
                    return filterResults
                }
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredPostsList = filterResults.values as ArrayList<PostsPlaces>
                notifyDataSetChanged()

            }
        }
    }


}






