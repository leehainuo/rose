package com.sso.mp;

import com.rose.jwt.starter.config.JwtProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class SsoServiceMpApplicationTests {

    @Resource
    JwtProperties jwtProperties;

    @Test
    void contextLoads() {
        log.info("key:{}", jwtProperties.getKey());
        log.info("accessTokenTtl:{}", jwtProperties.getAccessTokenTtl());
        log.info("refreshTokenTtl:{}", jwtProperties.getRefreshTokenTtl());

    }

}
