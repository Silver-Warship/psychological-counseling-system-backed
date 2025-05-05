package org.example.psychologicalcounseling.controller;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.psychologicalcounseling.module.getAll.GetAllService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetAll {
    private final GetAllService getAllService;

    public GetAll(GetAllService getAllService) {
        this.getAllService = getAllService;
    }

    /**
     * 获取所有用户信息接口
     * @param role 用户角色
     * @return 用户信息列表
     */
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@Param("role") String role) {
        return getAllService.getAll(role).buildResponse();
    }
}
