package org.assodeo.api.dto.association;

import org.assodeo.api.entity.Association;

public record AssociationDto(String name, String description) {

    public static AssociationDto from(Association association) {
        return new AssociationDto(association.getName(), association.getDescription());
    }
}
