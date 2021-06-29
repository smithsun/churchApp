package daily_digest.repository;

import daily_digest.domain.Videos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Videos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideosRepository extends JpaRepository<Videos, Long> {}
