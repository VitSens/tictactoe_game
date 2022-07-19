package com.vitsen.tictactoe.security.websocket;

import com.vitsen.tictactoe.entity.Account;
import com.vitsen.tictactoe.service.AccountService;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthenticatorService {

  private final AccountService accountService;
  private final AuthenticationManager authenticationManager;

  public WebSocketAuthenticatorService(
      AccountService accountService,
      AuthenticationManager authenticationManager
  ) {
    this.accountService = accountService;
    this.authenticationManager = authenticationManager;
  }

  public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(
      final String username,
      final String password
  ) throws AuthenticationException {

    if (username == null || username.trim().isEmpty()) {
      throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
    }
    if (password == null || password.trim().isEmpty()) {
      throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
    }

    Account account = accountService.findUserByUsername(username);

    if (account == null) {
      throw new BadCredentialsException("Bad credentials for account " + username);
    }

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        username,
        password,
        Collections.singletonList(new SimpleGrantedAuthority("USER"))
    );

    authenticationManager.authenticate(token);
    token.eraseCredentials();

    return token;
  }
}
