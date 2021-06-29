package daily_digest.repository;

import daily_digest.domain.PublicNotice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PublicNotice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicNoticeRepository extends JpaRepository<PublicNotice, Long> {}
