package com.vitsen.tictactoe.service;

import com.vitsen.tictactoe.entity.Game;
import com.vitsen.tictactoe.repository.GameRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    return gameRepository.findAll(Sort.by(Direction.DESC, "createdAt"));
  }

  @Transactional
  public void create(Game game) {
    validate(game);

    gameRepository.save(game);

    refresh();
  }

  private void validate(Game game) {
    if (game.getName() == null || game.getName().trim().isEmpty())
      throw new RuntimeException("Name game is null or empty");

    boolean isExist = this.findAll().stream()
        .anyMatch(i -> game.getName().equals(i.getName()));
    if (isExist) throw new RuntimeException("Game is exist");
  }

  private void refresh() {
    this.simpMessagingTemplate.convertAndSend("/topic/lobby", this.findAll());
  }
}
