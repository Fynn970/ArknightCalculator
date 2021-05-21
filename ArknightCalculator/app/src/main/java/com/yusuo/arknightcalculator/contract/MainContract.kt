package com.yusuo.zsshop.contract

import com.yusuo.zsshop.base.BasePresenter
import com.yusuo.zsshop.pojo.CapableOfficial

interface MainContract {

    interface MainView{
        fun setNewData(data:ArrayList<Map<String, List<CapableOfficial>>>)
        fun toastError(str:String)
    }
    interface MainPresenter{
        fun onZiZhiFlowClickListener(str: String, isSeletcted: Boolean)
        fun onWeiZhiFlowClickListener(str: String, isSeletcted: Boolean)
        fun onZhongLeiFlowClickListener(str: String, isSeletcted: Boolean)
        fun onCiZhuiFlowClickListener(str: String, isSeletcted: Boolean)
    }
    interface MainModel{
        suspend fun httpDataGet(): ArrayList<CapableOfficial>
    }
}