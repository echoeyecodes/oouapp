package com.echoeyecodes.scrub.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.echoeyecodes.dobby.utils.getGradeValue
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel
import com.echoeyecodes.scrub.models.UserModel
import com.echoeyecodes.scrub.repositories.CourseRepository
import com.echoeyecodes.scrub.utils.AndroidUtilities

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val courses : LiveData<List<CourseModel>>
    val courseRepository = CourseRepository(application)
    val user : LiveData<UserModel?>
    val mediatorLiveData = MediatorLiveData<List<CourseModel>>()
    var filters = HashMap<Filter, List<FilterModel>>().apply {
        put(Filter.LEVEL, ArrayList())
        put(Filter.SEMESTER, ArrayList())
        put(Filter.UNITS, ArrayList())
    }
    val gpa = MutableLiveData("0.00")
    var filtersLiveData = MutableLiveData(filters)

    init {

        user = courseRepository.getUserLiveData()
        courses = courseRepository.getCoursesLiveData().map {
            AndroidUtilities.log(it.toString())
            val x = it.map {
                setMap(Filter.SEMESTER, FilterModel(it.semester))
                setMap(Filter.UNITS, FilterModel(it.units))
                setMap(Filter.LEVEL, FilterModel(it.code[3].plus("00")))
                it
            }
            filtersLiveData.value = filters
            x
        }
        mediatorLiveData.addSource(courses){
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(filtersLiveData){
            mediatorLiveData.value = sortList(it)
        }
        fetchData()
    }

    fun fetchData(){
        courseRepository.addCourses()
    }

     fun calculateGPA(list:List<CourseModel>){
        if(list.isEmpty()){
            gpa.value = "0.00"
        }else{
            val obtainedUnits = list.fold(0.00, { acc, next -> acc + (next.units.toInt() * next.grade.getGradeValue()) })
            val totalUnits =  list.fold(0.00, { acc, next -> acc + next.units.toInt() })
            val totalGpa = obtainedUnits/totalUnits
            gpa.value = String.format("%.2f", totalGpa)
        }
    }

    private fun sortList(filter:HashMap<Filter, List<FilterModel>>) : List<CourseModel>{
        return courses.value?.filter { e ->
            val item = (filter[Filter.LEVEL]!!.find {f -> f.name[0] == e.code[3] } )
            item != null && item.selected
        }?.filter { e ->
            val item = (filter[Filter.UNITS]!!.find { f -> f.name == e.units })
            item != null && item.selected
        }?.filter { e ->
            val item = (filter[Filter.SEMESTER]!!.find { f -> f.name == e.semester })
            item != null && item.selected
        } ?: ArrayList()
    }

    fun getCourseFilters():HashMap<Filter, List<FilterModel>>{
        courses.value?.map {
            setMap(Filter.SEMESTER, FilterModel(it.semester))
            setMap(Filter.UNITS, FilterModel(it.units))
            setMap(Filter.LEVEL, FilterModel(it.code[3].plus("00")))
        }
        return filters
    }

    fun onFilterSelected(filter:HashMap<Filter, List<FilterModel>>){
        filtersLiveData.value = HashMap(filter)
    }

    private fun setMap(filter:Filter, model:FilterModel){
        val item = ArrayList(filters[filter]!!)
        val exists = item.find { it.name == model.name }
        if(exists == null){
            item.add(model)
        }
        filters[filter] = item
    }

    fun getNetworkState():LiveData<ApiState<Any>>{
        return courseRepository.state
    }
}