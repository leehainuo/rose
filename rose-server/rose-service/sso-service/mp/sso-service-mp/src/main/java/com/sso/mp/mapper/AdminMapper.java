package com.sso.mp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rose.sso.model.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
