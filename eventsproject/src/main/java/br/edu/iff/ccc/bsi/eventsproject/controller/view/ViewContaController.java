package br.edu.iff.ccc.bsi.eventsproject.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/conta")
public class ViewContaController {

    //@Autowired
    //private UserService userService;

    @GetMapping
    public String getUserProfile(Model model, HttpSession session) {
        CommonUser loggedUser = (CommonUser) session.getAttribute("loggedUser");

        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
            return "conta/conta"; 
        } else {
            return "redirect:/home"; 
        }
    }
}
