package br.com.southsystem.cooperative.web.rest.v1;


import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.exception.CpfNotFoundException;

import br.com.southsystem.cooperative.exception.CpfUnableToVoteException;
import br.com.southsystem.cooperative.exception.handler.CustomResponseEntityExceptionHandler;
import br.com.southsystem.cooperative.service.VoteService;
import br.com.southsystem.cooperative.service.dto.VoteCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.VoteDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;


/**
 * REST controller for managing {@link br.com.southsystem.cooperative.domain.Vote}.
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "API REST Votes")
@CrossOrigin(origins = "*")
public class VoteResource {

    private final VoteService voteService;

    public VoteResource(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * {@code POST  /votes} : Create a new vote.
     *
     * @param voteCreateRequestDTO the vote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voteDTO, with status {@code 400 (Bad Request)} if cpf not have the min length,
     * with status {@code 404 (Not Found)} if the cpf is invalid or with status {@code 404 (Not Found)}
     *  with status {@code 400 (Bad Request)} if the cpf is unable to vote.
     */
    @PostMapping("/votes")
    @ApiOperation(value = "This method create a vote. The vote must be 'Sim' or 'Não' --  " +
            "@return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voteDTO, " +
            "with status {@code 400 (Bad Request)} if cpf not have the min length,\n" +
            "      with status {@code 404 (Not Found)} if the cpf is invalid,\n" +
            "with status {@code 400 (Bad Request)} if the cpf is unable to vote.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vote successfully taken"),
            @ApiResponse(code = 400, message = "Cpf not have the min length"),
            @ApiResponse(code = 404, message = "Cpf is invalid"),
            @ApiResponse(code = 400, message = "Cpf is unable to vote"),
    })
    public ResponseEntity<VoteDTO> vote(@RequestBody VoteCreateRequestDTO voteCreateRequestDTO) {
        log.debug("REST request to save Vote : {}", voteCreateRequestDTO);
        try {
            if (voteCreateRequestDTO.getAffiliatedCpf() == null || voteCreateRequestDTO.getAffiliatedCpf().trim().equals("")) {
                throw new BadRequestAlertException("The Affiliated's CPF cannot be blank");
            }
            if (voteCreateRequestDTO.getSessionId() == null) {
                throw new BadRequestAlertException("The session id cannot be null!");
            }
            VoteDTO result = voteService.vote(voteCreateRequestDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            return CustomResponseEntityExceptionHandler.handle(e);
        }catch (CpfNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CpfUnableToVoteException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
