package br.com.southsystem.cooperative.web.rest.v1;


import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.exception.handler.CustomResponseEntityExceptionHandler;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.dto.SessionDTO;
import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
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
 * REST controller for managing {@link br.com.southsystem.cooperative.domain.Session}.
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "API REST Sessions")
@CrossOrigin(origins = "*")
public class SessionResource {

    private final SessionService sessionService;

    public SessionResource(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * {@code POST  /sessions} : Create a new session.
     *
     * @param sessionInitRequestDTO the sessionInitRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionDTO, or with status {@code 400 (Bad Request)}.
     */
    @PostMapping("/sessions")
    @ApiOperation(value = "This method create a session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A new session"),
            @ApiResponse(code = 400, message = "A bad request")

    })
    public ResponseEntity<SessionDTO> save(@RequestBody SessionInitRequestDTO sessionInitRequestDTO) {
        log.debug("REST request to save Session : {}", sessionInitRequestDTO);
        try {
            if (sessionInitRequestDTO.getSubjectId() == null) {
                throw new BadRequestAlertException("The Subject id cannot be null!");
            }
            SessionDTO result = sessionService.init(sessionInitRequestDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return CustomResponseEntityExceptionHandler.handle(e);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * {@code GET  /sessions} : get all the sessions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessions in body.
     */
    @GetMapping("/sessions")
    @ApiOperation(value = "This method returns all sessions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all sessions")
    })
    public ResponseEntity<List<SessionDTO>> getAllSessions(Pageable pageable) {
        log.debug("REST request to get Sessions");
        Page<SessionDTO> page = sessionService.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    /**
     * {@code GET  /sessions/:id} : get the "id" session.
     *
     * @param id the id of the sessionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sessions/{id}")
    @ApiOperation(value = "This method get a session by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Session successfully retrieved"),
            @ApiResponse(code = 404, message = "Session not found")

    })
    public ResponseEntity<SessionDTO> getSession(@PathVariable Long id) {
        log.debug("REST request to get session : {}", id);
        try {
            Optional<SessionDTO> sessionDTO = sessionService.findOne(id);
            return new ResponseEntity(sessionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@code GET  /sessions/:id} : get the "id" subject.
     *
     * @param id the id of the subject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sessions/getSessionBySubjectId/{id}")
    @ApiOperation(value = "This method get a session by subject id ")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Session successfully retrieved"),
            @ApiResponse(code = 404, message = "Session not found")
    })
    public ResponseEntity<SessionDTO> getSessionBySubjectId(@PathVariable Long id) {
        try {
            log.debug("REST request to get session : {}", id);
            Optional<SessionDTO> sessionDTO = sessionService.findOneBySubjectId(id);
            return new ResponseEntity(sessionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
