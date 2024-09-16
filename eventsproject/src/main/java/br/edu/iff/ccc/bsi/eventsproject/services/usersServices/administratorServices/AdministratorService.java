package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.AdministratorRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserService;

@Service
public class AdministratorService implements UserService<Long,AdministratorCreationDto, AdministratorUpdateDto,Administrator>{

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator addUser(AdministratorCreationDto userDto) {
        Administrator administrator = new Administrator(userDto);
    
        return  administratorRepository.save(administrator);
    }

    @Override
    public Administrator getUser(Long id) throws Exception{
        return administratorRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
        
    }

    @Override
    @Transactional
    public Administrator updateUser(Long id, AdministratorUpdateDto updateDto) throws Exception {
        Administrator admToBeUpdated = getUser(id);
        admToBeUpdated.setCompany(updateDto.company());
        admToBeUpdated.setEmail(updateDto.email());
        return administratorRepository.save(admToBeUpdated);
    }

    @Override
    public String deleteUser(Long id) {
        try {
            administratorRepository.deleteById(id);
            return """
                    Usuário deletado
                    """;    
        } catch (Exception e) {
            throw new RuntimeException( "Usuário não encontrado/problema ao deletar");
        }    
    }

    

   
    
}