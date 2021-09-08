package br.com.southsystem.cooperative.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotBlank(message = "A new subject cannot be blank")
    @NotNull(message = "A new subject cannot be null")
    private String subject;

    @Column
    @NotNull(message = "The dateTime cannot be blank")
    private LocalDateTime dateTime;


    @OneToOne(mappedBy = "subject")
    private Session session;

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", dateTime=" + dateTime +
                ", session=" + session +
                '}';
    }
}
