package br.edu.iff.ccc.bsi.eventsproject.services.usersServices;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.User;

/**
 * Interface relacionada ao serviço de usuários.
 * I -> Id do objeto 
 * D -> DTO do objeto
 * O -> Objeto
 * 
 */

public interface UserService <I,D,U,O extends User>{
    /**
     * 
     * @param userDto
     * @return Usuário deletado
     */
    O addUser(D userDto);
    /**
     * 
     * @param id
     * @return User pelo id
     */
    O getUser (I id) throws Exception;
    /**
     * 
     * @param id
     * @param updateDto
     * @return Usuário atualizado pelos parâmetros
     * @throws Exception 
     */
    O updateUser(I id, U updateDto) throws Exception;
    /**
     * 
     * @param id
     * @return Usuário deletado pelo id
     */
    String deleteUser(I id);
}
