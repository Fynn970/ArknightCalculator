package com.yusuo.zsshop

import android.util.Log
import com.yusuo.zsshop.`interface`.DealData
import com.yusuo.zsshop.pojo.CapableOfficial
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.internal.wait
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun main(){
//    var builder = OkHttpClient.Builder()
//        .readTimeout(5000, TimeUnit.MILLISECONDS)
//        .writeTimeout(5000, TimeUnit.MILLISECONDS)
//    var client: OkHttpClient = builder.build()
//
//    //注释1处
//    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        Log.d("demo", "coroutine: error ${throwable.message}")
//    }
//    val request = Request.Builder().url("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json").build()
//
//    GlobalScope.launch(exceptionHandler) {
//        val response = client.newCall(request).awaitResponse()
//        val str = getString(response)
//
//        print("协程请求 onResponse: $str")
//    }

//    runBlocking {
//
//        var a = requestData()
//        print(a)
//    }
//    print("aaa")
//    requestData()

//    GlobalScope.launch(Dispatchers.Default) {
//        getThreadName()
//    }
//    GlobalScope.launch {
//
//        val requestData = async {  requestData("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json")}.await()
//        println(requestData)
//    }

//    GlobalScope.launch {
//        async {
//            println(commonHttp("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json"))
//        }.await()
//
//
//    }

    var a = ArrayList<String>()
    a.add("能天使")
    a.add("莫斯提吗")
    a.add("星熊")
    a.add("能天使")
    a.add("莫斯提吗")
    a.add("星熊")
    a.add("能天使")
    a.add("莫斯提吗")
    a.add("星熊")
    a.add("能天使")
    a.add("莫斯提吗")
    a.add("星熊")
    var c = ArrayList<String>()
     c = a.filter { it.equals("星熊")&& !c.contains("星熊") } as ArrayList<String>
    var set = HashSet<String>(c)
    c.clear()
    c.addAll(set)
    c.forEach(::print)

}

//fun getdata(list: ArrayList<String>, str:String, dd:DealData<String>){
//    var a = ArrayList<String>()
//    for (i in list){
//        if (dd.equals(str)){
//            a.add(i)
//        }
//    }
//    return a
//}

suspend fun commonHttp(url:String): String  = withContext(Dispatchers.IO){
    var sb = StringBuffer()
    var url = URL(url)
    var con = url.openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    con.responseCode
    var ins = con.inputStream
    var reader = BufferedReader(InputStreamReader(ins))
    var i: String? = null
    while(({i = reader.readLine()}) != null) {
        sb.append(i)
    }
    println("sadcaq")
    reader.close()
    ins.close()
    sb.toString()


//    var resultBuffer = StringBuffer()
//    var httpUrlConnection = URL(url).openConnection() as HttpURLConnection
//    httpUrlConnection.setRequestProperty("Accept-Charset", "utf-8")
//    httpUrlConnection.setRequestProperty("Content-Type", "application/x-www/for-urlencoded")
//    if (httpUrlConnection.responseCode >= 300) throw Exception("HTTP Request is not success, Response code is ${httpUrlConnection.responseCode}")
//    var inputStream = httpUrlConnection.inputStream
//    var inputStreamReader = InputStreamReader(inputStream, "utf-8")
//    val reader = BufferedReader(inputStreamReader)
//    reader.use {
//        var temp = it.readLine()
//        if (temp != null){
//            resultBuffer.append(temp)
//        }
//    }
//    reader.close()
//    inputStreamReader.close()
//    inputStream.close()
//    println(resultBuffer.toString())
}


suspend fun getThreadName() = withContext(Dispatchers.IO){
    println("Thread Name = ${Thread.currentThread().name}")
}

//private suspend fun getString(response: Response): String {
//    return withContext(Dispatchers.IO) {
//        response.body?.string() ?: "empty string"
//    }
//}
suspend fun requestData(url: String) = withContext(Dispatchers.IO) {
        var resultBuffer = StringBuffer()
        var httpUrlConnection = URL(url).openConnection() as HttpURLConnection
        httpUrlConnection.setRequestProperty("Accept-Charset", "utf-8")
        httpUrlConnection.setRequestProperty("Content-Type", "application/x-www/for-urlencoded")
        if (httpUrlConnection.responseCode >= 300) throw Exception("HTTP Request is not success, Response code is ${httpUrlConnection.responseCode}")
        var inputStream = httpUrlConnection.inputStream
        var inputStreamReader = InputStreamReader(inputStream, "utf-8")
        val reader = BufferedReader(inputStreamReader)
        reader.use {
            var temp = it.readLine()
            if (temp != null){
                resultBuffer.append(temp)
            }
        }
        reader.close()
        inputStreamReader.close()
        inputStream.close()
        resultBuffer.toString()


}
 private suspend fun getMessage():String{
     var builder = OkHttpClient.Builder()
         .readTimeout(5000, TimeUnit.MILLISECONDS)
         .writeTimeout(10000, TimeUnit.MILLISECONDS)
     var client = builder.build()
     val request = Request.Builder().url("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json").build()
     var result: String = ""

            val response = client.newCall(request).awaitResponse()
            result = response.body.toString()

     return result
 }

suspend fun okhttp3.Call.awaitResponse(): okhttp3.Response {

    return suspendCancellableCoroutine {

        it.invokeOnCancellation {
            //当协程被取消的时候，取消网络请求
            cancel()
        }

        enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                it.resume(response)
            }
        })
    }
}