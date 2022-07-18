package com.vitsen.tictactoe.service;

import com.vitsen.tictactoe.entity.Game;
import com.vitsen.tictactoe.repository.GameRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  private final GameRepository gameRepository;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public GameService(
      GameRepository gameRepository,
      SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.gameRepository = gameRepository;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  public List<Game> findAll() {
    return gameRepository.findAll().stream()
        .sorted(Comparator.comparing(Game::getCreatedAt).reversed())
        .collect(Collectors.toUnmodifiableList());
  }

  @Transactional
  public void create(Game game) {
    validate(game);

    gameRepository.save(game);

    refresh();
  }

  private void validate(Game game) {
    boolean isExist = this.findAll().stream()
        .anyMatch(i -> game.getName().equals(i.getName()));
    if (isExist) throw new RuntimeException();
  }

  private void refresh() {
    this.simpMessagingTemplate.convertAndSend("/topic/lobby", this.findAll());
  }
}
