package com.rose.jwt.starter.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import com.rose.jwt.starter.config.JwtProperties;
import com.rose.jwt.starter.dto.ClaimDTO;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author lihainuo
 */

public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Claims changeToClaims(ClaimDTO claimDTO){
        Claims claims=Jwts.claims();
        // claims.putAll(map);
        claims.put("memberId", claimDTO.getMemberId());
        claims.put("roleId", claimDTO.getRoleId());
        claims.put("type", claimDTO.getType());
        return claims;
    }

    public String createAccessToken(ClaimDTO claimDTO){
        return Jwts.builder()
                .setClaims(changeToClaims(claimDTO))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenTtl()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken(ClaimDTO claimDTO){
        return Jwts.builder()
                .setClaims(changeToClaims(claimDTO))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenTtl()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 校验token
     * @param token token
     * @return 校验结果
     * Valid-合法
     * Expired-过期
     * Invalid-无效
     * Malformed-结构不符合标准
     * Invalid Signature-签名无效
     */
    public String validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return "Valid";
        } catch (ExpiredJwtException e) {
            return "Expired";
        } catch (SignatureException e) {
            return "Invalid Signature";
        } catch (MalformedJwtException e) {
            return "Malformed";
        } catch (Exception e) {
            return "Invalid";
        }
    }


    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

}