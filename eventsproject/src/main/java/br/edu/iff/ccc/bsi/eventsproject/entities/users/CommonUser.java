package br.edu.iff.ccc.bsi.eventsproject.entities.users;


import java.util.List;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.users.common.CommonCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("COMMON")
public class CommonUser extends User {
    
    @Size(max = 15)
    private String phoneNumber;
    
    @NotNull
    @Size(min = 11, max = 11) 
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    public CommonUser (CommonCreationDto dto){
        this.setName(dto.name());
        this.setPassword(dto.password());
        this.setRegisterDate(dto.registerDate());
        this.setAccessLevel(dto.accessLevel());
        this.setCpf(dto.cpf());
        this.setPhoneNumber(dto.phoneNumber());
        this.setEmail(dto.email());
    }
}
