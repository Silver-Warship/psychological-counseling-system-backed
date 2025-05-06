package org.example.psychologicalcounseling.controller;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.psychologicalcounseling.module.getAll.GetAllResponse;
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

    /**
     * 模糊查询用户信息接口
     * @param role     用户角色
     *        id       用户id
     *        nickname 用户昵称
     * @return 用户信息列表
     */
    @GetMapping("/fuzzySearch")
    public ResponseEntity<?> fuzzySearch(@Param("id") Long id,@Param("role") String role,@Param("nickname") String nickname) {

        GetAllResponse getAllResponse = getAllService.fuzzySearch(id, role, nickname);

        return getAllResponse.buildResponse();
    }


}
