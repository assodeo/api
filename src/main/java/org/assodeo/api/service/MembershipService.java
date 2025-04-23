package org.assodeo.api.service;

import org.assodeo.api.entity.Association;
import org.assodeo.api.entity.Membership;
import org.assodeo.api.exception.MembershipNotFoundException;
import org.assodeo.api.repository.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Retrieves the membership of a user in a specific association.
     *
     * @param userId        the id of the user
     * @param associationId the id of the association
     * @return the membership of the user in the association
     * @throws MembershipNotFoundException if no membership is found for the user in the association
     */
    public Membership getMembership(Long userId, Long associationId) {
        return membershipRepository.findByUserIdAndAssociationId(userId, associationId)
                .orElseThrow(() -> new MembershipNotFoundException(userId, associationId));
    }

    /**
     * Retrieves all associations of a user.
     *
     * @param userId the id of the user
     * @return a collection of the associations
     */
    public Collection<Association> getAssociations(Long userId) {
        return membershipRepository.findAssociationsByUserId(userId);
    }
}
