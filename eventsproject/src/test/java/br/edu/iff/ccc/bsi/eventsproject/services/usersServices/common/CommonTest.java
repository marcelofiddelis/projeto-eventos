package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.common;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.CommonUserRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserServiceTest;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices.CommonService;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CommonTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CommonTest implements UserServiceTest {

    @Mock
    private CommonUserRepository commonUserRepository;

    @InjectMocks
    private CommonService commonService;

    private CommonCreationDto dto;
    private CommonUser user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dto = new CommonCreationDto("Testerson", "12451235", "teste@gmail.com", new java.util.Date(), "user",
                "235235235235", "15523142244");
        user = new CommonUser(dto);
    }

    @Override
    @Test
    public void addUser_ShouldAddUser() {
        Mockito.when(commonUserRepository.save(user)).thenReturn(user);
        CommonUser userCreated = commonService.addUser(dto);
        Assertions.assertNotNull(userCreated);
        Assertions.assertEquals(user.getCpf(), userCreated.getCpf());
        Mockito.verify(commonUserRepository).save(user);
    }

    @Override
    @Test
    public void getUserById_ShouldReturnUserById() throws Exception {
        Mockito.when(commonUserRepository.findById(1L)).thenReturn(Optional.of(user));
        CommonUser userFound = commonService.getUser(1L);
        Assertions.assertNotNull(userFound);
        Assertions.assertEquals(user, userFound);
    }

    @Override
    @Test
    public void updateUser_ShouldUpdateUserById() throws Exception {
        Mockito.when(commonUserRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(commonUserRepository.save(user)).thenReturn(user);
        CommonUpdateDto updateDto = new CommonUpdateDto("outroEmail@gmail.com", null, null);
        CommonUser userUpdated = commonService.updateUser(1L, updateDto);
        Assertions.assertEquals("outroEmail@gmail.com", userUpdated.getEmail());
        Mockito.verify(commonUserRepository).save(user);
    }

    @Override
    @Test
    public void deleteUser_ShouldDeleteUserById() {
        commonService.deleteUser(1L);
        Mockito.verify(commonUserRepository).deleteById(1L);
    }

}
