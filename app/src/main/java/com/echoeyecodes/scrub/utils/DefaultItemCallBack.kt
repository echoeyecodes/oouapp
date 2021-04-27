package com.echoeyecodes.dobby.utils

import androidx.recyclerview.widget.DiffUtil
import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.FilterListModel
import com.echoeyecodes.scrub.models.FilterModel
import com.echoeyecodes.scrub.models.UserModel

class DefaultItemCallBack : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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