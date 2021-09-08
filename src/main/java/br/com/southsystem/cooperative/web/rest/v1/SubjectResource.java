package br.com.southsystem.cooperative.web.rest.v1;



import br.com.southsystem.cooperative.exception.handler.CustomResponseEntityExceptionHandler;
import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;

import br.com.southsystem.cooperative.service.dto.SubjectResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link br.com.southsystem.cooperative.domain.Subject}.
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "API REST Subjects")
@CrossOrigin(origins = "*")
public class SubjectResource {

    private final SubjectService subjectService;

    public SubjectResource(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * {@code POST  /subjects} : Create a new subject.
     *
     * @param subjectDTO the subjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectDTO, or with status {@code 400 (Bad Request)}.
     */
    @PostMapping("/subjects")
    @ApiOperation(value = "This method create a subject")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A new subject"),
            @ApiResponse(code = 400, message = "A bad request, check required attributes")

    })
    public ResponseEntity<SubjectDTO> save(@RequestBody SubjectCreateRequestDTO subjectDTO) {
        log.debug("REST request to save Subject : {}", subjectDTO);
        try {
            SubjectDTO result = subjectService.save(subjectDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            return CustomResponseEntityExceptionHandler.handle(e);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * {@code GET  /subjects} : get all the subjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjects in body.
     */
    @GetMapping("/subjects")
    @ApiOperation(value = "This method returns all subjects")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all subjects")
    })
    public ResponseEntity<List<SubjectDTO>> getAllSubjects(Pageable pageable) {
        log.debug("REST request to get Subjects");
        Page<SubjectDTO> page = subjectService.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    /**
     * {@code GET  /subjects/:id} : get the "id" subject.
     *
     * @param id the id of the subjectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subjects/{id}")
    @ApiOperation(value = "This method get a subject by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Subject successfully retrieved"),
            @ApiResponse(code = 404, message = "Subject not found")

    })
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable Long id) {
        log.debug("REST request to get Subject : {}", id);
        try {
            Optional<SubjectDTO> subjectDTO = subjectService.findOne(id);
            return new ResponseEntity(subjectDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@code GET  /subjects/getSubjectWithVotingResult/:id} : get the "id" subject.
     *
     * @param id the id of the subjectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subjects/getSubjectWithVotingResult/{id}")
    @ApiOperation(value = "This method get a subject by id with voting result")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Subject successfully retrieved"),
            @ApiResponse(code = 404, message = "Subject not found"),
            @ApiResponse(code = 404, message = "The session of this subject was not opened")
    })
    public ResponseEntity<SubjectResultDTO> getSubjectWithVotingResult(@PathVariable Long id) {
        log.debug("REST request to get Subject by id with voting result: {}", id);
        try {
            SubjectResultDTO subjectDTO = subjectService.votingResult(id);
            return new ResponseEntity(subjectDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
