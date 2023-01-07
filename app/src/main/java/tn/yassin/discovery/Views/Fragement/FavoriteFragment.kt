package tn.yassin.discovery.Views.Fragement

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Network.AdminApi
import tn.yassin.discovery.Network.FavoriteApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.IDUSER
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.oneblood.DataMapList.RecommendedAdapter


class FavoriteFragment : Fragment() {
    lateinit var RecylerFavorite: RecyclerView
    lateinit var AdapterRecommended: RecommendedAdapter
    var PostsModels: ArrayList<PostsAdmin> = ArrayList()
    private lateinit var MySharedPref: SharedPreferences
    lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var SwipeRefreshFavorite: SwipeRefreshLayout
    ////////////////////////////////////////////////////////////////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RecylerFavorite = view.findViewById(R.id.RecylerFavorite)
        RecylerFavorite.setLayoutManager(StaggeredGridLayoutManager(2, 1))
        AdapterRecommended = RecommendedAdapter(requireContext(),250)
        RecylerFavorite.adapter = AdapterRecommended
        //
        MySharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val idUser = MySharedPref.getString(IDUSER, null)
        //
        ShowMyFavorite(idUser.toString())
        //
        mShimmerViewContainer = view.findViewById(R.id.shimmer_Favorite);
        //
        SwipeRefreshFavorite=view.findViewById(R.id.SwipeRefreshFavorite)
        //
        SwipeRefreshFavorite.setOnRefreshListener {
            ShowMyFavorite(idUser.toString())
            SwipeRefreshFavorite.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }
    }

    fun ShowMyFavorite(idUser:String) {
        val map: HashMap<String, String> = HashMap()
        map["idUser"] = idUser
        //
        val retrofi: Retrofit = retrofit.getInstance()
        val service: FavoriteApi = retrofi.create(FavoriteApi::class.java)
        val call: Call<List<PostsAdmin>> = service.FavoritefindByUser(map)
        call.enqueue(object : Callback<List<PostsAdmin>> {
            override fun onResponse(call: Call<List<PostsAdmin>>, response: Response<List<PostsAdmin>>) {
                PostsModels = ArrayList(response.body())
                //println("Boddddyyyyyyyyyy "+response.body())
                //println("Size in fun "+PostsModels.size)
                AdapterRecommended.setDataList(PostsModels)
                AdapterRecommended.notifyDataSetChanged()
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<PostsAdmin>>, t: Throwable) {
                println("Message :" + t.stackTrace)
                Log.d("***", "Opppsss" + t.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        mShimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}