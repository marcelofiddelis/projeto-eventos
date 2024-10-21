package br.edu.iff.ccc.bsi.eventsproject.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/homepage")
public class HomePageController {
      @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("message", "Homepage");
        return "homepage/homepage"; 
    }
}

