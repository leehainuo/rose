package com.sso.mp.controller;


import com.rose.jwt.starter.config.JwtProperties;
import com.rose.sso.model.dto.MemberLoginDTO;
import com.sso.mp.service.MemberService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.rose.jwt.starter.dto.ClaimDTO;
import com.rose.jwt.starter.utils.JwtUtil;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    // 注入Service
    @Resource
    private MemberService memberService;
    @Resource
    private JwtProperties jwtProperties;

    @PostMapping("/test")
    public String test() {
        String key = jwtProperties.getKey();
        log.info("key:{}", key);
        return key;
    }

    @PostMapping("/login")
    public void login(@RequestBody MemberLoginDTO memberLoginDTO) {
        memberService.login(memberLoginDTO);
    }

    @PostMapping("/refresh/{refreshToken}")
    public void tokenRefresh(@PathVariable String refreshToken) {
        memberService.getTokenByRefreshToken(refreshToken);
    }

    @PostMapping("/validate/{refreshToken}")
    public void tokenValidate(@PathVariable String refreshToken) {

    }
}