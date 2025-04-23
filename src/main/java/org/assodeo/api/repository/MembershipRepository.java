package org.assodeo.api.repository;

import org.assodeo.api.entity.Association;
import org.assodeo.api.entity.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Optional<Membership> findByUserIdAndAssociationId(Long userId, Long associationId);

    @Query("SELECT m.association FROM Membership m WHERE m.user.id = :userId")
    Collection<Association> findAssociationsByUserId(Long userId);
}
