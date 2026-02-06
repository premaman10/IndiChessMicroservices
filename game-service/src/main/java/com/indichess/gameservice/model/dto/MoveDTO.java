package com.indichess.gameservice.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MoveDTO {
    private Long matchId;
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private String piece;
    private String promotedTo;
    private String capturedPiece;
    private boolean castled;
    private boolean isEnPassant;
    private boolean isPromotion;
    private String fenBefore;
    private String fenAfter;
    private String[][] board;
    private boolean isWhiteTurn;
    private String playerColor;
    private LocalDateTime timestamp;
    private String moveNotation;
    private String playerUsername;
}
