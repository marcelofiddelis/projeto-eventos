package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.CommonUserRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserService;

@Primary
@Service
public class CommonService implements UserService<Long, CommonCreationDto, CommonUpdateDto, CommonUser> {

    @Autowired
    private CommonUserRepository commonUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public CommonUser addUser(CommonCreationDto userDto) {
        CommonUser user = new CommonUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password())); // Criptografa a senha
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
        
        // Criptografa a nova senha se fornecida
        if (updateDto.password() != null && !updateDto.password().isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(updateDto.password()));
        }
        
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

    // Método de autenticação
    public CommonUser authenticate(String cpf, String password) {
        CommonUser user = commonUserRepository.findByCpf(cpf);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Retorna o usuário se a autenticação for bem-sucedida
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
    }

}
