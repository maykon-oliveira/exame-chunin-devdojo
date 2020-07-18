package com.maykonoliveira.examechunindevdojo.repository;

import com.maykonoliveira.examechunindevdojo.entity.LoggedUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/** @author maykon-oliveira */
public interface UserRepository extends ReactiveCrudRepository<LoggedUser, Long> {
  Mono<LoggedUser> findByUsername(String username);
}
