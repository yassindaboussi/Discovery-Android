package tn.yassin.discovery.Views.Fragement

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.ExploreFragmenttodo
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.Network.PostUserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.ViewModel.PostsViewModel
import tn.yassin.oneblood.DataMapList.PostUserExploreAdapter


class FragmentExplore : Fragment() {
    val retrofi: Retrofit = retrofit.getInstance()
    val service: PostUserApi = retrofi.create(PostUserApi::class.java)
    val ReadyFunction = ReadyFunction()
    private lateinit var MySharedPref: SharedPreferences
    //
    lateinit var RecyclerExploreUser: RecyclerView
    lateinit var PostUserAdapter: PostUserExploreAdapter
    //
    var PostModels: java.util.ArrayList<PostsPlaces> = java.util.ArrayList<PostsPlaces>()
    private lateinit var BackToExplore: ImageView
    //
    var viewModell = PostsViewModel()
    private lateinit var SwipeRefresh: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RecyclerExploreUser = requireView().findViewById(R.id.RecyclerExploreUser)
        ////
        RecyclerExploreUser.setLayoutManager(StaggeredGridLayoutManager(1, 1))
        PostUserAdapter = PostUserExploreAdapter(requireContext())
        RecyclerExploreUser.adapter = PostUserAdapter
        //
        MySharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

        ShowAllPosts()
        BackToExplore = requireView().findViewById(R.id.backToExplore)
        BackToExplore.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.container, ExploreFragmenttodo())?.commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //
                fragmentManager?.beginTransaction()?.replace(R.id.container, ExploreFragmenttodo())
                    ?.addToBackStack("ExploreTodo")?.commit()
                (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
            }
        })

/*        viewModell= ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)
        viewModell.ShowMyPosts()
        viewModell._experiencesLiveData.observe(viewLifecycleOwner, Observer {
            PostUserAdapter.submitList(it!!)
        })*/
        //
        SwipeRefresh=view.findViewById(R.id.SwipeRefreshExplore)
        //
        SwipeRefresh.setOnRefreshListener {
            ShowAllPosts()
            SwipeRefresh.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }

    }


    fun ShowAllPosts() {
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.ShowAllPost().enqueue(object : Callback<List<PostsPlaces>> {
            override fun onResponse(
                call: Call<List<PostsPlaces>>,
                response: Response<List<PostsPlaces>>
            ) {
                var DataBody = response.body()?.toString()
                println("Boddddyyyy " + DataBody)
                println("Boddddyyyy " + response.body())

                PostModels = ArrayList(response.body()!!)
                PostUserAdapter.submitList(PostModels)
            }
            override fun onFailure(call: Call<List<PostsPlaces>>, t: Throwable) {
                // Log.e("RETROFIT_ERROR", Response.code().toString())
                println("Message :" + t.stackTrace)
            }

        })
    }

/*    override fun onDetach() {
        super.onDetach();
        activity?.let{
            // val intent = Intent (it, Navigation::class.java)
            //it.startActivity(intent)
            fragmentManager?.beginTransaction()?.replace(R.id.container, ExploreFragmenttodo())
                ?.addToBackStack("ExploreTodo")?.commit()
            (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
        }
    }*/
}