package org.example.psychologicalcounseling.module.chat.file;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.example.psychologicalcounseling.module.chat.file.transmitFile.TransmitFileRequest;
import org.example.psychologicalcounseling.module.chat.file.transmitFile.TransmitFileResponse;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class FileService {
    public Response<TransmitFileResponse> TransmitFile(TransmitFileRequest request) {
        if (sendFile(request)) {
            return new Response<>(ErrorConstant.successSendFile.code, ErrorConstant.successSendMessage.codeMsg, null);
        } else {
            return new Response<>(ErrorConstant.failSendFile.code, ErrorConstant.failSendFile.codeMsg, null);
        }
    }

    private boolean sendFile(FileDescription fileDescription) {
         // send to the receiver if the receiver is online
        ConnectionService connectionService = GetBeanUtil.getBean(ConnectionService.class);
        WebSocketSession session = connectionService.getConnection(fileDescription.getReceiverID());
        if (session == null || !session.isOpen()) {
            return false;
        }

        Response<FileDescription> newFileResponse = new Response<>(ErrorConstant.newFile.code,
                ErrorConstant.newFile.codeMsg, fileDescription);

        // convert the message to json string and send it to the receiver
        try{
            session.sendMessage(new TextMessage(newFileResponse.toJsonString()));
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
