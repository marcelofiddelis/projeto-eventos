package br.edu.iff.ccc.bsi.eventsproject.controller.view;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices.AdministratorService;

@Controller
@RequestMapping(path = "/criarevento")
public class CriarEventoController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping
    public String showCreateEventForm(Model model) {
        // Cria uma lista de administradores para selecionar no formulário
        
        try {
             AdministratorCreationDto adminDto = new AdministratorCreationDto("Admin Name", "adminPassword", 
                "admin@test.com", new Date(), "admin", "My Company");
            Administrator administrator = new Administrator(adminDto);
            model.addAttribute("administrators", administrator);
            model.addAttribute("eventDto", new EventCreationDto(null, "", "", new Date(), new Date(), "", 0, ""));
            return "criarevento/criarevento";
        } catch (Exception e) {
            throw new RuntimeException();
        }
       
    }

    @PostMapping
    public String createEvent(@ModelAttribute EventCreationDto eventDto, Model model) {
        
        model.addAttribute("message", "Evento criado com sucesso!");
        return "redirect:/homeapp/homeapp";
    }

}
