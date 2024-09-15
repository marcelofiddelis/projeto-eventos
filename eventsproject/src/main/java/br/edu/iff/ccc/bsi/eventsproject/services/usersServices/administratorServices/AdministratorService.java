package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserService;

public class AdministratorService implements UserService<Long,AdministratorCreationDto,Administrator>{

    @Override
    public Administrator addUser(AdministratorCreationDto userDto) {
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public Administrator getUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public Administrator updateUser(Long id, AdministratorCreationDto updateDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public Administrator deleteUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }
    
}
