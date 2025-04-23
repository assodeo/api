package org.assodeo.api.service;

import org.assodeo.api.entity.Association;
import org.assodeo.api.entity.Membership;
import org.assodeo.api.entity.User;
import org.assodeo.api.exception.MembershipNotFoundException;
import org.assodeo.api.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipService membershipService;

    @Test
    void getMembershipReturnsMembershipWhenUserAndAssociationExist() {
        Long userId = 1L;
        Long associationId = 2L;

        Association association = new Association();
        association.setId(associationId);

        User user = new User();
        user.setId(userId);

        Membership membership = new Membership();
        membership.setId(1L);

        membership.setUser(user);
        membership.setAssociation(association);

        when(membershipRepository.findByUserIdAndAssociationId(userId, associationId)).thenReturn(Optional.of(membership));

        Membership result = membershipService.getMembership(userId, associationId);

        assertEquals(1L, result.getId());
        assertEquals(userId, result.getUser().getId());
        assertEquals(associationId, result.getAssociation().getId());
    }

    @Test
    void getMembershipThrowsExceptionWhenMembershipDoesNotExist() {
        Long userId = 1L;
        Long associationId = 2L;

        when(membershipRepository.findByUserIdAndAssociationId(userId, associationId)).thenReturn(Optional.empty());

        assertThrows(MembershipNotFoundException.class, () -> membershipService.getMembership(userId, associationId));
    }

    @Test
    void getAssociationsReturnsAssociationsWhenUserHasMemberships() {
        Long userId = 1L;

        Association association1 = new Association();
        association1.setId(1L);

        Association association2 = new Association();
        association2.setId(2L);

        when(membershipRepository.findAssociationsByUserId(userId)).thenReturn(List.of(association1, association2));

        Collection<Association> result = membershipService.getAssociations(userId);

        assertEquals(2, result.size());

        assertTrue(result.contains(association1));
        assertTrue(result.contains(association2));
    }

    @Test
    void getAssociationsReturnsEmptyCollectionWhenUserHasNoMemberships() {
        Long userId = 1L;

        when(membershipRepository.findAssociationsByUserId(userId)).thenReturn(Collections.emptyList());

        Collection<Association> result = membershipService.getAssociations(userId);

        assertTrue(result.isEmpty());
    }
}
