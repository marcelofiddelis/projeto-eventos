package br.edu.iff.ccc.bsi.eventsproject.services.eventService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.repositories.eventRepository.EventRepository;


public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private EventCreationDto eventCreationDto;
    private EventUpdateDto eventUpdateDto;
    private Event event;

    private AdministratorCreationDto dto;
    private Administrator user;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dto = new AdministratorCreationDto("Admin",
                "admin123",
                "teste@gmail.com",
                new java.util.Date(),
                "admin",
                "IFF");
        user = new Administrator(dto);

        eventCreationDto = new EventCreationDto(user,
            "Test Event", "Test Description", new java.util.Date(),
            new java.util.Date(System.currentTimeMillis() + 3600000),
            "Test Place", 100, "Test Type"
        );

        eventUpdateDto = new EventUpdateDto(
            "Updated Event", "Updated Description", new java.util.Date(),
            new java.util.Date(System.currentTimeMillis() + 7200000),
            "Updated Place", 200, "Updated Type"
        );

        event = new Event(eventCreationDto);
        event.setId(1L); // Simula um evento existente no banco
    }

    @Test
    public void createEvent_ShouldSaveEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(eventCreationDto);

        assertNotNull(createdEvent);
        assertEquals(eventCreationDto.name(), createdEvent.getName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void getEvent_ShouldReturnEvent_WhenEventExists() throws Exception {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event foundEvent = eventService.getEvent(1L);

        assertNotNull(foundEvent);
        assertEquals(event.getName(), foundEvent.getName());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    public void getEvent_ShouldThrowException_WhenEventDoesNotExist() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            eventService.getEvent(1L);
        });

        assertEquals("Evento não encontrado", exception.getMessage());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    public void updateEvent_ShouldUpdateEventDetails() throws Exception {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(1L, eventUpdateDto);

        assertNotNull(updatedEvent);
        assertEquals(eventUpdateDto.name(), updatedEvent.getName());
        assertEquals(eventUpdateDto.capacity(), updatedEvent.getCapacity());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void deleteEvent_ShouldDeleteEvent_WhenEventExists() {
        doNothing().when(eventRepository).deleteById(1L);

        String result = eventService.deleteEvent(1L);

        assertEquals("Evento deletado com sucesso", result);
        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteEvent_ShouldThrowException_WhenDeleteFails() {
        doThrow(new RuntimeException("Erro ao deletar evento")).when(eventRepository).deleteById(1L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.deleteEvent(1L);
        });

        assertEquals("Erro ao deletar evento", exception.getMessage());
        verify(eventRepository, times(1)).deleteById(1L);
    }
}

