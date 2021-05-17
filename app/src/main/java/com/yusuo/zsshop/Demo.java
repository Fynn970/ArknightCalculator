package com.yusuo.zsshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getResponseBody("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json");
//            }
//        });
//        thread.start();
//
//
//        List<Map<String, List<String>>> a = new ArrayList<>();
//        Map<String, List<String>> map = new HashMap<>();
//        List<String> aa = new ArrayList<>();
//        aa.add("mcnvb");
//        aa.add("qyhtr");
//        aa.add("ry");
//        map.put("asd",aa);
//        map.put("zxc",aa);
//        map.put("qwe",aa);
//        map.put("ewrt",aa);
//        a.add(map);
//        System.out.println(a.get(0).keySet().toArray()[0]);
        System.out.println(getResponseBody("https://www.bigfun.cn/static/aktools/1618472186/data/akhr.json"));

    }

    private static String getResponseBody(String url){
        StringBuffer sb = new StringBuffer();
        try {
            URL urll = new URL(url);
            HttpURLConnection gcon = (HttpURLConnection) urll.openConnection();
            InputStream is = gcon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String i;
            while ((i = br.readLine()) != null){
                sb.append(i);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<Person> test(List<Person> list, TestLam<Person> personTestLam){
        List<Person> result = new ArrayList<>();
        for (Person p :
                list) {
            if (personTestLam.aaa(p)){
                result.add(p);
            }
        }
        return result;
    }
}
