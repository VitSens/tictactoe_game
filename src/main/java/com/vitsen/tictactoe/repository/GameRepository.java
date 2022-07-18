package com.vitsen.tictactoe.repository;

import com.vitsen.tictactoe.entity.Game;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
public interface GameRepository extends JpaRepository<Game, UUID> {

}
