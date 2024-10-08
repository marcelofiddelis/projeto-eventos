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

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices.CommonService;

@RestController("usersCommonController")
@RequestMapping("/api/users/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Operation(summary = "Cria um novo usuário comum")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommonUser.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<CommonUser>> addUser(@RequestBody CommonCreationDto userDto) {
        CommonUser createdUser = commonService.addUser(userDto);
        EntityModel<CommonUser> resource = EntityModel.of(createdUser);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommonController.class).getUser(createdUser.getId())).withSelfRel();
        resource.add(selfLink);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um usuário comum por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommonUser.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CommonUser>> getUser(@PathVariable Long id) {
        try {
            CommonUser user = commonService.getUser(id);
            EntityModel<CommonUser> resource = EntityModel.of(user);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommonController.class).getUser(id)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza um usuário comum")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommonUser.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CommonUser>> updateUser(@PathVariable Long id, @RequestBody CommonUpdateDto updateDto) {
        try {
            CommonUser updatedUser = commonService.updateUser(id, updateDto);
            EntityModel<CommonUser> resource = EntityModel.of(updatedUser);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommonController.class).getUser(id)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um usuário comum")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            String response = commonService.deleteUser(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
