package tn.yassin.discovery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import tn.yassin.discovery.Views.Fragement.*

class Navigation : AppCompatActivity() {
    lateinit var BottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation)
        //
        BottomNavigationView = findViewById(R.id.bottomNavigationView)
        //
        val toolbar: Toolbar = findViewById(R.id.toolbar54)
        //toolbar.setNavigationIcon(R.drawable.yum)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
/*        if (supportActionBar != null) {
            val drawable = resources.getDrawable(R.drawable.yum)
            val bitmap = (drawable as BitmapDrawable).bitmap
            val newdrawable: Drawable =
                BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true))
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(newdrawable)
        }*/
        toolbar.visibility = View.GONE
        ///////////////////////////////////////////////////////////////////////////////////////////
        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentHome()).commit();
        BottomNavigationView.setSelectedItemId(R.id.nav_home)
        BottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = FragmentHome()
                }
                R.id.nav_explore -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = ExploreFragmenttodo()
                }
                R.id.nav_add -> {
                    toolbar.visibility = View.GONE
                    BottomNavigationView.visibility = View.GONE
                    selectedFragment = AddFragment()
                }
                R.id.nav_favorite -> {
                    toolbar.visibility = View.VISIBLE
                    selectedFragment = FavoriteFragment()
                }
                R.id.nav_profile -> {
                    toolbar.visibility = View.GONE
                    refresh()
                    selectedFragment = ProfileFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            transaction.replace(R.id.container, selectedFragment!!)
            transaction.commit()
            true
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
    }


/*    companion object {
        fun hideBottomNav() {
            lateinit var botomview: BottomNavigationView
            botomview.visibility = View.GONE
        }
    }*/

    private fun refresh()
    {
/*        val apiInterface = ApiInterface.create()
        mSharedPref = this.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        apiInterface.retriveuser(mSharedPref.getString(ID, "").toString()).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response:
            Response<User>
            ) {
                val user = response.body()
                Log.e("user : ", user.toString())
                if(user != null)
                {
                    mSharedPref.edit().apply{
                        putString(FOLLOWERS,user.followers.size.toString())
                        putString(FOLLOWING,user.following.size.toString())
                        putString(NBPOST,user.posts.size.toString())
                    }.apply()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Accueil,"Connexion error!", Toast.LENGTH_SHORT).show()
            }
        })*/
    }

/*    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, Navigation::class.java)
        startActivity(intent)
        finish()
    }*/
}