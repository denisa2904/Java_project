package com.example.zoo.api;

import com.example.zoo.model.Animal;
import com.example.zoo.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final AnimalService animalService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/bookTickets")
    public String bookTickets() {
        return "book_tickets";
    }

    @RequestMapping("/aboutUs")
    public String aboutUs() {
        return "about_us";
    }

    @RequestMapping("/animals")
    public String animals() {
        return "animals";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String register() {
        return "signup";
    }

    @RequestMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgot_pass";
    }

    @RequestMapping("/account")
    public String account() {
        return "account_settings";
    }

    @RequestMapping("/animal={name}")
    public ModelAndView animal(@PathVariable String name) throws FileNotFoundException {
        ModelAndView modelAndView = new ModelAndView("animal-description");
        Animal animal = animalService.getAnimalByName(name).get();
        modelAndView.addObject("ANIMAL_NAME", animal.getName());
        modelAndView.addObject("BINOMIAL_NAME", animal.getBinomialName());
        modelAndView.addObject("REGION", animal.getOrigin());
        modelAndView.addObject("CONSERVATION", animal.getConservation());
        modelAndView.addObject("TYPE", animal.getType());
        modelAndView.addObject("CLIMATE", animal.getClimate());
        modelAndView.addObject("DESCRIPTION", animal.getDescription());
        return modelAndView;
    }
}
