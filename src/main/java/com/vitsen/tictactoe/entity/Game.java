package com.vitsen.tictactoe.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.lang.Nullable;

@RedisHash(value = "Game")
@TypeAlias("Game")
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Game {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column
  @Nullable
  private UUID userId;

  @Column
  @NotNull
  private String createdBy;

  @Column
  @NotNull
  private LocalDateTime createdAt;

  @Column
  @NotNull
  @Indexed
  private String name;

  @Column
  @Nullable
  private String description;

  @Column
  @NotNull
  private Integer playerCount;

}
