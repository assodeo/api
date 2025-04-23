package org.assodeo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MembershipNotFoundException extends RuntimeException {

    public MembershipNotFoundException(Long userId, Long associationId) {
        super("Membership not found for user with id %d in association with id %d".formatted(userId, associationId));
    }
}
