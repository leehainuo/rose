package com.sso.mp.service.impl;

import com.rose.sso.model.entity.Admin;
import com.sso.mp.mapper.AdminMapper;
import com.sso.mp.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
