package org.assodeo.api.controller;

import org.assodeo.api.dto.association.AssociationDto;
import org.assodeo.api.entity.Association;
import org.assodeo.api.service.AssociationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/association")
public class AssociationController {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'READ')")
    public AssociationDto getAssociation(@PathVariable Long id) {
        Association association = associationService.getAssociation(id);

        return AssociationDto.from(association);
    }
}
