package com.indichess.gameservice.repo;

import com.indichess.gameservice.model.Rating;
import com.indichess.gameservice.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserIdAndGameType(Long userId, GameType gameType);
}
