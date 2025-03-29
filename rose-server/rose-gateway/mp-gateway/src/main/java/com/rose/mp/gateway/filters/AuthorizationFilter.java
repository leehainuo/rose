package com.rose.mp.gateway.filters;

import com.rose.jwt.starter.utils.JwtUtil;
import com.rose.mp.gateway.config.WhitelistConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private WhitelistConfig whitelistConfig;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 白名单校验
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (whitelistConfig.getPaths().stream()
                .anyMatch(whitePath -> pathMatcher.match(whitePath, path))) {
            return chain.filter(exchange);
        }

        // 从请求头中获取accessMD5 -> Redis -> accessToken
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String accessMD5 = headers.getFirst("Authorization");
        log.info("AuthorizationFilter MD5Token:{}", accessMD5);
        String accessToken = redisTemplate.opsForHash().get("access_token", accessMD5).toString();

        // accessToken验证
        String validated = jwtUtil.validateToken(accessToken);
        if (validated.equals("Valid")) {
            // 验证通过网关放行
            return chain.filter(exchange);
        }
        else {
            // 验证不通过,通过refreshToken获取新的accessToken
            log.info("验证结果:{}", validated);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
