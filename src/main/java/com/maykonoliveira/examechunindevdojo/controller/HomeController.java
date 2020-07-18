package com.maykonoliveira.examechunindevdojo.controller;

import com.maykonoliveira.examechunindevdojo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

/** @author maykon-oliveira */
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {
  private final VehicleService vehicleService;

  @GetMapping("login")
  public String login() {
    return "login";
  }

  @GetMapping
  public String findAll(Model model) {
    IReactiveDataDriverContextVariable reactiveDataDrivenMode =
        new ReactiveDataDriverContextVariable(vehicleService.findAll(), 1);
    model.addAttribute("vehicles", reactiveDataDrivenMode);
    return "public-vehicle-list";
  }
}
