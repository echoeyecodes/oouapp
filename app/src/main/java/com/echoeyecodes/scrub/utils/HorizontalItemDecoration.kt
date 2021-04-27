package com.echoeyecodes.dobby.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class HorizontalItemDecoration(left: Int, right: Int) : ItemDecoration() {
    private val left: Int = left.convertToDp()
    private val right:Int = right.convertToDp()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.left = this.left
        }
        outRect.right = this.right
    }
}