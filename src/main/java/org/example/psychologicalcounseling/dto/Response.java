package org.example.psychologicalcounseling.dto;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class Response<T> {
    public int code;
    public String message;
    public T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String toJsonString() {
        return Map.of(
                "code", code,
                "message", message,
                "data", (data == null) ? "null" : JSON.toJSONString(data)
        ).toString();
    }
}
