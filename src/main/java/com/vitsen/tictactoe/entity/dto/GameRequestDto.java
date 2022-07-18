package com.vitsen.tictactoe.entity.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class GameRequestDto {

  @NotNull
  private String name;

  @Nullable
  private String description;

}
