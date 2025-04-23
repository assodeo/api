package org.assodeo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssociationNotFoundException extends RuntimeException {

    public AssociationNotFoundException(Long id) {
        super("Association not found: %d".formatted(id));
    }
}
