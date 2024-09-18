package br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator;

import java.util.Date;

public record AdministratorCreationDto(
    String name, 
    String password,
    String email, 
    Date registerDate, 
    String acessLevel,
    String company) {

}
