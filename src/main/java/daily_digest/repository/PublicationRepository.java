package daily_digest.repository;

import daily_digest.domain.Publication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Publication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {}
