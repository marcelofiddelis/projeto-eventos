package br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event;

import java.util.Date;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;

public record EventCreationDto(
    Administrator creator,
    String name,
    String description,
    Date startDate,
    Date endDate,
    String place,
    int capacity,
    String type
) {
    
}
