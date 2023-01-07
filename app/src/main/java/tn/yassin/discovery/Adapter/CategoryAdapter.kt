package tn.yassin.oneblood.DataMapList

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import tn.yassin.discovery.DataMapList.CategoryViewHolder
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.Activity.CategoryDetails
import tn.yassin.discovery.Views.Activity.ForgetPassword1
import tn.yassin.discovery.data.Category


class CategoryAdapter(var context: Context, var ListList: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)

    }


    override fun getItemCount() = ListList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val NomCategory = ListList[position].NomCategory
        val IconCategory = ListList[position].IconCategory

        holder.Nom.text = NomCategory
        holder.Pic.setImageResource(IconCategory)

        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

        //animation Items RecyclerView
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        //holder.itemView.setVisibility(View.VISIBLE)
        holder.itemView.startAnimation(animation)


        holder.itemView.setOnClickListener {
            if (NomCategory == "Plage"||NomCategory == "Beach") {
                println("Plage HASE Selected !!")
            }
            if (NomCategory == "Desert"||NomCategory == "Sahara") {
                println("Desert HASE Selected !!")

            }
            if (NomCategory == "Activite"||NomCategory=="Activities") {
                println("Activite HASE Selected !!")

            }
            if (NomCategory == "Culture") {
                println("Culture HASE Selected !!")

            }
            if (NomCategory == "Nature") {
                println("Nature HASE Selected !!")

            }
            if (NomCategory == "Food") {
                println("Food HASE Selected !!")

            }
            if (NomCategory == "Arts") {
                println("Arts HASE Selected !!")

            }

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("CategorySelected", NomCategory)
            editor.apply()  //Save Data
            //
            val intent = Intent(context, CategoryDetails::class.java)
            context.startActivity(intent)
            //CategoryDetails
        }

    }


}



