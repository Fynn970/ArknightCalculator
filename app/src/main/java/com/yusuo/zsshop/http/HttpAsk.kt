package com.yusuo.zsshop.http

import com.yusuo.zsshop.pojo.CapableOfficial
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpAsk {
    companion object {
         fun getResponseBody(url: String): ArrayList<CapableOfficial> {
        val sb = StringBuffer()
            val urll = URL(url)
            val gcon =
                urll.openConnection() as HttpURLConnection
            val ips = gcon.inputStream
            val br = BufferedReader(InputStreamReader(ips))
            var i: String?
            while (br.readLine().also { i = it } != null) {
                sb.append(i)
            }
             var jsa = JSONArray(sb.toString())
             var data = ArrayList<CapableOfficial>()
            for (i in 0 until jsa.length()){
                var jo = jsa[i] as JSONObject
                var tags = ArrayList<String>()
                var jsonTags = jo["tags"] as JSONArray
                for (i in 0 until jsonTags.length()){
                    tags.add(jsonTags[i] as String)
                }
                data.add(
                    CapableOfficial(
                        jo["name"] as String,
                        jo["type"] as String,
                        jo["level"] as Int,
                        jo["sex"] as String,
                        tags,
                        jo["hidden"] as Boolean,
                        jo["name-en"] as String
                    )
                )
            }
             println(data)
        return data
    }
        fun getData() {


        }

        fun getResponse(url:String):String{
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val body = response.body.toString()
            return body
        }


//        suspend fun getResponseBody(url: String): String {
//            var result = ""
//            coroutineScope {
//                launch {
//                    val deferred = async {
//                        var data: String = null.toString()
//                        val okHttpClient = OkHttpClient()
//                        val userAgent =
//                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36"
//                        val request = Request.Builder()
//                            .url("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json")
//                            .addHeader("User-Agent", userAgent)
//                            .build()
//                        okHttpClient.newCall(request).enqueue(object : Callback {
//                            override fun onFailure(call: Call, e: IOException) {
//
//                                data = e.message.toString()
//                            }
//
//                            @Throws(IOException::class)
//                            override fun onResponse(call: Call, response: Response) {
//                                data = response.body!!.string()
//                            }
//                        })
//                        data
//                    }
//                    result = deferred.await()
//
//                }
//            }
//            return result
//        }
    }


}