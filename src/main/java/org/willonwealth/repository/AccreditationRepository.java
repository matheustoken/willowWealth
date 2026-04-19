package org.willonwealth.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import org.willonwealth.model.Accreditation;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccreditationRepository implements ReactivePanacheMongoRepository<Accreditation> {

    public Uni<List<Accreditation>> findByUserId(String userId) {
        return list("userId", userId);
    }

    public Uni<Accreditation> findById(String id) {
        return findById(new ObjectId(id));
    }

    public Uni<Accreditation> findPendingByUserId(String userId) {
        // Corrigido: Removido o Optional para facilitar o fluxo no Service
        // Também usei o Enum AccreditationStatus.PENDING em vez de String pura se possível
        return find("userId = :uId and status = :st",
                Parameters.with("uId", userId)
                        .and("st", "PENDING"))
                .firstResult();
    }

}
