package br.edu.iff.ccc.bsi.eventsproject;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.services.eventService.EventService;
import br.edu.iff.ccc.bsi.eventsproject.services.registerService.RegistrationService;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices.AdministratorService;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices.CommonService;

@Component
public class Runner implements CommandLineRunner {

    private final CommonService commonService;
    private final EventService eventService;
    private final RegistrationService registrationService;
    private final AdministratorService administratorService; 

    public Runner(CommonService commonService, EventService eventService, 
                  RegistrationService registrationService, 
                  AdministratorService administratorService) {
        this.commonService = commonService;
        this.eventService = eventService;
        this.registrationService = registrationService;
        this.administratorService = administratorService; 
    }

    @Override
    public void run(String... args) throws Exception {
        
        System.out.println("Criando CommonUser!");
        CommonCreationDto dto = new CommonCreationDto("Junin", "12451235","teste@gmail.com",new java.util.Date(),"user", "235235235235", "15523142244");
        // commonUser.setCpf("15523142244");
        // commonUser.setName("Junin");
        // commonUser.setPassword("12451235");
        // commonUser.setRegisterDate(new java.util.Date());
        CommonUser commonUser = commonService.addUser(dto);
        System.out.println("ComomUser criado com sucesso!");

         // Criando um Administrador
        AdministratorCreationDto adminDto = new AdministratorCreationDto("Admin Name", "adminPassword", 
                "admin@test.com", new Date(), "admin", "My Company");
        Administrator admin = administratorService.addUser(adminDto); 
        System.out.println("Administrador criado com sucesso!");

        // Criação de eventos
        EventCreationDto eventDto1 = new EventCreationDto(admin, "Evento 1", "Descrição do Evento 1", new Date(), new Date(System.currentTimeMillis() + 86400000), "Local 1", 100, "Tipo 1");
        Event event1 = eventService.createEvent(eventDto1);
        System.out.println("Evento 1 criado com sucesso!");

        EventCreationDto eventDto2 = new EventCreationDto(admin, "Evento 2", "Descrição do Evento 2", new Date(), new Date(System.currentTimeMillis() + 86400000), "Local 2", 50, "Tipo 2");
        Event event2 = eventService.createEvent(eventDto2);
        System.out.println("Evento 2 criado com sucesso!");

        //  Registro do usuário nos eventos
        Registration registration1 = new Registration();
        registration1.setUser(commonUser);
        registration1.setEvent(event1);
        registration1.setRegistrationDate(new Date());
        registrationService.registerUser(registration1); // Persistindo a inscrição
        System.out.println("Usuário registrado no Evento 1 com sucesso!");

        Registration registration2 = new Registration();
        registration2.setUser(commonUser);
        registration2.setEvent(event2);
        registration2.setRegistrationDate(new Date());
        registrationService.registerUser(registration2); // Persistindo a inscrição
        System.out.println("Usuário registrado no Evento 2 com sucesso!");

    }

}
