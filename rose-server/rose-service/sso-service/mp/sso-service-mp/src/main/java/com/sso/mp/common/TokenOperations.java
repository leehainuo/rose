package com.sso.mp.common;


import com.sso.mp.enums.TokenType;
import com.sso.mp.utils.MD5Util;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.rose.jwt.starter.dto.ClaimDTO;
import com.rose.jwt.starter.utils.JwtUtil;

@Slf4j
@Component
public class TokenOperations {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private MD5Util md5Util;
    @Resource
    private RedisTemplate redisTemplate;

    public void processToken (ClaimDTO claimDTO, TokenType type) {

        // 生成Token
        String token = type.equals(TokenType.ACCESS_TOKEN)
                ? jwtUtil.createAccessToken(claimDTO)
                : jwtUtil.createRefreshToken(claimDTO);
        token = "Bearer " + token;

        // MD5处理
        String md5 = md5Util.getMD5(token);

        // 存入Redis
        redisTemplate.opsForHash().put(type.getRedisKey(), md5, token);

        // 日志
        log.info("Token: {}, MD5: {}", token, md5);
    }

}
