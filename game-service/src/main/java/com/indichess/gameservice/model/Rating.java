package com.indichess.gameservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_type"})
)
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type", nullable = false)
    private GameType gameType;

    private int rating;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int draws;

    private LocalDateTime updatedAt;
}
