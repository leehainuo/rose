package com.sso.mp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.rose.sso.model.dto.MemberLoginDTO;
import com.rose.sso.model.entity.Member;
import com.sso.mp.common.TokenOperations;
import com.sso.mp.enums.TokenType;
import com.sso.mp.mapper.MemberMapper;
import com.sso.mp.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.rose.jwt.starter.dto.ClaimDTO;
import com.rose.jwt.starter.utils.JwtUtil;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private TokenOperations tokenOperations;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private WxMaService wxMaService;

    @Override
    public void login(MemberLoginDTO memberLoginDTO) {

        String openId = "";

        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(memberLoginDTO.getCode());
            openId = sessionInfo.getOpenid();
            log.info("openId:{}", openId);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setMemberId("1001");claimDTO.setRoleId("1001");
        /**
         * @function processToken
         * 用于生成Token并MD5加密存入Redis
         */
        claimDTO.setType(TokenType.ACCESS_TOKEN.getRedisKey());
        tokenOperations.processToken(claimDTO, TokenType.ACCESS_TOKEN);
        claimDTO.setType(TokenType.REFRESH_TOKEN.getRedisKey());
        tokenOperations.processToken(claimDTO, TokenType.REFRESH_TOKEN);
    }

    @Override
    public void getTokenByRefreshToken(String md5) {
        String refreshToken = redisTemplate.opsForHash()
                .get(TokenType.REFRESH_TOKEN.getRedisKey(), md5)
                .toString();
        String result = jwtUtil.validateToken(refreshToken);
        if (!result.equals("valid")) {
            throw new RuntimeException("refreshToken无效");
        }
        // 刷新Token

    }
}
