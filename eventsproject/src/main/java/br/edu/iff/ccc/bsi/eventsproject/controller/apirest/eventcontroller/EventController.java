package br.edu.iff.ccc.bsi.eventsproject.controller.apirest.eventcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.services.eventService.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Cria um novo evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventCreationDto dto) {
        Event createdEvent = eventService.createEvent(dto);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um evento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        try {
            Event event = eventService.getEvent(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventUpdateDto dto) {
        try {
            Event updatedEvent = eventService.updateEvent(id, dto);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            String response = eventService.deleteEvent(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
