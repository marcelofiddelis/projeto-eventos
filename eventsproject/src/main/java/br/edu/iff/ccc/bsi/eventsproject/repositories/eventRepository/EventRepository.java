package br.edu.iff.ccc.bsi.eventsproject.repositories.eventRepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Query para buscar eventos por nome
    @Query("SELECT e FROM Event e WHERE e.name = :name")
    List<Event> findByName(@Param("name") String name);
    
    // Query para buscar eventos por data de início
    @Query("SELECT e FROM Event e WHERE e.startDate = :startDate")
    List<Event> findByStartDate(@Param("startDate") Date startDate);
}

