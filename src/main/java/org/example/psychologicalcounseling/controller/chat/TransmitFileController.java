package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.file.FileService;
import org.example.psychologicalcounseling.module.chat.file.transmitFile.TransmitFileRequest;
import org.example.psychologicalcounseling.module.chat.file.transmitFile.TransmitFileResponse;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.springframework.stereotype.Controller;

@Controller
public class TransmitFileController extends RequestHandler<TransmitFileRequest, TransmitFileResponse> {
    private final FileService fileService;
    private final AccountRepository accountRepository;

    public TransmitFileController(FileService fileService, AccountRepository accountRepository) {
        super(TransmitFileRequest.class, TransmitFileResponse.class);
        this.fileService = fileService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<TransmitFileResponse> handleRequest(TransmitFileRequest request) {
        // check whether the senderID and receiverID are valid
        if (request.getSenderID() == null || !accountRepository.existsById(request.getSenderID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg
                    + request.getSenderID(), null);
        }

        if (request.getReceiverID() == null || !accountRepository.existsById(request.getReceiverID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg
                    + request.getReceiverID(), null);
        }

        // check whether the fileData is valid
        if (request.getFileData() == null || request.getFileData().isEmpty()) {
            return new Response<>(ErrorConstant.illegalFileData.code, ErrorConstant.illegalFileData.codeMsg, null);
        }

        return fileService.TransmitFile(request);
    }
}
