package org.example.psychologicalcounseling.param;

import lombok.Getter;
import org.example.psychologicalcounseling.param.chat.PullUnReceivedMessageRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

// This class is used in the MessageHandler class
// 使用此类存储请求类型和该请求所需的参数以及处理请求的函数
// 调用handle方法处理请求
@Getter
public class Request {
    private final String requestType;
    private final Function<Map<String, ?>, String> handleFunction;
    private final String[] requireParams;

    public Request(String requestType, String[] requireParams, Function<Map<String, ?>, String> handleFunction) {
        this.requestType = requestType;
        this.handleFunction = handleFunction;
        this.requireParams = requireParams;
    }

    // params: request_json (Map) - 存储请求参数的
    public String handle(Map<String, ?> parameter) {
        return handleFunction.apply(parameter);
    }
}
