package br.edu.iff.ccc.bsi.eventsproject.repositories.certificateRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
}
