package tn.yassin.discovery.Views.Fragement

//import tn.yassin.oneblood.DataMapList.PostUserAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.Network.PostUserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.*
import tn.yassin.oneblood.DataMapList.PostUserAdapter


class FragmentPhotouser : Fragment() {
    val ReadyFunction = ReadyFunction()
    private lateinit var MySharedPref: SharedPreferences
    lateinit var RecyclerPostUser: RecyclerView
    val retrofi: Retrofit = retrofit.getInstance()
    val service: PostUserApi = retrofi.create(PostUserApi::class.java)
    var PostModels: java.util.ArrayList<PostsPlaces> = java.util.ArrayList<PostsPlaces>()
    lateinit var PostUserAdapter: PostUserAdapter
    lateinit var LayoutEmptyPhoto: RelativeLayout
    private lateinit var SwipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userphoto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RecyclerPostUser = requireView().findViewById(R.id.RecyclerPhotoUser)
        LayoutEmptyPhoto = requireView().findViewById(R.id.LayoutEmptyPhoto)
        //
        MySharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        ////
        RecyclerPostUser.setLayoutManager(StaggeredGridLayoutManager(3, 1))
        PostUserAdapter = PostUserAdapter(requireContext())
        RecyclerPostUser.adapter = PostUserAdapter
        ShowMyPosts()
        //
/*        SwipeRefresh=view.findViewById(R.id.SwipeRefreshProfile)
        //
        SwipeRefresh.setOnRefreshListener {
            ShowMyPosts()
            SwipeRefresh.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }*/
    }

    
    fun ShowMyPosts() {
        val MyID = MySharedPref.getString(IDUSER, null)
        println("GetAllMyPost +  My id IS ====>>>>>>> " + MyID)
        val map: HashMap<String, String> = HashMap()
        map["postedby"] = MyID.toString()
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.GetAllMyPost(map).enqueue(object : Callback<List<PostsPlaces>> {
            override fun onResponse(
                call: Call<List<PostsPlaces>>,
                response: Response<List<PostsPlaces>>
            ) {
                var DataBody = response.body()?.toString()
                println("Boddddyyyy " + DataBody)
                println("Boddddyyyy " + response.body())

                PostModels = ArrayList(response.body()!!)
                PostUserAdapter.setDataList(PostModels)
                LayoutEmptyPhoto.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<PostsPlaces>>, t: Throwable) {
                // Log.e("RETROFIT_ERROR", Response.code().toString())
                println("Message :" + t.stackTrace)
                LayoutEmptyPhoto.visibility = View.VISIBLE
            }

        })
    }

/*    fun Recyler()
    {
        RecyclerPostUser.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }*/


}