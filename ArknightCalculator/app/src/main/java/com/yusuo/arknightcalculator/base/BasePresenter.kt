package com.yusuo.zsshop.base

import com.yusuo.zsshop.contract.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BasePresenter: CoroutineScope by MainScope(), MainContract.MainPresenter {

    abstract fun cancelCoroutineSocope()
}