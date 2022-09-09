package br.com.southsystem.adapters.secondary.postgresql.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "tb_associate")
@Value
@Builder
public class AssociatePost {
    @Id
    private Long id;
    private String name;
    private String cpf;

}
