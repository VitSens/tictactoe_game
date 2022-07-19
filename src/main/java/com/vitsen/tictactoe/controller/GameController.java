package com.vitsen.tictactoe.controller;

import com.vitsen.tictactoe.entity.Game;
import com.vitsen.tictactoe.entity.dto.GameRequestDto;
import com.vitsen.tictactoe.service.GameService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @MessageMapping("/lobby")
  @SendTo("/topic/lobby")
  public List<Game> getGame() {
    return gameService.findAll();
  }

  @MessageMapping("/lobby/create")
  public void createGame(GameRequestDto lobby) {
    gameService.create(toLobby(lobby));
  }

  @MessageExceptionHandler
  @SendTo("/topic/errors")
  public String handleException(Throwable exception) {
    return exception.getMessage();
  }

  private Game toLobby(GameRequestDto lobby) {
    return Game.builder()
        .name(lobby.getName())
        .description(lobby.getDescription())
        .createdBy("SYSTEM")
        .createdAt(LocalDateTime.now())
        .playerCount(1)
        .build();
  }
}
