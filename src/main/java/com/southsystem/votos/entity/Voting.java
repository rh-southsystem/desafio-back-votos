package com.southsystem.votos.entity;

import com.southsystem.votos.enums.VoteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"associate_id", "agenda_id"})})
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "associate_id", referencedColumnName = "id")
    private Associate associate;

    @ManyToOne
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    private Agenda agenda;

    @Enumerated(EnumType.STRING)
    private VoteTypeEnum voteType;

}
