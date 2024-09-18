package br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event;

import java.util.Date;

public record EventUpdateDto(
    String name,
    String description,
    Date startDate,
    Date endDate,
    String place,
    int capacity,
    String type
) {
    
}
