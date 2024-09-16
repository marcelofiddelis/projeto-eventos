package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administrator;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices.AdministratorService;

@TestConfiguration
public class AdministratorTestConfig {
    
    @Bean
    public AdministratorService administratorService(){
        return new AdministratorService();
    }
}
