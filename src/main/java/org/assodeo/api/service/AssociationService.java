package org.assodeo.api.service;

import org.assodeo.api.entity.Association;
import org.assodeo.api.exception.AssociationNotFoundException;
import org.assodeo.api.repository.AssociationRepository;
import org.springframework.stereotype.Service;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;

    public AssociationService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    /**
     * Retrieves an association by its id.
     *
     * @param id the id of the association
     * @return the association with the specified id
     * @throws AssociationNotFoundException if no association is found with the specified id
     */
    public Association getAssociation(Long id) {
        return associationRepository.findById(id)
                .orElseThrow(() -> new AssociationNotFoundException(id));
    }
}
