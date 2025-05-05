package org.example.psychologicalcounseling.module.gpt.kimi;

import com.alibaba.fastjson.JSON;
import org.example.psychologicalcounseling.module.gpt.kimi.apiResponse.KimiConversation;
import org.example.psychologicalcounseling.module.gpt.kimi.apiResponse.KimiMessage;
import org.example.psychologicalcounseling.module.gpt.kimi.apiResponse.KimiResponse;
import org.example.psychologicalcounseling.utils.HttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class KimiUtil {
    private static final String API_KEY = "sk-kpZmG3a0pWQIkwc69L5sdgpCXgU2rtjcivkTVu3EO7uasOsI";
    private static final String CALL_WORD = "你是一名心理咨询师，你将引导用户并解答用户的问题，默认用户使用中文。";

    // record the history of messages in each session
    private static final Map<String, List<KimiMessage>> messagePool = new HashMap<>();

    /**
     * send a post request to the server
     * @param messages the messages to be sent
     * @return the response from the server
     */
    private static String post_request(List<KimiMessage> messages) {
        Map<String, String> header = Map.of("Content-Type", "application/json",
                "Authorization", "Bearer " + API_KEY);
        Map<String, ?> body = Map.of("model", "moonshot-v1-8k",
                "messages", messages, "temperature", 0.3);

        return HttpUtil.sendPost("https://api.moonshot.cn/v1/chat/completions", header, body);
    }

    /**
     * get initial messages for the first request
     * @return a list of messages
     */
    public static List<KimiMessage> getInitialMessages(){
        List<KimiMessage> messages = new Vector<>(10);
        messages.add(new KimiMessage("system", CALL_WORD));
        return messages;
    }

    /**
     * register a new session and get the session ID
     * @return the session ID
     */
    public static String registerSession() {
        List<KimiMessage> messages = getInitialMessages();

        // get response from server and parse it to get returned messages
        String strResponse = post_request(messages);
        KimiResponse mapResponse = JSON.parseObject(strResponse, KimiResponse.class);
        KimiConversation[] choices = mapResponse.getChoices();
        if (choices == null) {
            return null;
        }

        for (KimiConversation conversation : choices) {
            messages.add(conversation.getMessage());
        }

        // add message into session pool
        String sessionID = mapResponse.getId();
        messagePool.put(sessionID, messages);

        return sessionID;
    }

    /**
     * send a message to the server and get the response
     * @param sessionID the session ID
     * @param message the message to be sent
     * @return the response from the server
     */
    public static String chat(String sessionID, String message) {
        List<KimiMessage> messages = messagePool.get(sessionID);
        messages.add(new KimiMessage("user", message));

        String response = chat(messages);
        if (response == null) {
            messages.remove(messages.size() - 1);
        }
        messagePool.put(sessionID, messages);

        return response;
    }

    /**
     * send a message to the server and get the response
     * @param messages the messages to be sent
     * @return the response from the server
     */
    public static String chat(List<KimiMessage> messages) {
        String strResponse = post_request(messages);
        KimiResponse mapResponse = JSON.parseObject(strResponse, KimiResponse.class);
        KimiConversation[] choices = mapResponse.getChoices();
        if (choices == null) {
            messages.remove(messages.size() - 1);
            return null;
        }

        KimiMessage lastMessage = choices[choices.length - 1].getMessage();
        messages.add(lastMessage);

        return lastMessage.getContent();
    }

    /**
     * test the KimiUtil class
     */
    public static void test() {
        String session_id = registerSession();
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
