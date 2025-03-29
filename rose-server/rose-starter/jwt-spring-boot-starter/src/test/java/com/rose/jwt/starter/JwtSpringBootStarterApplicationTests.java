package com.rose.jwt.starter;

import com.rose.jwt.starter.config.JwtProperties;
import com.rose.jwt.starter.dto.ClaimDTO;
import com.rose.jwt.starter.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@Data
@SpringBootTest
class JwtSpringBootStarterApplicationTests {

    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        log.info("key:{}",jwtProperties.getKey());
        log.info("accessTokenTtl:{}", jwtProperties.getAccessTokenTtl());
        log.info("refreshTokenTtl:{}", jwtProperties.getRefreshTokenTtl());
    }

    @Test
    void test() {
        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setMemberId("1");
        claimDTO.setRoleId("1");
        claimDTO.setType("access_token");
        String accessToken = jwtUtil.createAccessToken(claimDTO);
        log.info("accessToken:{}", accessToken);

    }

}
