package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.consultant.addConsultantRecord.addConsultantRecordDto;
import org.example.psychologicalcounseling.module.consultant.exportConsultantRecord.ExportConsultantRecordResponse;
import org.example.psychologicalcounseling.module.consultant.getCompletedConsultationNumber.GetCompletedConsultationNumberRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.module.consultant.ConsultantRecordService;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationResponse;
import org.example.psychologicalcounseling.module.session.GetRunningSession.GetRunningSessionNumberResponse;
import org.example.psychologicalcounseling.repository.ConsultantRecordRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultantRecordController {
    private final ConsultantRecordService consultantRecordService;
    private final UserRepository userRepository;
    private final CounsellorRepository counsellorRepository;
    private final SessionRepository sessionRepository;
    private final ConsultantRecordRepository consultantRecordRepository;

    public ConsultantRecordController(ConsultantRecordService consultantRecordService, UserRepository userRepository,
                                      CounsellorRepository counsellorRepository, SessionRepository sessionRepository,
                                      ConsultantRecordRepository consultantRecordRepository) {
        this.consultantRecordService = consultantRecordService;
        this.userRepository = userRepository;
        this.counsellorRepository = counsellorRepository;
        this.sessionRepository = sessionRepository;
        this.consultantRecordRepository = consultantRecordRepository;
    }

    /**
     * 通过用户ID或咨询师ID获取一段时间内咨询记录，如果不声明时间段将获取所有咨询记录
     * @param request   所需参数
     * @return          获取到的咨询记录
     */
    @GetMapping("/api/getConsultantRecord")
    public ResponseEntity<?> getConsultantRecord(GetConsultantRecordRequest request) {
        GetConsultantRecordResponse response = new GetConsultantRecordResponse();
        // check if startTimestamp and endTimestamp are present
        if (request.getStartTimestamp() == null) {
            request.setStartTimestamp(0L);
        }
        if (request.getEndTimestamp() == null) {
            request.setEndTimestamp(System.currentTimeMillis());
        }

        var timeStampError = TimeStampUtil.timestampBetweenCheck(request.getStartTimestamp(), request.getEndTimestamp());
        if (timeStampError.isPresent()) {
            response.setCode(601);
            response.setCodeMsg(timeStampError.get());
            return response.buildResponse();
        }

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setConsultantRecords(consultantRecordService.getConsultantRecordByUser(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setConsultantRecords(consultantRecordService.getConsultantRecordByCounsellor(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }

    /**
     * 通过用户ID或咨询师ID获取一段时间内咨询记录的数量，如果不声明时间段将获取所有咨询记录
     * @param request   所需参数
     * @return          获取到的咨询记录数量
     */
    @GetMapping("/api/getCompletedConsultationNumber")
    public ResponseEntity<?> getCompletedConsultationNumber(GetCompletedConsultationNumberRequest request) {
        GetRunningSessionNumberResponse response = new GetRunningSessionNumberResponse();
        // check if startTimestamp and endTimestamp are present
        if (request.getStartTimestamp() == null) {
            request.setStartTimestamp(0L);
        }
        if (request.getEndTimestamp() == null) {
            request.setEndTimestamp(System.currentTimeMillis());
        }
        var timeStampError = TimeStampUtil.timestampBetweenCheck(request.getStartTimestamp(), request.getEndTimestamp());
        if (timeStampError.isPresent()) {
            response.setCode(601);
            response.setCodeMsg(timeStampError.get());
            return response.buildResponse();
        }

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setNumber(consultantRecordService.getCompletedSessionNumberByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setNumber(consultantRecordService.getCompletedSessionNumberByCounsellorID(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }

    /**
     * 获取一段时间内咨询时长
     * @param request   所需参数
     * @return          在该时间段内的咨询时长
     */
    @GetMapping("/api/getHistoricalConsultationDuration")
    public ResponseEntity<?> getHistoricalConsultationDuration(getConsultationDurationRequest request) {
        getConsultationDurationResponse response = new getConsultationDurationResponse();
        // check if startTimestamp and endTimestamp are present
        if (request.getStartTimestamp() == null) {
            request.setStartTimestamp(0L);
        }
        if (request.getEndTimestamp() == null) {
            request.setEndTimestamp(System.currentTimeMillis());
        }
        var timeStampError = TimeStampUtil.timestampBetweenCheck(request.getStartTimestamp(), request.getEndTimestamp());
        if (timeStampError.isPresent()) {
            response.setCode(601);
            response.setCodeMsg(timeStampError.get());
            return response.buildResponse();
        }

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setDurations(consultantRecordService.getHistoricalConsultationDurationByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setDurations(consultantRecordService.getHistoricalConsultationDurationByCounsellorID(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }


    /**
     * 添加咨询记录
     * @param request   所需参数
     * @return          添加结果
     */
    @PostMapping("/api/addConsultantRecord")
    ResponseEntity<?> addConsultantRecord(@RequestBody addConsultantRecordDto request) {
        // check if userID and counsellorID are valid
        if (request.getUserID() == null || !userRepository.existsById(request.getUserID())) {
            return ResponseEntity.badRequest().body("User ID is invalid");
        }
        if (request.getCounsellorID() == null || !counsellorRepository.existsById(request.getCounsellorID())) {
            return ResponseEntity.badRequest().body("Counsellor ID is invalid");
        }

        // check if sessionID is valid
        if (request.getSessionID() == null || !sessionRepository.existsById(request.getSessionID())) {
            return ResponseEntity.badRequest().body("Session ID is invalid");
        }

        // check if userRating is valid
        if (request.getUserRating() == null || request.getUserRating() < 0 || request.getUserRating() > 5) {
            return ResponseEntity.badRequest().body("User rating is invalid");
        }

        if (consultantRecordService.addConsultantRecord(request.getCounsellorID(), request.getUserID(), request.getSessionID(),
                request.getUserRating(), request.getAppraisal(), request.getCounsellorAppraisal())) {
            return ResponseEntity.ok("Consultant record added successfully");
        }

        return ResponseEntity.badRequest().body("Failed to add consultant record");
    }

    /**
     * 导出咨询记录
     * @param recordID  咨询记录ID
     * @return          导出结果
     */
    @GetMapping("/api/exportConsultantRecord")
    ResponseEntity<?> exportConsultantRecord(Long recordID) {
        // check if recordID is valid
        if (recordID == null || !consultantRecordRepository.existsById(recordID)) {
            return ResponseEntity.badRequest().body("Record ID is invalid");
        }

        ExportConsultantRecordResponse response = new ExportConsultantRecordResponse();
        response.content = consultantRecordService.exportConsultantRecord(recordID);
        return response.buildResponse();
    }
}
