package br.com.southsystem.cooperative.domain;

import br.com.southsystem.cooperative.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @Column(name = "start_date_time")
    @NotNull(message = "The startDateTime cannot be null!")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    @NotNull(message = "The endDateTime cannot be null!")
    private LocalDateTime endDateTime;

    @Column
    private Boolean informedClosing = false;

    @OneToOne()
    @JoinColumn(name = "subject_id", referencedColumnName = "id", unique = true)
    @NotNull(message = "The subject cannot be null or invalid!")
    private Subject subject;

    @OneToMany(mappedBy = "session")
    private List<Vote> votes;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", subject=" + subject +
                ", votes=" + votes +
                '}';
    }


    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    private void setDefaultValueToEndDateTime() {
        if (getStartDateTime() == null) {
            setStartDateTime(LocalDateTime.now());
        }
        this.setEndDateTime(getStartDateTime().plusMinutes(1));
    }

    public void validateEndDateTime() {
        if (this.getEndDateTime() == null) {
            this.setDefaultValueToEndDateTime();
        } else if (LocalDateTime.now().isAfter(this.getEndDateTime())) {
            throw new InvalidParameterException("End date must be greater than current");
        }
    }

    public boolean isOpen() {
        LocalDateTime now = LocalDateTime.now();
        if (this.getEndDateTime().isAfter(now)) {
            return true;
        } else return false;
    }
}
