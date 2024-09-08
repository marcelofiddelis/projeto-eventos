package br.edu.iff.ccc.bsi.eventsproject.entities.certificate;

import java.io.Serializable;
import java.util.Date;

import br.edu.iff.ccc.bsi.eventsproject.entities.event.Event;
import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private CommonUser user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event cannot be null")
    private Event event;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Issue date cannot be null")
    private Date issueDate;

    @NotEmpty(message = "Description cannot be empty")
    private String description;
 
}
