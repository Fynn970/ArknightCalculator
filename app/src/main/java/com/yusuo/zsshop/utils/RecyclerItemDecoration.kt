package com.yusuo.zsshop.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecoration(itemSpace: Int, itemNum:Int): RecyclerView.ItemDecoration() {

    var itemSpace = itemSpace
    var itemNum = itemNum

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = itemSpace
        if (parent.getChildLayoutPosition(view) % itemNum == 0){
            outRect.left = 0
        }else{
            outRect.left = itemSpace
        }
    }
}