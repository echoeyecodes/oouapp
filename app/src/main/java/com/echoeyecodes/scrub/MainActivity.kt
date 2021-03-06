package com.echoeyecodes.scrub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.echoeyecodes.scrub.adapters.CourseAdapter
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.scrub.activities.AnalyticsActivity
import com.echoeyecodes.scrub.adapters.EmptyAdapter
import com.echoeyecodes.scrub.adapters.ProfileAdapter
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.callbacks.MainActivityCallBack
import com.echoeyecodes.scrub.fragments.AlertDialogFragment
import com.echoeyecodes.scrub.fragments.FilterFragment
import com.echoeyecodes.scrub.models.EmptyModel
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel
import com.echoeyecodes.scrub.utils.AndroidUtilities
import com.echoeyecodes.scrub.utils.AuthManager
import com.echoeyecodes.scrub.utils.MyApplication
import com.echoeyecodes.scrub.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity(), MainActivityCallBack, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var recyclerView:RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var filterBtn:ImageButton
    private lateinit var gpaTextView:TextView
    private lateinit var timestamp:TextView
    private lateinit var logOutBtn:ImageButton
    private lateinit var chartBtn:ImageButton
    private lateinit var filterFragment:FilterFragment
    private lateinit var alertDialogFragment:AlertDialogFragment
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        recyclerView = findViewById(R.id.recycler_view)
        logOutBtn = findViewById(R.id.exit_btn)
        chartBtn = findViewById(R.id.chart_btn)
        filterBtn = findViewById(R.id.filter_btn)
        gpaTextView = findViewById(R.id.gpa)
        timestamp = findViewById(R.id.timestamp)
        val courseAdapter = CourseAdapter()
        val profileAdapter = ProfileAdapter(this)
        val emptyAdapter = EmptyAdapter()

        filterFragment = getFragmentByTag(FilterFragment.TAG) as FilterFragment? ?: FilterFragment.newInstance()
        alertDialogFragment = getFragmentByTag(AlertDialogFragment.TAG) as AlertDialogFragment? ?: AlertDialogFragment.newInstance()
        val adapter = ConcatAdapter(emptyAdapter, profileAdapter, courseAdapter)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = CustomItemDecoration(10, 15)

        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        filterBtn.setOnClickListener {
            val fragment = (getFragmentByTag("FILTER_FRAGMENT") as FilterFragment?) ?: FilterFragment.newInstance()
            if(!fragment.isAdded){
                fragment.show(supportFragmentManager, "FILTER_FRAGMENT")
            }
        }

        logOutBtn.setOnClickListener { showRemoveAccountAlertDialog() }
        chartBtn.setOnClickListener { startActivity(Intent(this, AnalyticsActivity::class.java)) }

        swipeRefreshLayout.setOnRefreshListener(this)

        viewModel.user.observe(this, {
            if(it != null){
                emptyAdapter.submitList(ArrayList())
                profileAdapter.submitList(listOf(it))
                timestamp.text = "Last Updated: ${it.getTimeDifference()}"
            }else{
                emptyAdapter.submitList(listOf(EmptyModel(ResourcesCompat.getDrawable(resources, R.drawable.ic_empty, null)!!, "No data to show! \\n Wanna see a trick? Swipe down to refresh :-)")))            }
        })

        viewModel.mediatorLiveData.observe(this, {
            viewModel.calculateGPA(it)
            courseAdapter.submitList(it)
        })

        viewModel.gpa.observe(this, {
            gpaTextView.text = "CGPA: ${it}"
        })

        viewModel.getNetworkState().observe(this, {
            swipeRefreshLayout.isRefreshing = it == ApiState.Loading

            when(it){
                is ApiState.Error ->{
                   AndroidUtilities.showSnackBar(swipeRefreshLayout, it.data)
                }
            }
        })

    }

    override fun getFilters(): HashMap<Filter, List<FilterModel>> {
        return viewModel.getCourseFilters()
    }

    private fun getFragmentByTag(tag:String): Fragment?{
        return supportFragmentManager.findFragmentByTag(tag)
    }

//    override fun onStart() {
//        super.onStart()
//        startActivity(Intent(this, AnalyticsActivity::class.java))
//    }

    override fun setFilters(filter: HashMap<Filter, List<FilterModel>>) {
        viewModel.onFilterSelected(filter)
    }

    override fun showRemoveAccountAlertDialog() {
        if(!alertDialogFragment.isAdded){
            alertDialogFragment.show(supportFragmentManager, AlertDialogFragment.TAG)
        }
    }

    private fun logOut(){
        AuthManager().logOut()
    }

    override fun onResume() {
        super.onResume()
        MyApplication.myContext = this
        window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
    }

    override fun removeAccount() {
        alertDialogFragment.dismiss()
        logOut()
    }

    override fun onRefresh() {
        viewModel.fetchData()
    }
}