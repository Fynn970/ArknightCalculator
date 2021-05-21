package com.yusuo.arknightcalculator


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
import com.yusuo.zsshop.base.BaseActivity
import com.yusuo.zsshop.base.BasePresenter
import com.yusuo.zsshop.contract.MainContract
import com.yusuo.zsshop.http.HttpAsk
import com.yusuo.zsshop.layout.FlowLayout
import com.yusuo.zsshop.pojo.CapableOfficial
import com.yusuo.zsshop.presenter.MainPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class MainActivity : BaseActivity(), MainContract.MainView {

    private lateinit var mMainPresenter: BasePresenter
    private lateinit var recycleAdapter:RecycleAdapter
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initView() {
        val ziZhiAdapter = FlowAdapter(this, mZiZhiList)
        fv_zizhi.setAdapter(ziZhiAdapter)

        val weiZhiAdapter = FlowAdapter(this, mWeiZhiList)
        fv_weizhi.setAdapter(weiZhiAdapter)


        val zhongLeiAdapter = FlowAdapter(this, mZhongLeiList)
        fv_zhonglei.setAdapter(zhongLeiAdapter)


        val ciZhuiAdapter = FlowAdapter(this, mCiZhuiList)
        fv_cizhui.setAdapter(ciZhuiAdapter)


        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_recycledata.layoutManager = llm
        recycleAdapter = RecycleAdapter(this,mDemoList)
        rv_recycledata.adapter = recycleAdapter


        ziZhiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                mMainPresenter.onZiZhiFlowClickListener(str, isSelectedTag(str, view))
            }
        })

        weiZhiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                if (selectedCount == 6){
                    toastError("最多6个标签哦！")
                    return
                }
                mMainPresenter.onWeiZhiFlowClickListener(str,isSelectedTag(str,view))
            }
        })

        zhongLeiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                if (selectedCount == 6){
                    toastError("最多6个标签哦！")
                    return
                }
                mMainPresenter.onZhongLeiFlowClickListener(str,isSelectedTag(str,view))
            }

        })


        ciZhuiAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, str: String, position: Int) {
                if (selectedCount == 6){
                    toastError("最多6个标签哦！")
                    return
                }
                mMainPresenter.onCiZhuiFlowClickListener(str,isSelectedTag(str,view))
            }
        })
    }

    private fun isSelectedTag(str:String, view:View):Boolean{
        val isSelected: Boolean
        if (mSelectedStrList.contains(str)){
            isSelected = true
            view.setBackgroundResource(R.drawable.tag_normal)
            mSelectedStrList.remove(str)
            selectedCount--
        }else{
            isSelected = false
            view.setBackgroundResource(R.drawable.tag_selected)
            mSelectedStrList.add(str)
            selectedCount++
        }
        return isSelected
    }

    private fun initData() {
        mMainPresenter = MainPresenterImpl(this)
    }

    override fun setNewData(data: ArrayList<Map<String, List<CapableOfficial>>>) {
        mDemoList.clear()
        mDemoList.addAll(data)
        recycleAdapter.notifyDataSetChanged()
    }

    override fun toastError(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        mMainPresenter.cancelCoroutineSocope()
        super.onDestroy()
    }

}

