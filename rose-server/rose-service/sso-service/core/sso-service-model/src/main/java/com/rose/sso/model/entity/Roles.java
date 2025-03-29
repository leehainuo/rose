package com.rose.sso.model.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lihainuo
 * @since 2025-02-24
 */
@Getter
@Setter
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;
}
