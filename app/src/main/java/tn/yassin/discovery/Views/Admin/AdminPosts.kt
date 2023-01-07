package tn.yassin.discovery.Views.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Models.PostsPlaces
import tn.yassin.discovery.Network.AdminApi
import tn.yassin.discovery.Network.PostUserApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.IDUSER
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.oneblood.DataMapList.AdminReportAdapter
import tn.yassin.oneblood.DataMapList.PostUserExploreAdapter
import tn.yassin.oneblood.DataMapList.RecommendedAdapter

class AdminPosts : Fragment() {
    lateinit var RecyclerReport: RecyclerView
    lateinit var AdapterAdminReport: AdminReportAdapter
    var PostsModels: ArrayList<PostsPlaces> = ArrayList()
    val retrofi: Retrofit = retrofit.getInstance()
    val service: AdminApi = retrofi.create(AdminApi::class.java)
    val ReadyFunction = ReadyFunction()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RecyclerReport = view.findViewById(R.id.RecyclerReport)
        //
        RecyclerReport.setLayoutManager(StaggeredGridLayoutManager(1, 1))
        AdapterAdminReport = AdminReportAdapter(requireContext())
        RecyclerReport.adapter = AdapterAdminReport
        //
        ShowAllReported()
    }

    fun ShowAllReported() {
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.GetReportedPost().enqueue(object : Callback<List<PostsPlaces>> {
            override fun onResponse(
                call: Call<List<PostsPlaces>>,
                response: Response<List<PostsPlaces>>
            ) {
                var DataBody = response.body()?.toString()
                println("Boddddyyyy " + DataBody)
                println("Boddddyyyy " + response.body())

                PostsModels = ArrayList(response.body()!!)
                AdapterAdminReport.submitList(PostsModels)
            }
            override fun onFailure(call: Call<List<PostsPlaces>>, t: Throwable) {
                // Log.e("RETROFIT_ERROR", Response.code().toString())
                println("Message :" + t.stackTrace)
            }

        })
    }





































































































































































































































































































































































































































































}