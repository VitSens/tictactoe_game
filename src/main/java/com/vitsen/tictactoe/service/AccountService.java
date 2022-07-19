package com.vitsen.tictactoe.service;

import com.vitsen.tictactoe.entity.Account;
import com.vitsen.tictactoe.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account findUserByUsername(String username) {
    return accountRepository.findByUsername(username);
  }

}
