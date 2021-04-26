package com.echoeyecodes.ign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.adapters.CourseAdapter
import com.echoeyecodes.dobby.fragments.bottomsheets.FilterFragment
import com.echoeyecodes.dobby.utils.AndroidUtilities
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.ign.adapters.ProfileAdapter
import com.echoeyecodes.ign.api.constants.ApiState
import com.echoeyecodes.ign.callbacks.MainActivityCallBack
import com.echoeyecodes.ign.models.Filter
import com.echoeyecodes.ign.models.FilterModel
import com.echoeyecodes.ign.models.UserModel
import com.echoeyecodes.ign.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity(), MainActivityCallBack {
    private lateinit var recyclerView:RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var filterBtn:ImageButton
    private lateinit var gpaTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        recyclerView = findViewById(R.id.recycler_view)
        filterBtn = findViewById(R.id.filter_btn)
        gpaTextView = findViewById(R.id.gpa)
        val courseAdapter = CourseAdapter()
        val profileAdapter = ProfileAdapter(this)

        val adapter = ConcatAdapter(profileAdapter, courseAdapter)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = CustomItemDecoration(10, 15)

        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        filterBtn.setOnClickListener {
            val fragment = FilterFragment.newInstance()
            fragment.show(supportFragmentManager, "FILTER_FRAGMENT")
        }

        viewModel.user.observe(this, {
            if(it != null){
                profileAdapter.submitList(listOf(it))
            }
        })

        viewModel.mediatorLiveData.observe(this, {
            viewModel.calculateGPA(it)
            courseAdapter.submitList(it)
        })

        viewModel.gpa.observe(this, {
            gpaTextView.text = "CGPA: ${it}"
        })

        viewModel.getNetworkState().observe(this, {
            when(it){
                is ApiState.Error ->{
                   AndroidUtilities.showToastMessage(this, it.data)
                }
            }
        })

    }

    override fun getFilters(): HashMap<Filter, List<FilterModel>> {
        return viewModel.getCourseFilters()
    }

    override fun setFilters(filter: HashMap<Filter, List<FilterModel>>) {
        viewModel.onFilterSelected(filter)
    }
}