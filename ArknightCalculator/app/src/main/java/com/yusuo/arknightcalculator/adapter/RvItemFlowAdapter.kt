package com.yusuo.zsshop.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yusuo.arknightcalculator.R
import com.yusuo.zsshop.layout.FlowLayout
import com.yusuo.zsshop.layout.RvItemFlowLayout
import com.yusuo.zsshop.pojo.CapableOfficial
import com.yusuo.zsshop.utils.Utils

class RvItemFlowAdapter(
    var mContext: Context,
    var mPlanetList: List<CapableOfficial>
) : RvItemFlowLayout.Adapter<RvItemFlowAdapter.RvItemFlowViewHolder>() {

    private lateinit var mItemClickListener:OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup): RvItemFlowViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.itemlayout, parent, false)
        var mlp = ViewGroup.MarginLayoutParams(view.layoutParams)
        var margin = Utils.dip2px(mContext, 5)
        mlp.setMargins(margin, margin, margin, margin)
        view.layoutParams = mlp
        return RvItemFlowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvItemFlowViewHolder, position: Int) {
        holder.content.setText(mPlanetList[position].name)
        if (mPlanetList[position].level == 6){
            holder.content.setBackgroundResource(R.drawable.sixstaritembg)
        }else if (mPlanetList[position].level == 5){
            holder.content.setBackgroundResource(R.drawable.fivestaritembg)
        }else if (mPlanetList[position].level == 1){
            holder.content.setBackgroundResource(R.drawable.onestaritembg)
            holder.content.setTextColor(Color.WHITE)
        }else{
            holder.content.setBackgroundResource(R.drawable.twotofourstaritembg)
            holder.content.setTextColor(Color.WHITE)
        }
//        holder.itemView.setOnClickListener {
//            mItemClickListener.onItemClick(holder.content,mPlanetList[position], position)
//        }
    }

    override fun getItemCount(): Int {
        return  mPlanetList.size - 1
    }

    class RvItemFlowViewHolder(itemView : View) : FlowLayout.ViewHolder(itemView) {
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

