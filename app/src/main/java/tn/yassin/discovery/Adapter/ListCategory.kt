package tn.yassin.discovery.Adapter

import tn.yassin.discovery.R
import tn.yassin.discovery.data.Category

class ListCategory {

    var ListCategory: MutableList<Category> = ArrayList()


    fun initListCategory()
    {
        ListCategory.add(
            Category(
                NomCategory = "Plage",
                IconCategory = R.drawable.icon_beach
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Culture",
                IconCategory = R.drawable.icon_history
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Desert",
                IconCategory = R.drawable.icon_sahara
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Arts",
                IconCategory = R.drawable.icon_arts
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Food",
                IconCategory = R.drawable.icon_restau
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Activite",
                IconCategory = R.drawable.icon_sport
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Nature",
                IconCategory = R.drawable.icon_nature
            )
        )
    }
}