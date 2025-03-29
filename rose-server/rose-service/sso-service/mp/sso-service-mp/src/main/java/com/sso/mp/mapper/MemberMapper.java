package com.sso.mp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rose.sso.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
