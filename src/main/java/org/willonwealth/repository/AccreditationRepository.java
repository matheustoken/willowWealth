package org.willonwealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.willonwealth.model.Accreditation;
import org.willonwealth.model.AccreditationStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccreditationRepository extends JpaRepository<Accreditation, Long> {

    List<Accreditation> findByUserId(String userId);

    boolean existsByUserIdAndStatus(String userId, AccreditationStatus status);

    Optional<Accreditation> findByAccreditationId(UUID accreditationId);

    List<Accreditation> findByStatusAndLastUpdatedBefore(AccreditationStatus status, Instant date);


}