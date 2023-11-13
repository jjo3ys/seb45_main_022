package com.codestatus.domain.like.repository;

import com.codestatus.domain.like.entity.Like;
import com.codestatus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {
//    @Query(nativeQuery = true, value = "SELECT * FROM like WHERE feed_id=:feed AND user_id=:user")
    Optional<Like> findLikeByFeedFeedIdAndUserUserId(Long feedId, Long userId);
    boolean existsByFeedFeedIdAndUserUserIdAndDeletedIsFalse(Long feedId, User user);
    long countAllByFeedFeedIdAndDeletedIsFalse(long feedId);
}
