package br.com.southsystem.cooperative.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Affiliated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nome;

    @Column(unique = true)
    @NotNull(message = "The CPF cannot be null")
    @NotBlank(message = "The CPF cannot be blank")
    @Size(min = 11, max = 11, message = "The cpf must be 11 digits")
    private String cpf;
}
