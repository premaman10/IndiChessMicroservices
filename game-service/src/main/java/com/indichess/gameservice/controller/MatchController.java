package com.indichess.gameservice.controller;

import com.indichess.gameservice.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/create")
    public ResponseEntity<?> createMatch(HttpServletRequest request, @RequestParam String gameType) {
        Optional<Long> matchId = matchService.createMatch(request, gameType);
        return ResponseEntity.ok(matchId.orElse(-1L));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkMatch(HttpServletRequest request) {
        Optional<Long> matchId = matchService.checkMatch(request);
        return ResponseEntity.ok(matchId.orElse(-1L));
    }
}
