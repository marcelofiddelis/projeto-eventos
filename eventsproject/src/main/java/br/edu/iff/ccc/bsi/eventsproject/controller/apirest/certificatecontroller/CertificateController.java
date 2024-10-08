package br.edu.iff.ccc.bsi.eventsproject.controller.apirest.certificatecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.services.certificateService.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Operation(summary = "Cria um novo certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Certificado criado com sucesso", content = @Content(schema = @Schema(implementation = Certificate.class))),
        @ApiResponse(responseCode = "400", description = "Erro na criação do certificado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Certificate>> createCertificate(@RequestBody Certificate certificate) {
        Certificate createdCertificate = certificateService.createCertificate(certificate);

        // Adicionando links HATEOAS
        EntityModel<Certificate> resource = EntityModel.of(createdCertificate);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getCertificateById(createdCertificate.getId())).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).deleteCertificate(createdCertificate.getId())).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getAllCertificates()).withRel("all-certificates"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um certificado pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado encontrado", content = @Content(schema = @Schema(implementation = Certificate.class))),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Certificate>> getCertificateById(@PathVariable Long id) {
        try {
            Certificate certificate = certificateService.getCertificateById(id);

            // Adicionando links HATEOAS
            EntityModel<Certificate> resource = EntityModel.of(certificate);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getCertificateById(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).deleteCertificate(id)).withRel("delete"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getAllCertificates()).withRel("all-certificates"));

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lista todos os certificados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de certificados retornada com sucesso", content = @Content(schema = @Schema(implementation = Certificate.class)))
    })
    @GetMapping
    public ResponseEntity<List<EntityModel<Certificate>>> getAllCertificates() {
        List<Certificate> certificates = certificateService.getAllCertificates();

        // Adicionando HATEOAS aos certificados
        List<EntityModel<Certificate>> resources = certificates.stream()
            .map(certificate -> {
                EntityModel<Certificate> resource = EntityModel.of(certificate);
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getCertificateById(certificate.getId())).withSelfRel());
                return resource;
            })
            .collect(Collectors.toList());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um certificado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado atualizado com sucesso", content = @Content(schema = @Schema(implementation = Certificate.class))),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Certificate>> updateCertificate(@PathVariable Long id, @RequestBody Certificate updatedCertificate) {
        try {
            Certificate certificate = certificateService.updateCertificate(id, updatedCertificate);

            // Adicionando links HATEOAS
            EntityModel<Certificate> resource = EntityModel.of(certificate);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getCertificateById(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).deleteCertificate(id)).withRel("delete"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class).getAllCertificates()).withRel("all-certificates"));

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um certificado pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado deletado com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificate(@PathVariable Long id) {
        try {
            String response = certificateService.deleteCertificate(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

