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

public class HttpUtil {
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
        HttpGet http_get = new HttpGet(url);

        return getResponse(http_get);
    }

    public static String sendGet(String url, Map<String, String> header) {
        HttpGet http_get = new HttpGet(url);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            http_get.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(http_get);
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
        HttpPost http_post = new HttpPost(url);
        http_post.setEntity(entity);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            http_post.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(http_post);
    }

    public static String sendPost(String url, JSONObject body) {
        StringEntity entity = new StringEntity(body.toString(), Consts.UTF_8);
        HttpPost http_post = new HttpPost(url);
        http_post.setEntity(entity);

        return getResponse(http_post);
    }
}

