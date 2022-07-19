package com.vitsen.tictactoe.security.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthChannelInterceptor implements ChannelInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(AuthChannelInterceptor.class);

  private final WebSocketAuthenticatorService service;
  private static final String USERNAME_HEADER = "username";
  private static final String PASSWORD_HEADER = "password";

  public AuthChannelInterceptor(WebSocketAuthenticatorService service) {
    this.service = service;
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    // Instantiate an object for retrieving the STOMP headers
    final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    assert accessor != null;

    if (accessor.getCommand() == StompCommand.CONNECT) {
      final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
      final String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

      UsernamePasswordAuthenticationToken user = service.getAuthenticatedOrFail(username, password);
      accessor.setUser(user);
    }

    return message;
  }
}