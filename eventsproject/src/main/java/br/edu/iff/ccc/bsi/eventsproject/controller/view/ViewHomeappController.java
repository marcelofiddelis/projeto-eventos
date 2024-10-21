package br.edu.iff.ccc.bsi.eventsproject.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.services.registerService.RegistrationService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/homeapp")
public class ViewHomeappController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public String getHomeappPage(Model model, HttpSession session) {
        //Verifica se há um usuário autenticado na sessão
        CommonUser loggedUser = (CommonUser) session.getAttribute("loggedUser");

        if (loggedUser != null) {
            List<Event> eventos = registrationService.getEventsByUserId(loggedUser.getId());
            model.addAttribute("username", loggedUser.getName());
            model.addAttribute("eventos", eventos);

            return "homeapp/homeapp"; 

        } else {
            return "redirect:/home";
        }
    
    }

}
