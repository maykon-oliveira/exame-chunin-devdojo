package com.maykonoliveira.examechunindevdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ExameChuninDevdojoApplication {

  static {
    BlockHound.install();
  }

  public static void main(String[] args) {
    SpringApplication.run(ExameChuninDevdojoApplication.class, args);
  }
}
