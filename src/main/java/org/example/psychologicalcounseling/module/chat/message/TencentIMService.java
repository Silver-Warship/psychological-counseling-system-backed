package org.example.psychologicalcounseling.module.chat.message;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.model.Message;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.example.psychologicalcounseling.utils.HttpUtil;
import org.json.JSONObject;
import java.util.Map;
import java.util.Random;

public class TencentIMService implements MessageService {
    final String DOMAIN = "console.tim.qq.com";
    final String SDK_APPID = "1600073452";
    final String ADMIN_IDENTIFIER = "administrator";
    final String ADMIN_USERSIG = "eJwtjMsOwiAURP*FLabSFkSbuPERianGpBo3blDQXO0rgI9o-Hex7XLOzJwP2qZZ8NAGJSgKCOo1GZQuHZyhwVIVUIJ1RrrKdAOrbrKuQaEkHBBCeExZ1Db6VYPRnjPGIl*11EHxZ5ySOBxxyjsLXLx-M5-mb2xX*CSI0uvZ-ljcM3**pk*ai0MfT3ZiuLCx1MtqjL4-efM0Yw__";
    final String RANDOM = "99999999";
    final String CONTENT_TYPE = "json";
    private final MessageRepository messageRepository;

    public TencentIMService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * This method is used to send a message to a user using Tencent IM.
     * @param request The request containing the sender ID, receiver ID, and content.
     * @return A response indicating the success or failure of the operation.
     */
    @Override
    // sample url : https://xxxxxx/v4/openim/sendmsg?sdkappid=88888888&identifier=admin&usersig=xxx&random=99999999&contenttype=json
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        final String baseUrl = String.format("https://%s/v4/openim/sendmsg", DOMAIN);
        final String url = String.format("%s?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=%s",
                baseUrl, SDK_APPID, ADMIN_IDENTIFIER, ADMIN_USERSIG, RANDOM, CONTENT_TYPE);

        // make posted data
        JSONObject json = new JSONObject();
        json.put("From_Account", request.getSenderID());
        json.put("To_Account", request.getReceiverID());
        // generate unsigned int as message random
        json.put("MsgRandom", Integer.toUnsignedLong(new Random().nextInt()));
        Map<String, ?>[] msg_body = new Map[]{Map.of("MsgType", "TIMTextElem",
                "MsgContent", Map.of("Text", request.getContent()))};

        json.put("MsgBody", msg_body);

        String str_response = HttpUtil.sendPost(url, json.toMap());
        // check response and store message into database
        JSONObject json_response = new JSONObject(str_response);
        int error_code = json_response.getInt("ErrorCode");
        if (error_code == 0) {
            // store message into database
            Message message = new Message(request);
            messageRepository.save(message);
        } else {
            return new Response<>(error_code, "error", new TransmitMessageResponse());
        }

        return new Response<>(ErrorConstant.successSendMessage.code, ErrorConstant.successSendMessage.codeMsg, new TransmitMessageResponse());
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        return null;
    }

    @Override
    public Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request) {
        return null;
    }
}
