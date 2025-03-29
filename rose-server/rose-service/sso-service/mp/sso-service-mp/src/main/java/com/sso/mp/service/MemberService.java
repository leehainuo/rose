package com.sso.mp.service;

import com.rose.sso.model.dto.MemberLoginDTO;
import com.rose.sso.model.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
public interface MemberService extends IService<Member> {

    void login(MemberLoginDTO memberLoginDTO);

    void getTokenByRefreshToken(String refreshToken);
}
