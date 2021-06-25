package com.south.voting.service.impl;

import com.south.voting.domain.AssociateEntity;
import com.south.voting.repository.AssociateRepository;
import com.south.voting.service.AssociateService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssociateServiceImpl implements AssociateService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AssociateServiceImpl.class);

    private final AssociateRepository associateRepository;

    @Autowired
    public AssociateServiceImpl(AssociateRepository associateRepository) {
       this.associateRepository = associateRepository;
    }

    @Override
    @Transactional
    public AssociateEntity save(AssociateEntity associateEntity) throws Exception {
       validateAssociate(associateEntity);
       validateDocumentExist(associateEntity.getDocument());
       return associateRepository.save(associateEntity);
    }

    private void validateAssociate(AssociateEntity associateEntity){
        Validate.notNull(associateEntity,"Informe um associado.");
        Validate.notNull(associateEntity.getDocument(),"Informe o documento do associado.");
        Validate.notBlank(associateEntity.getDocument(),"Documento não pode estar vazio");
    }

    private void validateDocumentExist(String document) throws Exception {
        LOGGER.info("Validação de documento cadastrado do associado.");
        Optional<AssociateEntity> documentOptional = associateRepository.findByDocument(document);
        if(documentOptional.isPresent()) {
            throw new Exception("Documento já cadastrado em outro associado.");
        }
    }

    @Override
    public Optional<AssociateEntity> findByid(Long id) {
        return associateRepository.findById(id);
    }
}
