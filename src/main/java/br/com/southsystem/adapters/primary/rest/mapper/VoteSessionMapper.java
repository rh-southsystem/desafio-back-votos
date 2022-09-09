package br.com.southsystem.adapters.primary.rest.mapper;

import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResponse;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResultResponse;
import br.com.southsystem.application.domain.entity.VoteSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface VoteSessionMapper {
    VoteSessionResponse toDTO(VoteSession voteSession);

    VoteSessionResultResponse toResultDTO(VoteSession voteSession);
    @Mapping(target = "startDateTime", source = "startDateTime", qualifiedByName = "StringToLocalDateTime")
    @Mapping(target = "endDateTime", source = "endDateTime", qualifiedByName = "StringToLocalDateTime")
    VoteSession toEntity(VoteSessionRequest requestDTO);


    @Named("StringToLocalDateTime")
    default LocalDateTime defaultValueForQualifier(String strDateTime) {
        return LocalDateTime.parse(strDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
