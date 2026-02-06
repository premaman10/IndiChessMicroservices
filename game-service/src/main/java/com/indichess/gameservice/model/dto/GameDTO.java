package com.indichess.gameservice.model.dto;

import com.indichess.gameservice.model.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GameDTO {
    private Long id;
    private User player1;
    private User player2;
    private String status;
    private String playerColor;
    private boolean isMyTurn;
    private String[][] board;
    private String fen;
    private String gameType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
