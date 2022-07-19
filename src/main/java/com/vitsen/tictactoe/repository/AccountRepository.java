package com.vitsen.tictactoe.repository;

import com.vitsen.tictactoe.entity.Account;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Account findByUsername(@NotNull String username);
}
