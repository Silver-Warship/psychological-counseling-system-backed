package org.example.psychologicalcounseling.dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private String seq;
    private int code;
    private String message;
    private T data;

    public Response(int code, String message, T data) {
        this.seq = null;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public String toJsonString() {
        if (seq == null) {
            return JSON.toJSONString(Map.of(
                "code", code,
                "codeMsg", message,
                "data", (data == null) ? "null" : data
            ));
        } else {
            return JSON.toJSONString(Map.of(
                "seq", seq,
                "code", code,
                "codeMsg", message,
                "data", (data == null) ? "null" : data
            ));
        }
    }
}
