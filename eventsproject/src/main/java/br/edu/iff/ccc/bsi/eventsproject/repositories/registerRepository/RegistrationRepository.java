package br.edu.iff.ccc.bsi.eventsproject.repositories.registerRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByUser(CommonUser user);

    @Query("SELECT e FROM Event e JOIN Registration r ON r.event.id = e.id WHERE r.user.id = :userId")
    List<Event> findEventsByUserId(@Param("userId") Long userId);
}

