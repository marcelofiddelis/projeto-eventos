package br.edu.iff.ccc.bsi.eventsproject.services.usersServices;

public interface UserServiceTeste {
    public void addUser_ShouldAddUser();
    public void getUserById_ShouldReturnUserById() throws Exception;
    public void updateUser_ShouldUpdateUserById() throws Exception;
    public void deleteUser_ShouldDeleteUserById();

}
