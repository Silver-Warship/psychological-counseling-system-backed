package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.param.Response;
import org.example.psychologicalcounseling.param.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.param.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.param.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.param.chat.TransmitMessageResponse;
import org.example.psychologicalcounseling.utils.HttpProxy;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class TencentIM implements ChatProxy {
    final String DOMAIN = "console.tim.qq.com";
    final String SDK_APPID = "1600073452";
    final String ADMIN_IDENTIFIER = "administrator";
    final String ADMIN_USERSIG = "eJwtjMsOwiAURP*FLabSFkSbuPERianGpBo3blDQXO0rgI9o-Hex7XLOzJwP2qZZ8NAGJSgKCOo1GZQuHZyhwVIVUIJ1RrrKdAOrbrKuQaEkHBBCeExZ1Db6VYPRnjPGIl*11EHxZ5ySOBxxyjsLXLx-M5-mb2xX*CSI0uvZ-ljcM3**pk*ai0MfT3ZiuLCx1MtqjL4-efM0Yw__";
    final String RANDOM = "99999999";
    final String CONTENT_TYPE = "json";

    // sample url : https://xxxxxx/v4/openim/sendmsg?sdkappid=88888888&identifier=admin&usersig=xxx&random=99999999&contenttype=json
    public String transmitMessage(Map<String, ?> request_json) {
        final String baseUrl = String.format("https://%s/v4/openim/sendmsg", DOMAIN);
        final String url = String.format("%s?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=%s",
                baseUrl, SDK_APPID, ADMIN_IDENTIFIER, ADMIN_USERSIG, RANDOM, CONTENT_TYPE);

        // make posted data
        JSONObject json = new JSONObject();
        json.put("From_Account", request_json.get("From_Account"));
        json.put("To_Account", request_json.get("To_Account"));
        // generate unsigned int as message random
        json.put("MsgRandom", Integer.toUnsignedLong(new Random().nextInt()));
        Map<String, ?>[] msg_body = new Map[]{Map.of("MsgType", "TIMTextElem",
                "MsgContent", Map.of("Text", request_json.get("Text")))};

        json.put("MsgBody", msg_body);

        String str_response = HttpProxy.sendPost(url, json.toMap());
        // check response and store message into database
        JSONObject json_response = new JSONObject(str_response);
        int error_code = json_response.getInt("ErrorCode");
        if (error_code == 0) {
            // store message into database
            System.out.print("store into database");
        } else {
            return new Response<String>(error_code, "error", "transmit message failed").toJsonString();
        }

        return new Response<String>(200, "success", "transmit message").toJsonString();
    }

    public String pullUnReceivedMessage(Map<String, ?> request_json) {
        return new Response<String>(200, "success", "pull unreceived message").toJsonString();
    }

    public static void main(String[] args) {
        TencentIM tencentIM = new TencentIM();
        Map<String, ?> request_json = Map.of("From_Account", "test1", "To_Account", "test2", "Text", "hello");
        tencentIM.transmitMessage(request_json);
    }

    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        return null;
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        return null;
    }
}
