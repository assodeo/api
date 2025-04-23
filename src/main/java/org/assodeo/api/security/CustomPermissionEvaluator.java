package org.assodeo.api.security;

import org.assodeo.api.entity.Association;
import org.assodeo.api.service.PermissionService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final PermissionService permissionService;

    public CustomPermissionEvaluator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (!(targetDomainObject instanceof Long associationId) || !(permission instanceof String permissionName)) {
            return false;
        }

        return hasPermission(authentication, permissionName, associationId);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (!(targetId instanceof Long associationId) || !targetType.equalsIgnoreCase(Association.class.getName()) || !(permission instanceof String permissionName)) {
            return false;
        }

        return hasPermission(authentication, permissionName, associationId);
    }

    private boolean hasPermission(Authentication authentication, String permission, Long associationId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Long userId = Long.parseLong(authentication.getName());

        return permissionService.hasPermission(associationId, userId, permission);
    }
}
