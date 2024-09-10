package br.edu.iff.ccc.bsi.eventsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.AdministratorRepository;

@Component
public class Runner implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;

    // Injeta o repositório no construtor
    public Runner(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe um administrador no banco de dados
        if (administratorRepository.count() == 0) {
            // Criação de um administrador
            Administrator admin = new Administrator();
            admin.setName("Admin");
            admin.setPassword("admin123");
            admin.setRegisterDate(new java.util.Date());
            admin.setAccessLevel("Full");

            // Salva o administrador no banco de dados
            administratorRepository.save(admin);
            System.out.println("Administrador criado com sucesso!");
        }
    }
}
