package br.com.southsystem.cooperative.domain;

import br.com.southsystem.cooperative.domain.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "vote_date_time")
    @NotNull
    private LocalDateTime voteDateTime;

    @Column
    @NotNull(message = "The vote cannot be null")
    @Enumerated(EnumType.STRING)
    private VoteType vote;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @NotNull(message = "The session cannot be null")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "affiliated_id", referencedColumnName = "id")
    @NotNull
    private Affiliated affiliated;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voteDateTime=" + voteDateTime +
                ", vote=" + vote +
                ", session=" + session +
                ", affiliated=" + affiliated +
                '}';
    }
}
