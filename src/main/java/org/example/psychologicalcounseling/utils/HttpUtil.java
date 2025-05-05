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

    /**
     * This method is used to send a GET or POST request and get the response.
     * @param request The HttpRequestBase object representing the request.
     * @return The response as a String.
     */
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

    /**
     * This method is used to send a GET request and get the response.
     * @param url The URL to send the request to.
     * @return The response as a String.
     */
    public static String sendGet(String url) {
        HttpGet http_get = new HttpGet(url);

        return getResponse(http_get);
    }

    /**
     * This method is used to send a GET request with headers and get the response.
     * @param url The URL to send the request to.
     * @param header The headers to include in the request.
     * @return The response as a String.
     */
    public static String sendGet(String url, Map<String, String> header) {
        HttpGet http_get = new HttpGet(url);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            http_get.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(http_get);
    }

    /**
     * This method is used to send a POST request with headers and a JSON body, and get the response.
     * @param url The URL to send the request to.
     * @param params The parameters to include in the request body.
     * @return The response as a String.
     */
    public static String sendPost(String url, Map<String, ?> params) {
        JSONObject body = new JSONObject();
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            body.put(entry.getKey().toString(), entry.getValue());
        }

        return sendPost(url, body);
    }

    /**
     * This method is used to send a POST request with headers and a JSON body, and get the response.
     * @param url The URL to send the request to.
     * @param header The headers to include in the request.
     * @param params The parameters to include in the request body.
     * @return The response as a String.
     */
    public static String sendPost(String url, Map<String, String> header, Map<String, ?> params) {
        JSONObject body = new JSONObject();
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            body.put(entry.getKey().toString(), entry.getValue());
        }

        return sendPost(url, header, body);
    }

    /**
     * This method is used to send a POST request with headers and a JSON body, and get the response.
     * @param url The URL to send the request to.
     * @param header The headers to include in the request.
     * @param body The JSON body to include in the request.
     * @return The response as a String.
     */
    public static String sendPost(String url, Map<String, String> header, JSONObject body) {
        StringEntity entity = new StringEntity(body.toString(), Consts.UTF_8);
        HttpPost http_post = new HttpPost(url);
        http_post.setEntity(entity);
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            http_post.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        return getResponse(http_post);
    }

    /**
     * This method is used to send a POST request with a JSON body, and get the response.
     * @param url The URL to send the request to.
     * @param body The JSON body to include in the request.
     * @return The response as a String.
     */
    public static String sendPost(String url, JSONObject body) {
        StringEntity entity = new StringEntity(body.toString(), Consts.UTF_8);
        HttpPost http_post = new HttpPost(url);
        http_post.setEntity(entity);

        return getResponse(http_post);
    }
}

