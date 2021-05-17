package com.yusuo.zsshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yusuo.zsshop.R
import com.yusuo.zsshop.layout.RvItemFlowLayout
import com.yusuo.zsshop.pojo.CapableOfficial
import java.util.zip.Inflater

class RecycleAdapter(context: Context, data:List<Map<String, List<CapableOfficial>>>): RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder>() {

    private lateinit var normalView: View
    private var data:List<Map<String, List<CapableOfficial>>> = data
    private var mContext = context
    private var mInflater = LayoutInflater.from(mContext);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        var view = mInflater.inflate(R.layout.additemrecyclelayout, parent, false)
//            normalView = View.inflate(parent.context, R.layout.additemrecyclelayout, null)
            return RecycleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        if (data.size != position){
            var key = ArrayList(data[position].keys)[0]
            holder.tvAddText.setText(key)
            var mapValue = data[position][key]
            if (mapValue != null){
            var rvItemFlowAdapter = RvItemFlowAdapter(mContext, mapValue)
            holder.rvAddFlowLayout.setAdapter(rvItemFlowAdapter)}
        }
    }

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAddText:TextView
        var rvAddFlowLayout: RvItemFlowLayout
        init {
            tvAddText = itemView.findViewById(R.id.additemrecycle_tv)
            rvAddFlowLayout = itemView.findViewById(R.id.additemrecycle_rvfl)
        }
    }

}