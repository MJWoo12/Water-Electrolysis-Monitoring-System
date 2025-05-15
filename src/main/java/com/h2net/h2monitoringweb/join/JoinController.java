package com.h2net.h2mornitoringweb.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/join")
public class JoinController {
    @Autowired
    private JoinService joinService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> checkUsedId (@PathVariable String userId) {
        try{
            boolean checkId = joinService.checkUsedId(userId);
            if(checkId) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복된 아이디입니다.");
            } else{
                return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 접근입니다.");
        }
    }

    @PostMapping("/insertUser")
    public ResponseEntity<String> insertUser (@RequestBody JoinVo joinVo) throws Exception{
        try{
            boolean success = joinService.insertUser(joinVo);
            if (success) {
                return ResponseEntity.status(HttpStatus.OK).body("가입 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("가입 실패");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 접근입니다.");
        }
    }
}
