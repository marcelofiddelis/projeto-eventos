package br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common;

import java.util.Date;

public record CommonCreationDto(
    String name, 
    String password,
    String email,
    Date registerDate,
    String accessLevel,
    String phoneNumber,
    String cpf
) {
    
}
