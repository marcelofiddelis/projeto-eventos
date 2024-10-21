package br.edu.iff.ccc.bsi.eventsproject.services.registerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.registerRepository.RegistrationRepository;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    // Método para buscar todas as inscrições de um usuário
    public List<Registration> getRegistrationsByUser(CommonUser user) {
        return registrationRepository.findByUser(user);
    }
    

    public Registration registerUser(Registration registration) {
        return registrationRepository.save(registration);
    }

    public List<Event> getEventsByUserId(Long userId) {
        List<Event> events = registrationRepository.findEventsByUserId(userId);
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events;
    }
    
}
