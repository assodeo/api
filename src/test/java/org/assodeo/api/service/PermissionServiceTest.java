package org.assodeo.api.service;

import org.assodeo.api.entity.Membership;
import org.assodeo.api.entity.Permission;
import org.assodeo.api.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private MembershipService membershipService;

    @InjectMocks
    private PermissionService permissionService;

    @Test
    void hasPermissionReturnsTrueWhenUserHasPermission() {
        Long userId = 1L;
        Long associationId = 1L;
        String permissionName = "READ";

        Permission permission = new Permission();
        permission.setName(permissionName);

        Role role = new Role();
        role.setPermissions(Set.of(permission));

        Membership membership = new Membership();
        membership.setRoles(Set.of(role));

        when(membershipService.getMembership(userId, associationId)).thenReturn(membership);

        boolean result = permissionService.hasPermission(userId, associationId, permissionName);

        assertTrue(result);
    }

    @Test
    void hasPermissionReturnsFalseWhenUserDoesNotHavePermission() {
        Long userId = 1L;
        Long associationId = 1L;
        String permissionName = "WRITE";

        Permission permission = new Permission();
        permission.setName("READ");

        Role role = new Role();
        role.setPermissions(Set.of(permission));

        Membership membership = new Membership();
        membership.setRoles(Set.of(role));

        when(membershipService.getMembership(userId, associationId)).thenReturn(membership);

        boolean result = permissionService.hasPermission(userId, associationId, permissionName);

        assertFalse(result);
    }

    @Test
    void hasPermissionThrowsExceptionWhenNoMembershipFound() {
        Long userId = 1L;
        Long associationId = 1L;
        String permissionName = "READ";

        when(membershipService.getMembership(userId, associationId)).thenThrow(new RuntimeException("No membership found"));

        assertThrows(RuntimeException.class, () -> permissionService.hasPermission(userId, associationId, permissionName));
    }

    @Test
    void hasPermissionHandlesEmptyRolesGracefully() {
        Long userId = 1L;
        Long associationId = 1L;
        String permissionName = "READ";

        Membership membership = new Membership();
        membership.setRoles(Collections.emptySet());

        when(membershipService.getMembership(userId, associationId)).thenReturn(membership);

        boolean result = permissionService.hasPermission(userId, associationId, permissionName);

        assertFalse(result);
    }

    @Test
    void hasPermissionHandlesEmptyPermissionsGracefully() {
        Long userId = 1L;
        Long associationId = 1L;
        String permissionName = "READ";

        Role role = new Role();
        role.setPermissions(Collections.emptySet());

        Membership membership = new Membership();
        membership.setRoles(Set.of(role));

        when(membershipService.getMembership(userId, associationId)).thenReturn(membership);

        boolean result = permissionService.hasPermission(userId, associationId, permissionName);

        assertFalse(result);
    }
}
