package com.indichess.gameservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "moves",
        uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "ply"})
)
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private int ply;
    private int moveNumber;

    @Enumerated(EnumType.STRING)
    private PieceColor color;

    private String uci;
    private String san;

    private String fenBefore;
    private String fenAfter;

    private LocalDateTime createdAt;
}
