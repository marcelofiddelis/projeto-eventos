package br.edu.iff.ccc.bsi.eventsproject.controller.apirest.eventcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.services.eventService.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Cria um novo evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento criado com sucesso", content = @Content(schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "400", description = "Erro na criação do evento", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Event>> createEvent(@RequestBody EventCreationDto dto) {
        Event createdEvent = eventService.createEvent(dto);

        // Adicionando links HATEOAS
        EntityModel<Event> resource = EntityModel.of(createdEvent);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEvent(createdEvent.getId())).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).updateEvent(createdEvent.getId(), null)).withRel("update"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).deleteEvent(createdEvent.getId())).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getAllEvents()).withRel("all-events"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um evento pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado", content = @Content(schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Event>> getEvent(@PathVariable Long id) {
        try {
            Event event = eventService.getEvent(id);

            // Adicionando links HATEOAS
            EntityModel<Event> resource = EntityModel.of(event);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEvent(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).updateEvent(id, null)).withRel("update"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).deleteEvent(id)).withRel("delete"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getAllEvents()).withRel("all-events"));

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lista todos os eventos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso", content = @Content(schema = @Schema(implementation = Event.class)))
    })
    @GetMapping
    public ResponseEntity<List<EntityModel<Event>>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        // Adicionando HATEOAS aos eventos
        List<EntityModel<Event>> resources = events.stream()
            .map(event -> {
                EntityModel<Event> resource = EntityModel.of(event);
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEvent(event.getId())).withSelfRel());
                return resource;
            })
            .collect(Collectors.toList());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um evento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso", content = @Content(schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Event>> updateEvent(@PathVariable Long id, @RequestBody EventUpdateDto dto) {
        try {
            Event updatedEvent = eventService.updateEvent(id, dto);

            // Adicionando links HATEOAS
            EntityModel<Event> resource = EntityModel.of(updatedEvent);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEvent(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).updateEvent(id, dto)).withRel("update"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).deleteEvent(id)).withRel("delete"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getAllEvents()).withRel("all-events"));

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um evento pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso", content = @Content),
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

