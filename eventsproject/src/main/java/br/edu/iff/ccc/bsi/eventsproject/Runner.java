package br.edu.iff.ccc.bsi.eventsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.AdministratorRepository;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.CommonUserRepository;

@Component
public class Runner implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;
    private final CommonUserRepository commonUserRepository;

    // Injeta o repositório no construtor
    public Runner(AdministratorRepository administratorRepository, CommonUserRepository commonUserRepository) {
        this.administratorRepository = administratorRepository;
        this.commonUserRepository = commonUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe um administrador no banco de dados
        if (administratorRepository.count() == 0) {
            // Criação de um administrador
            AdministratorCreationDto dto = new AdministratorCreationDto
            ("Admin",
             "admin123",
             "teste@gmail.com",
              new java.util.Date() ,
               "admin",
               "IFF");
            Administrator admin = new Administrator(dto);
            // admin.setName("Admin");
            // admin.setPassword("admin123");
            // admin.setRegisterDate(new java.util.Date());
            // admin.setAccessLevel("Full");

            // Salva o administrador no banco de dados
            administratorRepository.save(admin);
            System.out.println("Administrador criado com sucesso!");
        }

        CommonCreationDto dto = new CommonCreationDto("Junin", "12451235","teste@gmail.com",new java.util.Date(),"user", "235235235235", "15523142244");
        CommonUser commonUser = new CommonUser(dto);
        // commonUser.setCpf("15523142244");
        // commonUser.setName("Junin");
        // commonUser.setPassword("12451235");
        // commonUser.setRegisterDate(new java.util.Date());
        commonUserRepository.save(commonUser);

    }
}
