package com.lanyang.framework.web.util;

import com.lanyang.framework.web.annotation.Logic;
import com.lanyang.framework.web.annotation.RequiresPermissions;
import com.lanyang.framework.web.annotation.RequiresRoles;
import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.exception.BizException;
import com.lanyang.framework.web.exception.NotPermissionException;
import com.lanyang.framework.web.exception.NotRoleException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author lanyang
 * @date 2025/4/15
 * @des
 */
public class AuthUtils {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 校验是否登录
     * @return
     */
    public static final LoginUser checkLogin(){
        String token = ServletUtils.getToken();
        if (token == null) {
            throw new BizException(401, "请登录");
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            throw new BizException(401, "请登录");
        }
        return loginUser;
    }

    /**
     * 校验是否有对应的角色
     * @param requiresRoles
     */
    public static final void checkRole(RequiresRoles requiresRoles){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            throw new BizException(401, "请登录");
        }

        if(CollectionUtils.isEmpty(loginUser.getRoles())){
            throw new BizException(401, "无权限访问");
        }
        // 校验角色
        if (requiresRoles.logical() == Logic.AND) {
            checkRoleAnd(loginUser.getRoles(), requiresRoles.value());
        } else {
            checkRoleOr(loginUser.getRoles(), requiresRoles.value());
        }

    }

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     *
     * @param roles 角色标识数组
     */
    private static void checkRoleAnd(Set<String> userAuth, String... roles) {
        for (String role : roles) {
            if (!hasRole(userAuth, role)) {
                throw new NotRoleException(role);
            }
        }
    }

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     *
     * @param roles 角色标识数组
     */
    private static void checkRoleOr(Set<String> userAuth, String... roles) {
        for (String role : roles) {
            if (hasRole(userAuth, role)) {
                return;
            }
        }
        if (roles.length > 0) {
            throw new NotRoleException(roles);
        }
    }

    /**
     * 判断是否包含角色
     *
     * @param roles 角色列表
     * @param role  角色
     * @return 用户是否具备某角色权限
     */
    private static boolean hasRole(Collection<String> roles, String role) {
        return roles.stream().filter(StringUtils::hasText)
                .anyMatch(x -> SUPER_ADMIN.contains(x) || PatternMatchUtils.simpleMatch(x, role));
    }

    /**
     * 校验是否有对应的权限码
     * @param requiresPermissions
     */
    public static final void checkPermissions(RequiresPermissions requiresPermissions){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            throw new BizException(401, "请登录");
        }

        if(CollectionUtils.isEmpty(loginUser.getPermissions())){
            throw new BizException(401, "无权限访问");
        }

        // 校验权限
        if (requiresPermissions.logical() == Logic.AND) {
            checkPermissionAnd(loginUser.getPermissions(), requiresPermissions.value());
        } else {
            checkPermissionOr(loginUser.getPermissions(), requiresPermissions.value());
        }
    }

    /**
     * 验证用户是否含有指定权限，必须全部拥有
     *
     * @param permissions 权限列表
     */
    private static void checkPermissionAnd(Set<String> userAuth, String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(userAuth, permission)) {
                throw new NotPermissionException(permission);
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param permissions 权限码数组
     */
    private static void checkPermissionOr(Set<String> userAuth, String... permissions) {
        for (String permission : permissions) {
            if (hasPermission(userAuth, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new NotPermissionException(permissions);
        }
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private static boolean hasPermission(Collection<String> authorities, String permission) {
        return authorities.stream().filter(StringUtils::hasText)
                .anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(x, permission));
    }
}
