package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices.CommonService;

@TestConfiguration
public class CommonTestConfig {
    @Bean
    public CommonService commonService(){
        return new CommonService();
    }
}
