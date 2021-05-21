package com.yusuo.zsshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yusuo.arknightcalculator.R
import com.yusuo.zsshop.layout.FlowLayout
import com.yusuo.zsshop.utils.Utils

class FlowAdapter(val mContext: Context, var mPlanetList: List<String>) : FlowLayout.Adapter<FlowAdapter.FlowViewHolder>() {

    private lateinit var mItemClickListener:OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup): FlowViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.itemlayout, parent, false)
        var mlp = ViewGroup.MarginLayoutParams(view.layoutParams)
        var margin = Utils.dip2px(mContext, 5)
        mlp.setMargins(margin, margin, margin, margin)
        view.layoutParams = mlp
        return FlowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowViewHolder, position: Int) {
        holder.content.setText(mPlanetList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(holder.content,mPlanetList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return  mPlanetList.size - 1
    }

    class FlowViewHolder(itemView : View) : FlowLayout.ViewHolder(itemView) {
        var content: TextView

        init{
            content = itemView.findViewById(R.id.tv_test_content)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, str: String, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }
}

