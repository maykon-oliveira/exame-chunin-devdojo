package com.maykonoliveira.examechunindevdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExameChuninDevdojoApplication {

  //  static {
  //    BlockHound.install();
  //  }

  public static void main(String[] args) {
    //
    // System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"));
    SpringApplication.run(ExameChuninDevdojoApplication.class, args);
  }
}
