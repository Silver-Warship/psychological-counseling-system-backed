package org.example.psychologicalcounseling.controller;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.psychologicalcounseling.module.getAll.GetAllService;
import org.example.psychologicalcounseling.module.user.info.EditRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetAll {

    @Autowired
    GetAllService getAllService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@Param("role") String role) {

        return getAllService.getAll(role).buildResponse();
    }
}
