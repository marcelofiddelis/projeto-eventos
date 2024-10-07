package br.edu.iff.ccc.bsi.eventsproject.services.eventService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.repositories.eventRepository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventCreationDto dto){
        Event event = new Event(dto);
        return eventRepository.save(event);
    }

    public Event getEvent(Long id) throws Exception{
        return eventRepository.findById(id).orElseThrow(() -> new Exception("Evento não encontrado"));
    }


    public List<Event> getAllEvents() {
        return eventRepository.findAll(); // Retorna a lista de todos os eventos
    }

    public Event updateEvent(Long id, EventUpdateDto dto) throws Exception{
        Event eventToBeUpdated = getEvent(id);
        eventToBeUpdated.setName(dto.name());
        eventToBeUpdated.setDescription(dto.description());
        eventToBeUpdated.setStartDate(dto.startDate());
        eventToBeUpdated.setEndDate(dto.endDate());
        eventToBeUpdated.setPlace(dto.place());
        eventToBeUpdated.setCapacity(dto.capacity());
        eventToBeUpdated.setType(dto.type());

        return eventRepository.save(eventToBeUpdated);
    }

    public String deleteEvent(long id){
        try {
            eventRepository.deleteById(id);
            return "Evento deletado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar evento");
        }
    }
}
