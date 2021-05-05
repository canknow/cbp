package com.canknow.cbp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

    public static String get(String url, Map<String, String> params, Map<String, String> header) throws Exception {
        HttpGet httpGet = null;
        String body = "";

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            List<String> mapList = new ArrayList<>();

            if (params != null) {
                for (Entry<String, String> entry : params.entrySet()) {
                    mapList.add(entry.getKey() + "=" + entry.getValue());
                }
            }
            if (CollectionUtils.isNotEmpty(mapList)) {
                url = url + "?";
                String paramsString = StringUtils.join(mapList, "&");
                url = url + paramsString;
            }
            httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            if (header != null) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("请求失败");
            }
            else {
                body = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return body;
    }

    public static String post(String url, Map<String, String> data) throws Exception {
        return post(url, JsonUtils.mapToJson(data), null);
    }

    public static String post(String url, Map<String, String> data, Map<String, String> header) throws Exception {
        return post(url, JsonUtils.mapToJson(data), header);
    }

    public static String post(String url, String json, Map<String, String> header) throws Exception {
        HttpPost httpPost = null;
        String body = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            if (header != null) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity(), "UTF-8");

            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException(body);
            }
        }
        finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return body;
    }

    public static String postForm(String url, Map<String, String> params, Map<String, String> header) throws Exception {
        HttpPost httpPost = null;
        String body = "";

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            if (header != null) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (params != null) {
                for (Entry<String, String> entry : params.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("请求失败");
            }
            else {
                body = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return body;
    }

    public static void main(String[] args) throws Exception {
        Map data = new HashMap();
        data.put("loginToken", "333");

        //首先获取token
        String response = post("https://api.verification.jpush.cn/v1/web/loginTokenVerify", data, null);
        //如果返回的结果是list形式的，需要使用JSONObject.parseArray转换
        //List<Result> list = JSONObject.parseArray(response, Result.class);
        System.out.println(response);
    }
}