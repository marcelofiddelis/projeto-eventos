package br.edu.iff.ccc.bsi.eventsproject.entities.event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.edu.iff.ccc.bsi.eventsproject.entities.certificate.Certificate;
import br.edu.iff.ccc.bsi.eventsproject.entities.dtos.event.EventCreationDto;
import br.edu.iff.ccc.bsi.eventsproject.entities.register.Registration;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.Administrator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @NotNull
    @Size(min = 1, max = 255)
    private String place;

    @Min(1)
    private int capacity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Administrator creator;

    @Size(max = 100)
    private String type;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    public Event(EventCreationDto dto){
        this.setCreator(dto.creator());
        this.setName(dto.name());
        this.setDescription(dto.description());
        this.setStartDate(dto.startDate());
        this.setEndDate(dto.endDate());
        this.setPlace(dto.place());
        this.setCapacity(dto.capacity());
        this.setType(dto.type());
    }
}
