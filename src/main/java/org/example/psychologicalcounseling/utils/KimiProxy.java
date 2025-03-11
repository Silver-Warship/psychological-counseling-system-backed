package org.example.psychologicalcounseling.utils;

import com.alibaba.fastjson.JSON;
import org.example.psychologicalcounseling.dto.kimi.KimiConversation;
import org.example.psychologicalcounseling.dto.kimi.KimiMessage;
import org.example.psychologicalcounseling.dto.kimi.KimiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class KimiProxy {
    private static final String API_KEY = "sk-kpZmG3a0pWQIkwc69L5sdgpCXgU2rtjcivkTVu3EO7uasOsI";
    private static final String CALL_WORD = "你是一名心理咨询师，你将引导用户并解答用户的问题，默认用户使用中文。";
    private static final String ERROR_WORD = "服务器繁忙，请稍后再试。";
    private static final Map<String, List<KimiMessage>> session_pool = new HashMap<>();

    private static String post_request(List<KimiMessage> messages) {
        Map<String, String> header = Map.of("Content-Type", "application/json",
                "Authorization", "Bearer " + API_KEY);
        Map<String, ?> body = Map.of("model", "moonshot-v1-8k",
                "messages", messages, "temperature", 0.3);

        return HttpProxy.sendPost("https://api.moonshot.cn/v1/chat/completions", header, body);
    }

    public static String register_session() {
        List<KimiMessage> messages = new Vector<>(10);
        messages.add(new KimiMessage("system", CALL_WORD));

        // get response from server and parse it to get returned messages
        String str_response = post_request(messages);
        KimiResponse map_response = JSON.parseObject(str_response, KimiResponse.class);
        KimiConversation[] choices = map_response.getChoices();
        if (choices == null) {
            return ERROR_WORD;
        }

        for (KimiConversation conversation : choices) {
            messages.add(conversation.getMessage());
        }

        // add message into session pool
        String session_id = map_response.getId();
        session_pool.put(session_id, messages);

        return session_id;
    }

    public static String chat(String session_id, String message) {
        List<KimiMessage> messages = session_pool.get(session_id);
        messages.add(new KimiMessage("user", message));

        String str_response = post_request(messages);
        KimiResponse map_response = JSON.parseObject(str_response, KimiResponse.class);
        KimiConversation[] choices = map_response.getChoices();
        if (choices == null) {
            messages.remove(messages.size() - 1);
            return ERROR_WORD;
        }

        KimiMessage last_message = choices[choices.length - 1].getMessage();
        messages.add(last_message);
        session_pool.put(session_id, messages);

        return last_message.getContent();
    }

    public static void test() {
        String session_id = register_session();
        while (true) {
            System.out.print("You: ");
            String message = System.console().readLine();
            if (message.equals("exit")) {
                break;
            }
            String response = chat(session_id, message);
            System.out.println("Kimi: " + response);
        }
    }

    public static void main(String[] args) {
        test();
    }
}
