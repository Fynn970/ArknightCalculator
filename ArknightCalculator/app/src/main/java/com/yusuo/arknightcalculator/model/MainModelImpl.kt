package com.yusuo.zsshop.model

import com.yusuo.zsshop.contract.MainContract
import com.yusuo.zsshop.http.HttpAsk
import com.yusuo.zsshop.pojo.CapableOfficial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainModelImpl(val mMainPresenter: MainContract.MainPresenter): MainContract.MainModel{
    override suspend fun httpDataGet(): ArrayList<CapableOfficial> {
        return withContext(Dispatchers.IO){
            HttpAsk.getResponseBody("https://www.bigfun.cn/static/aktools/1620462222/data/akhr.json")
        }
    }


}