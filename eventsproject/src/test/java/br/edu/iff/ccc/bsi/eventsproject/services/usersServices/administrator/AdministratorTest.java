package br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administrator;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorUpdateDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import br.edu.iff.ccc.bsi.eventsproject.repositories.usersRepository.AdministratorRepository;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.UserServiceTest;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.administratorServices.AdministratorService;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AdministratorTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AdministratorTest implements UserServiceTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AdministratorService administratorService;

    private AdministratorCreationDto dto;
    private Administrator user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dto = new AdministratorCreationDto("Admin",
                "admin123",
                "teste@gmail.com",
                new java.util.Date(),
                "admin",
                "IFF");
        user = new Administrator(dto);
    }

    @Override
    @Test
    public void addUser_ShouldAddUser() {

        Mockito.when(administratorRepository.save(user)).thenReturn(user);
        Administrator adminCreated = administratorService.addUser(dto);
        Assertions.assertNotNull(adminCreated);
        Assertions.assertEquals(user.getCompany(), adminCreated.getCompany());
        Mockito.verify(administratorRepository).save(user);
    }

    @Override
    @Test
    public void getUserById_ShouldReturnUserById() throws Exception {
        Mockito.when(administratorRepository.findById(1L)).thenReturn(Optional.of(user));
        Administrator adminFound = administratorService.getUser(1L);
        Assertions.assertNotNull(adminFound);
        Assertions.assertEquals(user, adminFound);
    }

    @Override
    @Test
    public void updateUser_ShouldUpdateUserById() throws Exception {
        Mockito.when(administratorRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(administratorRepository.save(user)).thenReturn(user);
        AdministratorUpdateDto updateDto = new AdministratorUpdateDto("outroTeste@gmail.com", null);
        Administrator adminUpdated = administratorService.updateUser(1L, updateDto);
        Assertions.assertEquals("outroTeste@gmail.com", adminUpdated.getEmail());
        Mockito.verify(administratorRepository).save(user);
    }

    @Override
    @Test
    public void deleteUser_ShouldDeleteUserById() {
        administratorService.deleteUser(1L);
        Mockito.verify(administratorRepository).deleteById(1L);
    }

}
