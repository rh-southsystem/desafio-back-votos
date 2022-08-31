package br.com.southsystem.adapters.secondary.postgresql.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "tb_votesession")
@Value
@Builder
public class VoteSessionPost {
    @Id
    private Long id;
    private String description;
    private Boolean enabled;
    @Column("start_date_time")
    private LocalDateTime startDateTime;
    @Column("end_date_time")
    private LocalDateTime endDateTime;
}
