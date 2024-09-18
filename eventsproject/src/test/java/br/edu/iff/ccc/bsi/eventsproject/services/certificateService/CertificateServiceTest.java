package br.edu.iff.ccc.bsi.eventsproject.services.certificateService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.certificateRepository.CertificateRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.certificateService.CertificateService;

public class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private CertificateService certificateService;

    private Certificate certificate;
    private Administrator creator;
    private AdministratorCreationDto dto;
    private EventCreationDto eventCreationDto;
    private CommonUser user;
    private CommonCreationDto commonCreationDto;
    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        commonCreationDto = new CommonCreationDto("Testerson", "12451235", "teste@gmail.com", new java.util.Date(), "user",
                "235235235235", "15523142244");
        user = new CommonUser(commonCreationDto);
        
        dto = new AdministratorCreationDto("Admin",
                "admin123",
                "teste@gmail.com",
                new java.util.Date(),
                "admin",
                "IFF");
        creator = new Administrator(dto);

        eventCreationDto = new EventCreationDto(creator,
                "Test Event", "Test Description", new java.util.Date(),
                new java.util.Date(System.currentTimeMillis() + 3600000),
                "Test Place", 100, "Test Type");
        event = new Event(eventCreationDto);

        certificate = new Certificate();
        certificate.setId(1L);
        certificate.setUser(user);
        certificate.setEvent(event);
        certificate.setIssueDate(new Date());
        certificate.setDescription("Certificado de participação");
    }

    @Test
    public void createCertificate_ShouldSaveCertificate() {
        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);

        Certificate createdCertificate = certificateService.createCertificate(certificate);

        assertNotNull(createdCertificate);
        assertEquals(certificate.getId(), createdCertificate.getId());
        assertEquals(certificate.getDescription(), createdCertificate.getDescription());
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }

    @Test
    public void getCertificateById_ShouldReturnCertificate_WhenExists() throws Exception {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));

        Certificate foundCertificate = certificateService.getCertificateById(1L);

        assertNotNull(foundCertificate);
        assertEquals(certificate.getId(), foundCertificate.getId());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    public void getCertificateById_ShouldThrowException_WhenNotFound() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            certificateService.getCertificateById(1L);
        });

        assertEquals("Certificado não encontrado", exception.getMessage());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    public void updateCertificate_ShouldUpdateCertificate() throws Exception {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);

        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setUser(user);
        updatedCertificate.setEvent(event);
        updatedCertificate.setIssueDate(new Date());
        updatedCertificate.setDescription("Certificado atualizado");

        Certificate result = certificateService.updateCertificate(1L, updatedCertificate);

        assertNotNull(result);
        assertEquals("Certificado atualizado", result.getDescription());
        verify(certificateRepository, times(1)).findById(1L);
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }

    @Test
    public void deleteCertificate_ShouldDeleteCertificate_WhenExists() {
        doNothing().when(certificateRepository).deleteById(1L);

        String result = certificateService.deleteCertificate(1L);

        assertEquals("Certificado deletado com sucesso", result);
        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteCertificate_ShouldThrowException_WhenDeleteFails() {
        doThrow(new RuntimeException("Erro ao deletar certificado")).when(certificateRepository).deleteById(1L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            certificateService.deleteCertificate(1L);
        });

        assertEquals("Erro ao deletar certificado", exception.getMessage());
        verify(certificateRepository, times(1)).deleteById(1L);
    }
}
