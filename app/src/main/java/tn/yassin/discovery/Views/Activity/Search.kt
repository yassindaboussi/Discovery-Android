package tn.yassin.discovery.Views.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Adapter.SearchAdapter
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Network.AdminApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.ReadyFunction

class Search : AppCompatActivity() {
    val ReadyFunction = ReadyFunction()
    lateinit var searchViewSearch: SearchView
    lateinit var RecyclerViewSearch: RecyclerView
    lateinit var AdapterSearch: SearchAdapter
    var PostsModels: ArrayList<PostsAdmin> = ArrayList()
    lateinit var btnFilter: Button
    ///
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchViewSearch=findViewById(R.id.searchViewSearch)
        RecyclerViewSearch=findViewById(R.id.RecyclerViewSearch)
       // btnFilter=findViewById(R.id.btnFilter)

        searchViewSearch.isIconified = false
        searchViewSearch.clearFocus()
        searchViewSearch.setQueryHint("Type your keyword here");
        searchViewSearch.onActionViewExpanded();

        //(requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
       // ReadyFunction.hideKeyboard(searchViewSearch, this@Search)

        RecyclerViewSearch.setLayoutManager(StaggeredGridLayoutManager(2, 1))
        AdapterSearch = SearchAdapter(getApplicationContext())
        RecyclerViewSearch.adapter = AdapterSearch

        ShowAll()

        OnSearch()

        ComboLieux()

        ComboStars()
    }

    fun OnSearch() {
        searchViewSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                AdapterSearch.getFilter()?.filter(newText)
                println("onQueryTextChange "+newText)
                return true
            }
        })
        //
    }

    fun ShowAll() {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: AdminApi = retrofi.create(AdminApi::class.java)
        val call: Call<List<PostsAdmin>> = service.PostSortedbyRate()
        call.enqueue(object : Callback<List<PostsAdmin>> {
            override fun onResponse(call: Call<List<PostsAdmin>>, response: Response<List<PostsAdmin>>) {
                PostsModels = ArrayList(response.body())
                AdapterSearch.setDataList(PostsModels)
                //AdapterSearch.notifyDataSetChanged()
            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<PostsAdmin>>, t: Throwable) {
                println("Message :" + t.stackTrace)
                Log.d("***", "Opppsss" + t.message)
            }
        })
    }

    fun ComboLieux()
    {
        var LISTE: Array<String>
        val actvGroup = findViewById<AutoCompleteTextView>(R.id.actvStarsGroup) as? AutoCompleteTextView
        LISTE = getApplicationContext().resources.getStringArray(R.array.StarsGroups) as Array<String>
        ReadyFunction.autoCompleteListAdapter(getApplicationContext(), LISTE, actvGroup!!)
    }

    fun ComboStars()
    {
        var LISTE: Array<String>
        val actvGroup = findViewById<AutoCompleteTextView>(R.id.actvLieuxGroup) as? AutoCompleteTextView
        LISTE = getApplicationContext().resources.getStringArray(R.array.LieuxGroups) as Array<String>
        ReadyFunction.autoCompleteListAdapter(getApplicationContext(), LISTE, actvGroup!!)
    }


}