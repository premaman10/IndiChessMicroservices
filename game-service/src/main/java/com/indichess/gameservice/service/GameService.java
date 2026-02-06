package com.indichess.gameservice.service;

import com.indichess.gameservice.model.Match;
import com.indichess.gameservice.model.dto.GameDTO;
import com.indichess.gameservice.model.dto.MoveDTO;
import com.indichess.gameservice.model.dto.MoveRequest;
import com.indichess.gameservice.repo.MatchRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GameService {

    private final MatchRepo matchRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtService jwtService;

    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class GameState {
        private String[][] board;
        private boolean isWhiteTurn;
        private String status;
        private Long player1Id;
        private Long player2Id;
        private String player1Username;
        private String player2Username;
        private LocalDateTime lastMoveTime;
    }

    public GameDTO getGameDetails(Long matchId, String username) {
        Optional<Match> matchOpt = matchRepo.findById(matchId);
        if (matchOpt.isEmpty()) throw new RuntimeException("Game not found");
        Match match = matchOpt.get();

        GameState gameState = activeGames.computeIfAbsent(matchId, id -> initializeGameState(match));

        String playerColor = match.getPlayer1Id().toString().equals(username) ? "white" : "black"; // This logic needs to be careful, using username vs ID
        // Better: check username against the player IDs (might need user-service to resolve username to ID if not in token)
        // For now, let's assume the frontend sends the right matchId and we trust the token's username.

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(match.getId());
        gameDTO.setStatus(gameState.getStatus());
        gameDTO.setBoard(gameState.getBoard());
        gameDTO.setIsMyTurn(determineMyTurn(gameState, username));
        gameDTO.setGameType(match.getGameType().toString());
        return gameDTO;
    }

    private GameState initializeGameState(Match match) {
        String[][] initialBoard = {
                { "r", "n", "b", "q", "k", "b", "n", "r" },
                { "p", "p", "p", "p", "p", "p", "p", "p" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "P", "P", "P", "P", "P", "P", "P", "P" },
                { "R", "N", "B", "Q", "K", "B", "N", "R" }
        };
        return new GameState(initialBoard, true, "IN_PROGRESS", match.getPlayer1Id(), match.getPlayer2Id(), null, null, LocalDateTime.now());
    }

    private boolean determineMyTurn(GameState state, String username) {
        // Simplified: needs mapping of username to player1/2
        return state.isWhiteTurn(); // Placeholder
    }

    public MoveDTO processMove(Long matchId, MoveRequest moveRequest, Principal principal) {
        String username = principal.getName();
        GameState gameState = activeGames.get(matchId);
        if (gameState == null) throw new RuntimeException("Game not active");

        // Logic to update board and switch turns
        gameState.setBoard(moveRequest.getBoard());
        gameState.setWhiteTurn(!gameState.isWhiteTurn());
        gameState.setLastMoveTime(LocalDateTime.now());

        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setMatchId(matchId);
        moveDTO.setBoard(gameState.getBoard());
        moveDTO.setWhiteTurn(gameState.isWhiteTurn());
        moveDTO.setPlayerUsername(username);
        
        messagingTemplate.convertAndSend("/topic/moves/" + matchId, moveDTO);
        return moveDTO;
    }
}
