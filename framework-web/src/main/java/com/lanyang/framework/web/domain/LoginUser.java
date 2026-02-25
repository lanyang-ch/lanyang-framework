package com.lanyang.framework.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author lanyang
 * @date 2025/11/19
 * @des
 */
@Data
@ApiModel("登录用户信息")
public class LoginUser {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "缓存key")
    private String userKey;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String realName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别（0-未知 1-男 2-女）")
    private Integer sex;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "用户类型 0-普通租户用户 1-租户管理员")
    private Integer userType;

    @ApiModelProperty(value = "帐号状态（1正常 0停用）")
    private Integer status;

    @ApiModelProperty(value = "角色集合")
    private Set<String> roles;

    @ApiModelProperty(value = "权限码集合")
    private Set<String> permissions;

    @ApiModelProperty(value = "数据权限列表")
    private List<DataAuth> dataAuthList;

    @ApiModelProperty(value = "是否超级管理员")
    private Boolean superAdmin;

    @ApiModelProperty(value = "是否租户管理员")
    private Boolean tenantAdmin;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

}
