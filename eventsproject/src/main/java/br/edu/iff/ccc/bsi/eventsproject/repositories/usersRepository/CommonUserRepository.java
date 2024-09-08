package br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;

@Repository
public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {
    
}
