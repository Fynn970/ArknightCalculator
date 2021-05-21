package com.yusuo.zsshop.utils

import android.content.Context
import com.yusuo.zsshop.pojo.CapableOfficial

class Utils {
    companion object {

    fun dip2px(mContext: Context, dpValue: Int): Int {
        var scale = mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }



        fun getDataFromListByType(list:List<CapableOfficial>,str: String):List<CapableOfficial>{
            var temp = ArrayList<CapableOfficial>()
            for (i in list){
                if (i.type.equals(str)){
                    temp.add(i)
                }
            }
            return temp
        }
}
}