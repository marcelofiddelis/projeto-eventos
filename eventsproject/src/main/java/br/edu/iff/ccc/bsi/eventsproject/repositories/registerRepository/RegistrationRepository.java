package br.edu.iff.ccc.bsi.eventsproject.repositories.registerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
}

