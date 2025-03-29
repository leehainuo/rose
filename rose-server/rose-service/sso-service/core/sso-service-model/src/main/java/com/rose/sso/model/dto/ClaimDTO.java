package com.rose.sso.model.dto;

import lombok.Data;

@Data
public class ClaimDTO {
    private String memberId;
    private String roleId;
    private String type;
}
