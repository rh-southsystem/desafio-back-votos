package br.com.southsystem.cooperative.service;
import br.com.southsystem.cooperative.domain.Subject;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;
import br.com.southsystem.cooperative.service.dto.SubjectResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface SubjectService {
    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    SubjectDTO save(SubjectCreateRequestDTO subjectDTO);


    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    Page<SubjectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" subject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectDTO> findOne(Long id);

    /**
     * Get the voting result of subject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    SubjectResultDTO votingResult(Long id);

    /**
     * Get the "id" subject.
     *
     * @param id the id of the domain.
     * @return the domain.
     */
    Optional<Subject> getDomainSubject(Long id);

}
