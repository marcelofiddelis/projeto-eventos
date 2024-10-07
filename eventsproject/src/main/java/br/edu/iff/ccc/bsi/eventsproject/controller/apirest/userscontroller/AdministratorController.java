package br.edu.iff.ccc.bsi.eventsproject.controller.apirest.userscontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices.AdministratorService;

@RestController
@RequestMapping("/api/users/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Operation(summary = "Cria um novo administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Administrator.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Administrator>> addUser(@RequestBody AdministratorCreationDto userDto) {
        Administrator createdAdministrator = administratorService.addUser(userDto);
        EntityModel<Administrator> resource = EntityModel.of(createdAdministrator);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AdministratorController.class).getUser(createdAdministrator.getId())).withSelfRel();
        resource.add(selfLink);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um administrador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Administrator.class)) }),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Administrator>> getUser(@PathVariable Long id) {
        try {
            Administrator administrator = administratorService.getUser(id);
            EntityModel<Administrator> resource = EntityModel.of(administrator);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AdministratorController.class).getUser(id)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza um administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Administrator.class)) }),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Administrator>> updateUser(@PathVariable Long id, @RequestBody AdministratorUpdateDto updateDto) {
        try {
            Administrator updatedAdministrator = administratorService.updateUser(id, updateDto);
            EntityModel<Administrator> resource = EntityModel.of(updatedAdministrator);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AdministratorController.class).getUser(id)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            String response = administratorService.deleteUser(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
