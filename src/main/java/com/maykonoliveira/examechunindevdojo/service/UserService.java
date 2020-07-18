package com.maykonoliveira.examechunindevdojo.service;

import com.maykonoliveira.examechunindevdojo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/** @author maykon-oliveira */
@Service
@AllArgsConstructor
public class UserService implements ReactiveUserDetailsService {
  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userRepository.findByUsername(username).cast(UserDetails.class);
  }
}
