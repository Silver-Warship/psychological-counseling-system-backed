package org.example.psychologicalcounseling.param;

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
        Map<String, Object> response = Map.of(
            "code", code,
            "message", message,
            "data", data
        );

        return response.toString();
    }
}
