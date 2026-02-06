package com.indichess.gameservice.model.dto;

import lombok.Data;

@Data
public class MoveRequest {
    private Integer fromRow;
    private Integer fromCol;
    private Integer toRow;
    private Integer toCol;
    private String piece;
    private String promotedTo;
    private String capturedPiece;
    private Boolean castled;
    private Boolean isEnPassant;
    private Boolean isPromotion;
    private String fenBefore;
    private String fenAfter;
    private String playerColor;
    private String[][] board;
}
