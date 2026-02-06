package com.indichess.gameservice.model;

public enum MatchStatus {

    IN_PROGRESS(-2),
    PLAYER1_WON(-1),
    DRAW(0),
    PLAYER2_WON(1);


    private final int code;

    MatchStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
