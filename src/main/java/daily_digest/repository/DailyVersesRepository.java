package daily_digest.repository;

import daily_digest.domain.DailyVerses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DailyVerses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyVersesRepository extends JpaRepository<DailyVerses, Long> {}
