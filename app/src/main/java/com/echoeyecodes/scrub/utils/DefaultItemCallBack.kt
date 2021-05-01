package com.echoeyecodes.dobby.utils

import androidx.recyclerview.widget.DiffUtil
import com.echoeyecodes.scrub.models.*

class DefaultItemCallBack : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

class EmptyItemCallBack : DiffUtil.ItemCallback<EmptyModel>() {

    override fun areItemsTheSame(oldItem: EmptyModel, newItem: EmptyModel): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: EmptyModel, newItem: EmptyModel): Boolean {
        return oldItem == newItem
    }

}

class FilterListItemCallBack : DiffUtil.ItemCallback<FilterListModel>() {

    override fun areItemsTheSame(oldItem: FilterListModel, newItem: FilterListModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: FilterListModel, newItem: FilterListModel): Boolean {
        return oldItem == newItem
    }

}

class FilterItemCallBack : DiffUtil.ItemCallback<FilterModel>() {

    override fun areItemsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
        return oldItem == newItem
    }

}

class UserItemCallBack : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

}

class CourseItemCallBack : DiffUtil.ItemCallback<CourseModel>() {

    override fun areItemsTheSame(oldItem: CourseModel, newItem: CourseModel): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: CourseModel, newItem: CourseModel): Boolean {
        return oldItem == newItem
    }
}

class BarChartItemCallBack:DiffUtil.ItemCallback<BarChartModel>(){
    override fun areItemsTheSame(oldItem: BarChartModel, newItem: BarChartModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: BarChartModel, newItem: BarChartModel): Boolean {
        return oldItem == newItem
    }

}

class PieChartItemCallBack:DiffUtil.ItemCallback<PieChartModel>(){
    override fun areItemsTheSame(oldItem: PieChartModel, newItem: PieChartModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PieChartModel, newItem: PieChartModel): Boolean {
        return oldItem == newItem
    }

}

class LegendItemCallBack:DiffUtil.ItemCallback<LegendModel>(){
    override fun areItemsTheSame(oldItem: LegendModel, newItem: LegendModel): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: LegendModel, newItem: LegendModel): Boolean {
        return oldItem == newItem
    }

}