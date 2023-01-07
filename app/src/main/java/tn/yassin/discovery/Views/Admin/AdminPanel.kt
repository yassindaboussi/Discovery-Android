package tn.yassin.discovery.Views.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import tn.yassin.discovery.MyPagerAdapter
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.R

class AdminPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_panel)

        val BtnbacktoHome = findViewById<Button>(R.id.backtoHome)


        BtnbacktoHome.setOnClickListener {
            val intent = Intent(this, Navigation::class.java)
            this.startActivity(intent)
        }
        SetTabPager()
    }

    fun SetTabPager()
    {
        val tabLayoutProfile = findViewById<TabLayout>(R.id.tabLayoutAdmin)
        val viewpagerProfile = findViewById<ViewPager2>(R.id.view_pageAdmin)
        val myPagerAdapter = MyPagerAdapterAdmin(this)
        viewpagerProfile.setAdapter(myPagerAdapter)
        tabLayoutProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpagerProfile.setCurrentItem(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewpagerProfile.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayoutProfile.getTabAt(position)!!.select();
                if (position == 0) {
                    // you are on the first page
                }
                else if (position == 1) {
                    // you are on the second page
                }
                super.onPageSelected(position)
            }
        })
    }
}