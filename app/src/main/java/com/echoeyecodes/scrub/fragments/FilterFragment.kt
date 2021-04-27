package com.echoeyecodes.scrub.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.MainActivity
import com.echoeyecodes.scrub.adapters.FilterListAdapter
import com.echoeyecodes.scrub.callbacks.FilterFragmentCallback
import com.echoeyecodes.scrub.callbacks.MainActivityCallBack
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterListModel
import com.echoeyecodes.scrub.models.FilterModel
import com.echoeyecodes.scrub.viewmodels.FilterFragmentViewModel
import com.echoeyecodes.scrub.viewmodels.FilterFragmentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class FilterFragment : BottomSheetDialogFragment(), FilterFragmentCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModelFactory: FilterFragmentViewModelFactory
    private lateinit var viewModel: FilterFragmentViewModel
    private lateinit var mainActivityCallBack: MainActivityCallBack
    private lateinit var applyBtn:MaterialButton
    private lateinit var cancelBtn:MaterialButton

    companion object{
        const val TAG = "FILTER_FRAGMENT"
        fun newInstance() = FilterFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity){
            mainActivityCallBack = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = FilterFragmentViewModelFactory(mainActivityCallBack.getFilters())
        viewModel = ViewModelProvider(this, viewModelFactory).get(FilterFragmentViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view)
        applyBtn = view.findViewById(R.id.apply_btn)
        cancelBtn = view.findViewById(R.id.cancel_btn)

        val adapter = FilterListAdapter(this)
        val itemDecoration = CustomItemDecoration(10)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.adapter = adapter

        viewModel.filtersLiveData.observe(this, {
            adapter.submitList(ArrayList((listOf(FilterListModel(Filter.LEVEL, it[Filter.LEVEL]!!),
                FilterListModel(Filter.UNITS, it[Filter.UNITS]!!),
                FilterListModel(Filter.SEMESTER, it[Filter.SEMESTER]!!)))))
        })

        cancelBtn.setOnClickListener { dismiss() }
        applyBtn.setOnClickListener {
            mainActivityCallBack.setFilters(viewModel.filtersLiveData.value!!)
            dismiss()
        }
    }

    override fun onFilterSelected(name: Filter, filter: FilterModel) {
        viewModel.onFilterSelected(name, filter)
    }

}