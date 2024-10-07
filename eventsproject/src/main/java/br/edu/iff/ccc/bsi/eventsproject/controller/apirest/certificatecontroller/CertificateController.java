package br.edu.iff.ccc.bsi.eventsproject.controller.apirest.certificatecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.services.certificateService.CertificateService;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Operation(summary = "Cria um novo certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Certificado criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Certificate.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(@RequestBody Certificate certificate) {
        try {
            Certificate createdCertificate = certificateService.createCertificate(certificate);
            return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um certificado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Certificate.class)) }),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificateById(@PathVariable Long id) {
        try {
            Certificate certificate = certificateService.getCertificateById(id);
            return new ResponseEntity<>(certificate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Retorna todos os certificados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificados retornados com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Certificate.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        List<Certificate> certificates = certificateService.getAllCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Certificate.class)) }),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable Long id, @RequestBody Certificate updatedCertificate) {
        try {
            Certificate certificate = certificateService.updateCertificate(id, updatedCertificate);
            return new ResponseEntity<>(certificate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado deletado com sucesso"),
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

