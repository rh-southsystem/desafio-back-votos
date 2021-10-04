package br.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class AbstractService<T, PK extends Serializable> implements Serializable {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
	private static final long serialVersionUID = 1L;

 	public List<T> findAll() throws ServiceException {
		try {
			return (List<T>) getRepository().findAll();

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na consulta", ex);

		}
	}

	public Optional<T> findById(PK id) throws ServiceException {
		try {
			Optional<T> t = getRepository().findById(id);
			return t;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na consulta", ex);
		}
	}
 
	public T save(T entity) throws ServiceException {
		try {
			return getRepository().save(entity);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			throw new ServiceException("Erro na gravação", ex);
		}
	}
	public void saveAll( List<T> entities ) throws ServiceException {
        try {
            getRepository().saveAll(entities);
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(),ex);
        	throw new ServiceException("Erro na gravação", ex);
        }
    }
   

	public abstract PagingAndSortingRepository<T, PK> getRepository();
} 
