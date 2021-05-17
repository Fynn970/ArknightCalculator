package com.yusuo.zsshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusuo.zsshop.adapter.FlowAdapter
import com.yusuo.zsshop.adapter.FlowAdapter.OnItemClickListener
import com.yusuo.zsshop.adapter.RecycleAdapter
import com.yusuo.zsshop.http.HttpAsk
import com.yusuo.zsshop.layout.FlowLayout
import com.yusuo.zsshop.pojo.CapableOfficial
import kotlinx.coroutines.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {

//    private lateinit var mStarFlowLayout: FlowLayout
    private lateinit var mZiZhiFlowLayout: FlowLayout
    private lateinit var mWeiZhiFlowLayout: FlowLayout
    private lateinit var mZhongLeiFlowLayout: FlowLayout
    private lateinit var mCiZhuiFlowLayout: FlowLayout
    private lateinit var mRecycleView: RecyclerView
    private lateinit var data: ArrayList<CapableOfficial>
    private var mStarList = ArrayList<String>()
    private var mZiZhiList = ArrayList<String>(
        Arrays.asList("新手","资深干员","高级资深干员")
    )
    private var mWeiZhiList = ArrayList<String>(Arrays.asList("远程位","近战位"))
    private var mZhongLeiList = ArrayList<String>(
        Arrays.asList("先锋干员","狙击干员","医疗干员","术师干员","近卫干员","重装干员","辅助干员","特种干员")
    )
    private var mCiZhuiList = ArrayList<String>(
        Arrays.asList("治疗","支援","输出","群攻","减速","生存","防护","削弱","位移"
            ,"控场","爆发","召唤","快速复活","费用回复","支援机械"))

    private var mSelectedStrList = ArrayList<String>()
    private var selectedCount = 0


    private var mDemoList = ArrayList<Map<String, List<CapableOfficial>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除title
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN); //去状态栏
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initView() {
//        mStarFlowLayout = findViewById(R.id.fv_star)
        mZiZhiFlowLayout = findViewById(R.id.fv_zizhi)
        mWeiZhiFlowLayout = findViewById(R.id.fv_weizhi)
        mZhongLeiFlowLayout = findViewById(R.id.fv_zhonglei)
        mCiZhuiFlowLayout = findViewById(R.id.fv_cizhui)
        mRecycleView = findViewById(R.id.rv_recycledata)

//        var adapter = FlowAdapter(this, mStarList)
//        mStarFlowLayout.setAdapter(adapter)

        var ziZhiAdapter = FlowAdapter(this, mZiZhiList)
        mZiZhiFlowLayout.setAdapter(ziZhiAdapter)

        var weiZhiAdapter = FlowAdapter(this, mWeiZhiList)
        mWeiZhiFlowLayout.setAdapter(weiZhiAdapter)


        var zhongLeiAdapter = FlowAdapter(this, mZhongLeiList)
        mZhongLeiFlowLayout.setAdapter(zhongLeiAdapter)


        var ciZhuiAdapter = FlowAdapter(this, mCiZhuiList)
        mCiZhuiFlowLayout.setAdapter(ciZhuiAdapter)


        var llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mRecycleView.layoutManager = llm
        var recycleAdapter = RecycleAdapter(this,mDemoList)
        mRecycleView.adapter = recycleAdapter

//        adapter.setOnItemClickListener(object : OnItemClickListener{
//            override fun onItemClick(
//                view: View,
//                str: String,
//                position: Int
//            ) {
//                isItemSelected(str, view, 0)
//                recycleAdapter.notifyDataSetChanged()
//            }
//        })

        ziZhiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                if (str.equals("资深干员"))
                    isItemSelected(str, view,3)
                else if(str.equals("高级资深干员"))
                    isItemSelected(str, view,4)
                else if(str.equals("新手"))
                    isItemSelected(str, view,5)

                recycleAdapter.notifyDataSetChanged()
            }
        })

        weiZhiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                isItemSelected(str, view,2)
                recycleAdapter.notifyDataSetChanged()
            }
        })

        zhongLeiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                isItemSelected(str, view, 1)
                recycleAdapter.notifyDataSetChanged()
            }
        })


        ciZhuiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                isItemSelected(str, view, 2)
                recycleAdapter.notifyDataSetChanged()
            }
        })
    }

//    private fun mRecycleAdapterAddView(adapter: RecycleAdapter, str: String){
//        adapter.setAddViewListener(object:RecycleAdapter.addViewListener{
//            override fun onAddViewListener(position: Int) {
//                mSelectedStrList.add(str)
//                adapter.notifyDataSetChanged()
//            }
//        })
//    }

    private fun isItemSelected(str: String, view:View, type:Int){
        if (filterList(mSelectedStrList, str)){
            view.setBackgroundResource(R.drawable.tag_normal)
            mSelectedStrList.remove(str)
            var temp = ArrayList(mDemoList.filter { x-> ArrayList(x.keys)[0].indexOf(str) == -1 })
            mDemoList.clear()
            mDemoList.addAll(temp)
            selectedCount--
        }else{
            if (selectedCount >= 6){
                Toast.makeText(this, "标签最多点6个哦", Toast.LENGTH_SHORT).show()
                return
            }
            selectedCount++
            view.setBackgroundResource(R.drawable.tag_selected)
            mSelectedStrList.add(str)
            dealData(str, type)
            if (mSelectedStrList.size > 1){
                val set = HashSet(mSelectedStrList)
                mSelectedStrList.clear()
                mSelectedStrList.addAll(set)
            }
        }
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

    private fun initData() {
        GlobalScope.launch (Dispatchers.Main){
            var deferred = async(Dispatchers.IO) {

                HttpAsk.getResponseBody("https://www.bigfun.cn/static/aktools/1620462222/data/akhr.json")
            }
            data = deferred.await()
        }
        for (i in 0..6){
            mStarList.add("★$i")
        }

//        var a = ArrayList<String>()
//        a.add("能天使")
//        a.add("莫斯提吗")
//        a.add("星熊")
//        a.add("能天使")
//        a.add("莫斯提吗")
//        a.add("星熊")
//        a.add("能天使")
//        a.add("莫斯提吗")
//        a.add("星熊")
//        a.add("能天使")
//        a.add("莫斯提吗")
//        a.add("星熊")
//
//        var map = HashMap<String, List<String>>()
//        map.put("测试", a)
//
//        mDemoList.add(map)
//        mDemoList.add(map)
//        mDemoList.add(map)
//        mDemoList.add(map)
//        mDemoList.add(map)
//        mDemoList.add(map)
//        mDemoList.add(map)
    }



    fun dealData(str:String, type:Int){
        var map = HashMap<String, List<CapableOfficial>>()
        var tempList = ArrayList<HashMap<String, List<CapableOfficial>>>()
        var temp:List<CapableOfficial> = ArrayList<CapableOfficial>()

        map = getTypeData(str,type)
        tempList.add(map)
        map = HashMap<String, List<CapableOfficial>>()
        for (i in mDemoList){
            val key = ArrayList<String>(i.keys)[0]
            val value = i[key]?.toList()
            temp = returnListByType(str, type, value)
            if (temp.size != 0) {
                map.put("$key  $str", temp)
                tempList.add(map)
                map = HashMap<String, List<CapableOfficial>>()
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
}
