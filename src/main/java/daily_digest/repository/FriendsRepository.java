package daily_digest.repository;

import daily_digest.domain.Friends;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Friends entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {}
