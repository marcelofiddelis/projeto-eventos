package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.CommonUserRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserService;

@Service
public class CommonService implements UserService<Long, CommonCreationDto, CommonUpdateDto, CommonUser> {

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Override
    public CommonUser addUser(CommonCreationDto userDto) {
        CommonUser user = new CommonUser(userDto);
        return commonUserRepository.save(user);
    }

    @Override
    public CommonUser getUser(Long id) throws Exception {
        return commonUserRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    @Override
    public CommonUser updateUser(Long id, CommonUpdateDto updateDto) throws Exception {
        CommonUser userToUpdate = getUser(id);
        userToUpdate.setEmail(updateDto.email());
        userToUpdate.setPhoneNumber(updateDto.phoneNumber());
        userToUpdate.setPassword(updateDto.password());
        return commonUserRepository.save(userToUpdate);
    }

    @Override
    public String deleteUser(Long id) {

        try {
            commonUserRepository.deleteById(id);
            return """
                    Usuário deletado
                    """;
        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado/problema ao deletar");
        }
    }

}
