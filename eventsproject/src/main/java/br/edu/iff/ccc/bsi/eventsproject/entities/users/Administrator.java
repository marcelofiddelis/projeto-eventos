package br.edu.iff.ccc.bsi.eventsproject.entities.users;

import java.util.List;

import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.administrator.AdministratorCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("ADMIN")
public class Administrator extends User {

    @NotNull
    @Size(min = 1, max = 50)
    private String company;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Event> events;

    // Construtor padrão
    public Administrator() {
    }
    

    public Administrator (AdministratorCreationDto dto) {
        //Dava para fazer com um Mapper, mas iria dar um pouco de trabalho para todas as operações
        this.setName(dto.name());
        this.setPassword(dto.password());
        this.setRegisterDate(dto.registerDate());
        this.setAccessLevel(dto.acessLevel());
        this.setCompany(dto.company());
        this.setEmail(dto.email());
        
    }

}
