package com.indichess.gameservice.controller;

import com.indichess.gameservice.model.dto.MoveDTO;
import com.indichess.gameservice.model.dto.MoveRequest;
import com.indichess.gameservice.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {

    private final GameService gameService;

    @MessageMapping("/move/{matchId}")
    public void processMove(@DestinationVariable Long matchId, @Payload MoveRequest moveRequest, Principal principal) {
        gameService.processMove(matchId, moveRequest, principal);
    }
}
