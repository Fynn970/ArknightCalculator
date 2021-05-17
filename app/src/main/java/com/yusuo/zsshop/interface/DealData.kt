package com.yusuo.zsshop.`interface`

import com.yusuo.zsshop.pojo.CapableOfficial

@FunctionalInterface
interface DealData<T> {

    fun getDataByList(list : ArrayList<CapableOfficial>, str: String): ArrayList<CapableOfficial>
}