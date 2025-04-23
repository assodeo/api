package org.assodeo.api.service;

import org.assodeo.api.entity.Association;
import org.assodeo.api.exception.AssociationNotFoundException;
import org.assodeo.api.repository.AssociationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociationServiceTest {

    @Mock
    private AssociationRepository associationRepository;

    @InjectMocks
    private AssociationService associationService;

    @Test
    void getAssociationReturnsAssociationWhenIdExists() {
        Long associationId = 1L;

        Association association = new Association();
        association.setId(associationId);
        association.setName("Test Association");
        association.setDescription("This is a test association");

        when(associationRepository.findById(associationId)).thenReturn(Optional.of(association));

        Association result = associationService.getAssociation(associationId);

        assertEquals(associationId, result.getId());
        assertEquals("Test Association", result.getName());
        assertEquals("This is a test association", result.getDescription());
    }

    @Test
    void getAssociationThrowsExceptionWhenIdDoesNotExist() {
        Long associationId = 1L;

        when(associationRepository.findById(associationId)).thenReturn(Optional.empty());

        assertThrows(AssociationNotFoundException.class, () -> associationService.getAssociation(associationId));
    }
}
