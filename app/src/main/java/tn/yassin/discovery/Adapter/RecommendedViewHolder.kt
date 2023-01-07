package tn.yassin.discovery.DataMapList

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.yassin.discovery.R

class RecommendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val PlaceName : TextView = itemView.findViewById<TextView>(R.id.PlaceName)
    val PicRecomm : ImageView = itemView.findViewById<ImageView>(R.id.PicRecomm)
    val LieuxPlace : TextView = itemView.findViewById<TextView>(R.id.LieuxPlace)
    val ratingBar : RatingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)

    val numbComment : TextView = itemView.findViewById<TextView>(R.id.numbComment)

}