package com.yusuo.zsshop.layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.yusuo.zsshop.adapter.FlowAdapter
import com.yusuo.zsshop.adapter.RvItemFlowAdapter

class RvItemFlowLayout: ViewGroup {

    private val TAG = "FLOWLAYOUT"
    private var mChildrenPositionList = ArrayList<Rect>()
    private  var mMaxLines  = Int.MAX_VALUE;
    private var mVisibleItemCount: Int = 0;

//    private lateinit var listener:OnItemClickListener

//    private var mHorizontalSpacing: Int = dp2px(16)
//    private var mVerticalSpacing = dp2px(8)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
    )

//    interface OnItemClickListener{
//        fun OnItemSelect(index: Int, indexString: String)
//    }
//
//    fun setOnItemClickListener(listener:OnItemClickListener){
//        this.listener = listener
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mChildrenPositionList.clear()

        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        var widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        var a = helper(widthSize)
        var measuredHeight = 0
        if (heightMode == View.MeasureSpec.EXACTLY){
            measuredHeight = heightSize;
        }else{
            measuredHeight = a[0]!!
        }

        var measuredWidth = 0
        if (widthMode == View.MeasureSpec.EXACTLY){
            measuredWidth = widthSize
        }else{
            measuredWidth = a[1]!!
        }

        setMeasuredDimension(measuredWidth, measuredHeight)
    }
    fun helper(widthSize : Int): Array<Int?> {
        var isOneRow = true
        var width = paddingLeft
        var height = paddingTop
        var maxHeight = 0
        var currLine = 1

        for (i in 0 until childCount){
            var child = getChildAt(i)
//            child.setOnClickListener{
//                val t:TextView = it as TextView
//
//                Toast.makeText(context, t.text, Toast.LENGTH_SHORT).show()
//                println(
//                    t.resources)
//            }
            var params = child.layoutParams
            var mlp : ViewGroup.MarginLayoutParams

            if (params is ViewGroup.MarginLayoutParams){
                mlp = params
            }else{
                mlp = ViewGroup.MarginLayoutParams(params)
            }

            var childWidth = mlp.leftMargin + child.measuredWidth + mlp.rightMargin
            var childHeight = mlp.topMargin + child.measuredHeight + mlp.bottomMargin
            maxHeight = Math.max(maxHeight, childHeight)

            if (width + childWidth + paddingRight > widthSize){
                height += maxHeight
                width = paddingLeft
                maxHeight = childHeight
                isOneRow = false
                currLine++
                if (currLine > mMaxLines){
                    break
                }
            }
            var rect = Rect(width + mlp.leftMargin,
                height + mlp.topMargin,
                width + childWidth - mlp.rightMargin,
                height + childHeight - mlp.bottomMargin)
            mChildrenPositionList.add(rect)

            width += childWidth
        }
        var res = arrayOfNulls<Int>(2)
        res[0] = height + maxHeight + paddingBottom
        res[1] = if(isOneRow) width + paddingRight else widthSize
        return res
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var n = Math.min(childCount, mChildrenPositionList.size)
        for (i in 0 until n){
            var child = getChildAt(i)
            var rect = mChildrenPositionList.get(i)
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
        mVisibleItemCount = n
    }

    open fun setAdapter(adapter: FlowAdapter){
        removeAllViews()
        var n = adapter.getItemCount()
        for (i in 0..n){
            var holder = adapter.onCreateViewHolder(this)
            adapter.onBindViewHolder(holder, i)
            var child = holder.itemView
            addView(child)
        }
    }

    fun setMaxLines(maxLines:Int){
        mMaxLines = maxLines
    }

    fun getVisibleItemCount():Int = mVisibleItemCount
    fun setAdapter(adapter: RvItemFlowAdapter){
        removeAllViews()
        var n = adapter.getItemCount()
        for (i in 0..n){
            var holder = adapter.onCreateViewHolder(this)
            adapter.onBindViewHolder(holder, i)
            var child = holder.itemView
            addView(child)
        }
    }

    abstract class  Adapter<VH : FlowLayout.ViewHolder>{
        abstract fun onCreateViewHolder(parent: ViewGroup) : VH
        abstract fun onBindViewHolder(holder: VH, position:Int)
        abstract fun getItemCount():Int
    }

}