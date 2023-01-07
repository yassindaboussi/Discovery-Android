package tn.yassin.discovery.DataMapList

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.yassin.discovery.R

class PostUserExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val UserPostAvatar : ImageView = itemView.findViewById(R.id.PostUserAvatar)
    val UserPostName : TextView = itemView.findViewById(R.id.UserPostName)
    val UserPostDate : TextView = itemView.findViewById(R.id.DatePostUser)
    val UserMediaPost : ImageView = itemView.findViewById(R.id.UserMediaPost)
    val PostuserMenu : ImageView = itemView.findViewById(R.id.PostuserMenu)
    val DescriptionPost : TextView = itemView.findViewById(R.id.DescriptionPosttt)
    val btnDownloadPost : ImageView = itemView.findViewById(R.id.btnDownloadPosttt)
    val txtNbJaimeExp:TextView = itemView.findViewById(R.id.txtNbJaimeExp)

}