package com.indichess.gameservice.service;

import com.indichess.gameservice.model.Match;
import com.indichess.gameservice.model.MatchStatus;
import com.indichess.gameservice.model.User;
import com.indichess.gameservice.model.GameType;
import com.indichess.gameservice.repo.MatchRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final Map<String, String> waitingPlayers = new ConcurrentHashMap<>(); 
    private final Map<Long, String[]> matchPlayers = new ConcurrentHashMap<>();

    private final JwtService jwtService;
    private final UserClient userClient;
    private final MatchRepo matchRepo;

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Optional<Long> createMatch(HttpServletRequest request, String gameTypeStr) {
        String tk = getJwtFromCookie(request);
        String username = jwtService.extractUsername(tk);

        if (username == null) return Optional.empty();

        synchronized (this) {
            for (Map.Entry<String, String> entry : waitingPlayers.entrySet()) {
                String opponentUsername = entry.getKey();
                String opponentGameType = entry.getValue();
                if (!opponentUsername.equals(username) && opponentGameType.equalsIgnoreCase(gameTypeStr)) {
                    User player1 = userClient.getUserByUsername(opponentUsername);
                    User player2 = userClient.getUserByUsername(username);
                    if (player1 != null && player2 != null) {
                        Match match = new Match(player1.getUserId(), player2.getUserId(), MatchStatus.IN_PROGRESS, 1);
                        match.setGameType(GameType.valueOf(gameTypeStr.toUpperCase()));
                        match = matchRepo.save(match);
                        
                        matchPlayers.put(match.getId(), new String[]{opponentUsername, username});
                        waitingPlayers.remove(opponentUsername);
                        return Optional.of(match.getId());
                    }
                }
            }
            waitingPlayers.remove(username);
            waitingPlayers.put(username, gameTypeStr);
            return Optional.of(-1L);
        }
    }

    public Optional<Long> checkMatch(HttpServletRequest request) {
        String tk = getJwtFromCookie(request);
        String username = jwtService.extractUsername(tk);
        if (username == null) return Optional.empty();

        synchronized (this) {
            if (waitingPlayers.containsKey(username)) return Optional.of(-1L);
            for (Map.Entry<Long, String[]> entry : matchPlayers.entrySet()) {
                String[] players = entry.getValue();
                if (players[0].equals(username) || players[1].equals(username)) {
                    Long matchId = entry.getKey();
                    matchPlayers.remove(matchId);
                    return Optional.of(matchId);
                }
            }
        }
        return Optional.empty();
    }
}
