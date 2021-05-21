package com.yusuo.zsshop.presenter

import com.yusuo.zsshop.base.BasePresenter
import com.yusuo.zsshop.model.MainModelImpl
import com.yusuo.zsshop.contract.MainContract
import com.yusuo.zsshop.pojo.CapableOfficial
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainPresenterImpl(val mainView: MainContract.MainView): BasePresenter() {

    private var mSelectedStrList = ArrayList<String>()
    private var selectedCount = 0

    private var mDemoList = ArrayList<Map<String, List<CapableOfficial>>>()
    private lateinit var data: ArrayList<CapableOfficial>

    private var mMainModel:MainContract.MainModel

    init {
        mMainModel = MainModelImpl(this)
        launch {
            data = async{
                mMainModel.httpDataGet()
            }.await()
        }

    }

    override fun onZiZhiFlowClickListener(str: String, isSeletcted: Boolean) {
        if (str.equals("资深干员"))
            isItemSelected(str,3, !isSeletcted)
        else if(str.equals("高级资深干员"))
            isItemSelected(str,4, !isSeletcted)
        else if(str.equals("新手"))
            isItemSelected(str,5, !isSeletcted)
    }

    override fun onWeiZhiFlowClickListener(str: String, isSeletcted: Boolean) {
        isItemSelected(str,2, !isSeletcted)
    }

    override fun onZhongLeiFlowClickListener(str: String, isSeletcted: Boolean) {
        isItemSelected(str, 1, !isSeletcted)
    }

    override fun onCiZhuiFlowClickListener(str: String, isSeletcted: Boolean) {
        isItemSelected(str, 2, !isSeletcted)
    }


    /**
     * 通过判断是否选择来分别处理数据
     */
    private fun isItemSelected(str: String, type:Int, isSeletcted: Boolean){
        if (!isSeletcted){
            var temp = ArrayList(mDemoList.filter { x-> ArrayList(x.keys)[0].indexOf(str) == -1 })
            mDemoList.clear()
            mDemoList.addAll(temp)
            selectedCount--
        }else{
            if (selectedCount >= 6){
                mainView.toastError("标签最多点6个哦")
                return
            }
            dealData(str, type)
            if (mSelectedStrList.size > 1){
                val set = HashSet(mSelectedStrList)
                mSelectedStrList.clear()
                mSelectedStrList.addAll(set)
            }
        }
        mainView.setNewData(mDemoList)
    }

    private fun deleteSelectedData(str: String) {
        mDemoList.filter { x-> ArrayList(x.keys)[0].indexOf(str) == -1 }
    }

    private fun filterList(list:List<String>,str:String):Boolean {
        var isselected = false
        for (s in list) {
            if (s.equals(str)) {
                isselected = true
            }
        }
        return isselected
    }

    fun dealData(str:String, type:Int){
        var map: java.util.HashMap<String, List<CapableOfficial>>
        var tempList = ArrayList<HashMap<String, List<CapableOfficial>>>()
        var temp: List<CapableOfficial>

        map = getTypeData(str,type)
        tempList.add(map)
        map = HashMap()
        for (i in mDemoList){
            val key = ArrayList<String>(i.keys)[0]
            val value = i[key]?.toList()
            temp = returnListByType(str, type, value)
            if (temp.size != 0) {
                map.put("$key  $str", temp)
                tempList.add(map)
                map = HashMap()
            }
        }

        mDemoList.addAll(tempList)
        Collections.sort(mDemoList, SortByLengthComparator())
    }

    private fun getTypeData(str:String, type:Int): HashMap<String, List<CapableOfficial>> {
        var map = HashMap<String, List<CapableOfficial>>()
        if (type == 1) {
            map.put(str,data.filter { x -> x.type.equals(str.substring(0,str.length-2))}.sortedByDescending { x->x.level }.sortedByDescending { x->x.level })
        }else if (type == 2){
            var temp = ArrayList<CapableOfficial>()
            for (i in data){
                for (j in i.tags){
                    if (j.equals(str)){
                        temp.add(i)
                        break
                    }
                }
            }
            map.put(str, temp.sortedByDescending { x->x.level })
        }else if (type == 3){
            map.put(str,data.filter { x -> x.level == 5}.sortedByDescending { x->x.level }.sortedByDescending { x->x.level })
        }else if (type == 4){
            map.put(str,data.filter { x -> x.level == 6}.sortedByDescending { x->x.level }.sortedByDescending { x->x.level })
        }else if (type == 5){
            map.put(str,data.filter { x -> x.level == 2}.sortedByDescending { x->x.level }.sortedByDescending { x->x.level })
        }
        return map
    }

    private fun returnListByType(str:String, type:Int, list: List<CapableOfficial>?): List<CapableOfficial> {
        var temp = ArrayList<CapableOfficial>()
        if (list != null) {
            if (type == 1) {
                temp = ArrayList(list.filter { x -> x.type.equals(str.substring(0, str.length - 2)) }
                    .sortedByDescending { x -> x.level })
            } else if (type == 2) {
                for (i in list){
                    for (j in i.tags){
                        if (j.equals(str)){
                            temp.add(i)
                            break
                        }
                    }
                }
                temp.sortedByDescending { x->x.level }

            } else if (type == 3) {
                temp = ArrayList(list.filter { x -> x.level == 5 }.sortedByDescending { x -> x.level })
            } else if (type == 4) {
                temp = ArrayList(list.filter { x -> x.level == 6 }.sortedByDescending { x -> x.level })
            }else if (type == 5) {
                temp = ArrayList(list.filter { x -> x.level == 2 }.sortedByDescending { x -> x.level })
            }
        }
        return temp
    }


    class SortByLengthComparator : Comparator<Map<String, List<CapableOfficial>>>{
        override fun compare(
            o1: Map<String, List<CapableOfficial>>,
            o2: Map<String, List<CapableOfficial>>
        ): Int {
            if (ArrayList(o1.keys)[0].length < ArrayList(o2.keys)[0].length){
                return 1
            }else if(ArrayList(o1.keys)[0].length == ArrayList(o2.keys)[0].length){
                return 0
            }else{
                return -1
            }
        }

    }

    override fun cancelCoroutineSocope() {
        cancel()
    }

}