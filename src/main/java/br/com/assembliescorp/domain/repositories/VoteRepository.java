package br.com.assembliescorp.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.assembliescorp.domain.entities.VoteEntity;
import br.com.assembliescorp.domain.projections.VoteGroupProjection;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
	
	@Query("Select s.valueVote as value, count(s.id) as total FROM VoteEntity s WHERE s.session.id = :idSession GROUP BY s.valueVote")
	List<VoteGroupProjection> getCountBySession(Long idSession);
	
	@Modifying
	@Query("Update VoteEntity v set v.apurated = true where v.session.id = :idSession and v.apurated = false")
	void process(Long idSession);

}
