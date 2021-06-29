package daily_digest.repository;

import daily_digest.domain.Digests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Digests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DigestsRepository extends JpaRepository<Digests, Long> {}
