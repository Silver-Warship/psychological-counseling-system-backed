package org.example.psychologicalcounseling.dto;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Field;

@Getter
public class RequestHandler<T, V> {
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


    // declare the method handleRequest
    // only one of the two handleRequest methods will be used
    // depending on the subclass
    //  must override this method
    public Response<V> handleRequest(T request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Response<V> handleRequest(T request, WebSocketSession session) {
        return handleRequest(request);
    }
}
