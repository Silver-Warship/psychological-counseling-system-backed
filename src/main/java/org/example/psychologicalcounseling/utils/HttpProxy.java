package org.example.psychologicalcounseling.utils;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class HttpProxy {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    private synchronized static String getResponse(HttpRequestBase request) {
        try {
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String sendGet(String url) {
        HttpGet httpGet = new HttpGet(url);

        return getResponse(httpGet);
    }

    public static String sendGet(String url, Map<String, String> header) {
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            httpGet.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(httpGet);
    }
    
    public static String sendPost(String url, Map<String, ?> params) {
        JSONObject body = new JSONObject();
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            body.put(entry.getKey().toString(), entry.getValue());
        }

        return sendPost(url, body);
    }

    public static String sendPost(String url, Map<String, String> header, Map<String, ?> params) {
        JSONObject body = new JSONObject();
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            body.put(entry.getKey().toString(), entry.getValue());
        }

        return sendPost(url, header, body);
    }

    public static String sendPost(String url, Map<String, String> header, JSONObject body) {
        StringEntity entity = new StringEntity(body.toString(), Consts.UTF_8);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            httpPost.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(httpPost);
    }

    public static String sendPost(String url, JSONObject body) {
        StringEntity entity = new StringEntity(body.toString(), Consts.UTF_8);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);

        return getResponse(httpPost);
    }
}

