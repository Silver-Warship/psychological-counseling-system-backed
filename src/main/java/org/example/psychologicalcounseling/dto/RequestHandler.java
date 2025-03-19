package org.example.psychologicalcounseling.dto;
import lombok.Getter;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageResponse;

import java.lang.reflect.Field;

@Getter
public abstract class RequestHandler<T, V> {
    // requestParamClass: the class of the request parameter
    // responseDataClass: the class of the response data
    private final Class<T> requestParamClass;
    private final Class<V> responseDataClass;

    // the subclass must call this constructor to set the requestParamClass and responseDataClass
    public RequestHandler(Class<T> requestParamClass, Class<V> responseClass) {
        this.requestParamClass = requestParamClass;
        this.responseDataClass = responseClass;
    }

    // return the request parameters must occur in user's input
    public String[] requestParams() {
        // get all attributes of requestParamClass
        // and return string array of all attributes
        Field[] fields = requestParamClass.getDeclaredFields();
        String[] attributes = new String[fields.length];
        for (int i = 0; i < requestParamClass.getDeclaredFields().length; i++) {
            attributes[i] = requestParamClass.getDeclaredFields()[i].getName();
        }

        return attributes;
    }

    public abstract Response<V> handleRequest(T request);
}
