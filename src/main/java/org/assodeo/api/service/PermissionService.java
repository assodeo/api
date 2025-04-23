package org.assodeo.api.service;

import org.assodeo.api.entity.Membership;
import org.assodeo.api.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final MembershipService membershipService;

    public PermissionService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * Checks if a user has a specific permission in a specific association.
     *
     * @param userId        the id of the user
     * @param associationId the id of the association
     * @param permission    the name of the permission to check
     * @return {@code true} if the user has the permission, {@code false} otherwise
     */
    public boolean hasPermission(Long userId, Long associationId, String permission) {
        return getUserPermissions(userId, associationId)
                .stream()
                .map(Permission::getName)
                .anyMatch(p -> p.equalsIgnoreCase(permission));
    }

    /**
     * Retrieves the permissions of a user in a specific association.
     *
     * @param userId        the id of the user
     * @param associationId the id of the association
     * @return a collection of permissions granted to the user in the association
     * @throws RuntimeException if no membership is found for the user in the association
     */
    private Collection<Permission> getUserPermissions(Long userId, Long associationId) {
        Membership membership = membershipService.getMembership(userId, associationId);

        return membership.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
    }
}
