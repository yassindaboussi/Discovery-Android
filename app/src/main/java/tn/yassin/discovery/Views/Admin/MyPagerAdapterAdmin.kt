package tn.yassin.discovery.Views.Admin
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import tn.yassin.discovery.Views.Fragement.FragmentHome
import tn.yassin.discovery.Views.Fragement.FragmentPhotouser
import tn.yassin.discovery.Views.Fragement.FragmentVideouser

class MyPagerAdapterAdmin (fa: FragmentActivity) : FragmentStateAdapter(fa){

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AdminPosts()
            }
            else -> {
                return AdminPosts()
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}