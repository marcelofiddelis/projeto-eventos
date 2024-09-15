package br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator;

import java.util.Date;

public record AdministratorCreationDto(
    String name, 
    String password, 
    Date registerDate, 
    String acessLevel) {

}
