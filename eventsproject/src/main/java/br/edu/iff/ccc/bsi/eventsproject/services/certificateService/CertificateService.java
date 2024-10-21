package br.edu.iff.ccc.bsi.eventsproject.services.certificateService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.repositories.certificateRepository.CertificateRepository;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    
    public Certificate createCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    
    public Certificate getCertificateById(Long id) throws Exception {
        return certificateRepository.findById(id).orElseThrow(() -> new Exception("Certificado não encontrado"));
    }

    
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public Certificate updateCertificate(Long id, Certificate updatedCertificate) throws Exception {
        Certificate existingCertificate = getCertificateById(id);
        existingCertificate.setUser(updatedCertificate.getUser());
        existingCertificate.setEvent(updatedCertificate.getEvent());
        existingCertificate.setIssueDate(updatedCertificate.getIssueDate());
        existingCertificate.setDescription(updatedCertificate.getDescription());
        return certificateRepository.save(existingCertificate);
    }

    // Método para deletar um certificado
    public String deleteCertificate(Long id) {
        try {
            certificateRepository.deleteById(id);
            return "Certificado deletado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar certificado");
        }
    }
}

