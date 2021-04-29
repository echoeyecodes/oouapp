package com.echoeyecodes.scrub.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.adapters.EmptyAdapter
import com.echoeyecodes.scrub.adapters.chartadapters.BarChartAdapter
import com.echoeyecodes.scrub.adapters.chartadapters.OverallCGPAChartAdapter
import com.echoeyecodes.scrub.adapters.chartadapters.PieChartAdapter
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.model.AnalyticsResponseModel
import com.echoeyecodes.scrub.models.*
import com.echoeyecodes.scrub.utils.AndroidUtilities
import com.echoeyecodes.scrub.viewmodels.AnalyticsViewModel


class AnalyticsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel:AnalyticsViewModel
    private lateinit var backBtn:ImageButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        viewModel = ViewModelProvider(this).get(AnalyticsViewModel::class.java)

        backBtn = findViewById(R.id.back_btn)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        recyclerView = findViewById(R.id.recycler_view)

        swipeRefreshLayout.setOnRefreshListener(this)

        val emptyAdapter = EmptyAdapter()
        val pieChartAdapter = PieChartAdapter()
        val barChartAdapter = BarChartAdapter()
        val overallCGPAChartAdapter = OverallCGPAChartAdapter()

        val adapter = ConcatAdapter(emptyAdapter, pieChartAdapter, barChartAdapter, overallCGPAChartAdapter)
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = CustomItemDecoration(10)

        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        emptyAdapter.submitList(listOf(EmptyModel(ResourcesCompat.getDrawable(resources, R.drawable.ic_empty_ufo, null)!!, "No data here tbh!")))

//        pieChartAdapter.submitList(listOf(PieChartModel("Some pie chart here", "Hahaha... who's the boss", 26, listOf(SliceModel(30f, "A"), SliceModel(20f, "B"), SliceModel(15f, "C"), SliceModel(18f, "D"), SliceModel(12f, "E"), SliceModel(5f, "F")))))
//        barChartAdapter.submitList(listOf(BarChartModel("Title goes here", "Description vlaue which should be needed", listOf(Axis(1,50, "First Class"), Axis(2,70, "Second Class"), Axis(3,200, "Third Class")))))
//        overallCGPAChartAdapter.submitList(listOf(""))

        viewModel.getNetworkState().observe(this, {
            swipeRefreshLayout.isRefreshing = it == ApiState.Loading
            when(it){
                is ApiState.Complete<*> ->{
                    val data = it.data as AnalyticsResponseModel?
                    if(data != null){
                        AndroidUtilities.log(data.toString())
                        emptyAdapter.submitList(ArrayList())
                        pieChartAdapter.submitList(listOf(data.gradeDistribution))
                        barChartAdapter.submitList(listOf(data.gradeGroup))
//                        barChartAdapter.submitList(listOf())
                    }
                }
                is ApiState.Error ->{
                    AndroidUtilities.showSnackBar(recyclerView, it.data)
                }
            }
        })

        backBtn.setOnClickListener { onBackPressed() }
    }

    override fun onRefresh() {
        viewModel.fetchData()
    }
}