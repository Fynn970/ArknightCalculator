package com.yusuo.zsshop.layout

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntegerRes
import com.yusuo.zsshop.R
import com.yusuo.zsshop.adapter.FlowAdapter
import java.lang.IllegalArgumentException


open class FlowLayout: ViewGroup {

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
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var a = helper(widthSize)
        var measuredHeight = 0
        if (heightMode == MeasureSpec.EXACTLY){
            measuredHeight = heightSize;
        }else{
            measuredHeight = a[0]!!
        }

        var measuredWidth = 0
        if (widthMode == MeasureSpec.EXACTLY){
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
            var mlp : MarginLayoutParams

            if (params is MarginLayoutParams){
                mlp = params
            }else{
                mlp = MarginLayoutParams(params)
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

    open abstract class  Adapter<VH : ViewHolder>{
        abstract fun onCreateViewHolder(parent: ViewGroup) : VH
        abstract fun onBindViewHolder(holder: VH, position:Int)
        abstract fun getItemCount():Int
    }

    abstract class ViewHolder(itemView: View){
        var itemView: View

        init{
            this.itemView = itemView
        }
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event != null) {
//            when (event.action){
//                 MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN-> {
//                     var x = event.getX();
//                     var y = event . getY ();
//                     if (x > width - 2 * textWidth) {
//                         index = (int)(y / (height / 27));
//                         //此处有增加，当屏幕被点击后，将参数传入。
//                         if (listener != null) {
//                             listener.onItemSelect(index, array[index]);
//                         }
//                         invalidate();
//                         return true;
//                     }
//                 }
//
//                    MotionEvent.ACTION_UP-> {
//                        index = -1;
//                        invalidate();
//                        return true;
//                    }
//            }
//        }
//        return super.onTouchEvent(event)
//    }
}