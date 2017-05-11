package com.piedra.platease.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

/**
 * @author webinglin
 * @since 2017-05-10
 */
public class CaseSensitiveWildcardPermissionResolver  extends WildcardPermissionResolver {

    /**
     * Resolves a Permission based on the given String representation.
     *
     * @param permissionString the String representation of a permission.
     * @return A Permission object that can be used internally to determine a subject's permissions.
     * @throws InvalidPermissionStringException if the permission string is not valid for this resolver.
     */
    @Override
    public Permission resolvePermission(String permissionString) {
        return new WildcardPermission(permissionString, true);
    }
}
