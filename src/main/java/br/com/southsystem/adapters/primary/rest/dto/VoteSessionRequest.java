package br.com.southsystem.adapters.primary.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@ToString
public class VoteSessionRequest {
    @NotBlank(message = "The description cannot be null")
    private String description;
    @NotNull(message = "The startDateTime cannot be null")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})", message = "the startDateTime is invalid, must be format yyyy-MM-dd HH:mm")
    private String startDateTime;
    @NotNull(message = "The endDateTime cannot be null")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})", message = "the endDateTime is invalid, must be format yyyy-MM-dd HH:mm")
    private String endDateTime;
}
