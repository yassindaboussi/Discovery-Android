package tn.yassin.discovery
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import tn.yassin.discovery.Views.Fragement.FragmentHome
import tn.yassin.discovery.Views.Fragement.FragmentPhotouser
import tn.yassin.discovery.Views.Fragement.FragmentVideouser

class MyPagerAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa){

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentPhotouser()
            }
            else -> {
                return FragmentPhotouser()
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}