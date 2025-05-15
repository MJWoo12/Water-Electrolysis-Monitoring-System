package com.h2net.h2monitoringweb.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public ResponseEntity <List<Map<String, Object>>> NoAuthUserList(){
        List<Map<String, Object>> selectUserList = adminService.noAuthUserList();
        if(selectUserList != null && !selectUserList.isEmpty()){
            return ResponseEntity.ok(selectUserList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/updateAuth")
    public ResponseEntity<String> updateUserAuth(@RequestBody AdminVO vo){
        try {
            int updatedRows = adminService.updateUserAuth(vo);

            if(updatedRows > 0){
                return ResponseEntity.ok("권한 수정 완료");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 유저를 찾을 수 없거나 이미 수정된 권한입니다.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 통신 오류");
        }

    }
}
