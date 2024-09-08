package br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
   
}
