package org.assodeo.api.exception;

public class MembershipNotFoundException extends RuntimeException {

    public MembershipNotFoundException(Long userId, Long associationId) {
        super("Membership not found for user with ID %d in association with ID %d".formatted(userId, associationId));
    }
}
