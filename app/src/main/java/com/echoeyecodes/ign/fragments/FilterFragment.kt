package com.echoeyecodes.dobby.fragments.bottomsheets


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.AndroidUtilities
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.dobby.utils.FilterListItemCallBack
import com.echoeyecodes.ign.R
import com.echoeyecodes.ign.MainActivity
import com.echoeyecodes.ign.adapters.FilterListAdapter
import com.echoeyecodes.ign.callbacks.FilterFragmentCallback
import com.echoeyecodes.ign.callbacks.MainActivityCallBack
import com.echoeyecodes.ign.models.Filter
import com.echoeyecodes.ign.models.FilterListModel
import com.echoeyecodes.ign.models.FilterModel
import com.echoeyecodes.ign.viewmodels.FilterFragmentViewModel
import com.echoeyecodes.ign.viewmodels.FilterFragmentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterFragment : BottomSheetDialogFragment(), FilterFragmentCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModelFactory: FilterFragmentViewModelFactory
    private lateinit var viewModel: FilterFragmentViewModel
    private lateinit var mainActivityCallBack: MainActivityCallBack
    private lateinit var applyBtn:MaterialButton
    private lateinit var cancelBtn:MaterialButton

    companion object{
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
        viewModelFactory = FilterFragmentViewModelFactory(mainActivityCallBack.getFilters())
        viewModel = ViewModelProvider(this, viewModelFactory).get(FilterFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
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
                    FilterListModel(Filter.SESSION, it[Filter.SESSION]!!),
                    FilterListModel(Filter.SEMESTER, it[Filter.SEMESTER]!!)))))
        })

        cancelBtn.setOnClickListener { dismiss() }
        applyBtn.setOnClickListener {
            mainActivityCallBack.setFilters(viewModel.filtersLiveData.value!!)
            dismiss()
        }
        return view
    }

    override fun onFilterSelected(name: Filter, filter: FilterModel) {
        viewModel.onFilterSelected(name, filter)
    }

}