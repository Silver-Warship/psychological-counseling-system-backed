package org.example.psychologicalcounseling.param;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

// This class is used in the MessageHandler class
// 使用此类存储请求类型和该请求所需的参数以及处理请求的函数
// 调用handle方法处理请求
public class Request {
    @Getter
    private final String request_type;
    private final String[] require_params;
    private final Function<Map<String, ?>, String> handle_function;

    public Request(String requestType, String[] require_params, Function<Map<String, ?>, String> handle_function) {
        this.request_type = requestType;
        this.require_params = require_params;
        this.handle_function = handle_function;
    }

    public String[] getRequireParams() {
        return require_params;
    }

    // params: request_json (Map) - 存储请求参数的
    public String handle(Map<String, ?> request_json) {
        return handle_function.apply(request_json);
    }
}
