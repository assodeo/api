package org.assodeo.api.security;

import org.assodeo.api.entity.Association;
import org.assodeo.api.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomPermissionEvaluatorTest {

    @Mock
    private PermissionService permissionService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomPermissionEvaluator customPermissionEvaluator;

    @Test
    void hasPermissionReturnsTrueWhenUserHasPermissionForDomainObject() {
        Long associationId = 1L;
        String permissionName = "READ";

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("1");
        when(permissionService.hasPermission(associationId, 1L, permissionName)).thenReturn(true);

        boolean result = customPermissionEvaluator.hasPermission(authentication, associationId, permissionName);

        assertTrue(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenUserDoesNotHavePermissionForDomainObject() {
        Long associationId = 1L;
        String permissionName = "WRITE";

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("1");
        when(permissionService.hasPermission(associationId, 1L, permissionName)).thenReturn(false);

        boolean result = customPermissionEvaluator.hasPermission(authentication, associationId, permissionName);

        assertFalse(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenAuthenticationIsNull() {
        boolean result = customPermissionEvaluator.hasPermission(null, 1L, "READ");

        assertFalse(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenAuthenticationIsNotAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);

        boolean result = customPermissionEvaluator.hasPermission(authentication, 1L, "READ");

        assertFalse(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenTargetDomainObjectIsInvalid() {
        boolean result = customPermissionEvaluator.hasPermission(authentication, "invalidObject", "READ");

        assertFalse(result);
    }

    @Test
    void hasPermissionReturnsTrueWhenUserHasPermissionForTargetIdAndType() {
        Long associationId = 1L;
        String permissionName = "READ";

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("1");
        when(permissionService.hasPermission(associationId, 1L, permissionName)).thenReturn(true);

        boolean result = customPermissionEvaluator.hasPermission(authentication, associationId, Association.class.getName(), permissionName);

        assertTrue(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenTargetIdIsInvalid() {
        boolean result = customPermissionEvaluator.hasPermission(authentication, "invalidId", Association.class.getName(), "READ");

        assertFalse(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenTargetTypeIsInvalid() {
        boolean result = customPermissionEvaluator.hasPermission(authentication, 1L, "InvalidType", "READ");

        assertFalse(result);
    }
}
