package com.southsystem.votos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime dtStart;

    private LocalDateTime dtEnd;

    @Column(columnDefinition = "boolean default false")
    private boolean active;

//    @Override
//    public String toString() {
//        return "Agenda{" +
//                "id=" + id +
//                ", description='" + description + '\'' +
//                ", dtStart=" + dtStart +
//                ", dtEnd=" + dtEnd +
//                ", active=" + active +
//                '}';
//    }
}
