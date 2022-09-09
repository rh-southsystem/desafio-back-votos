package br.com.southsystem.adapters.secondary.postgresql.entity;

import br.com.southsystem.adapters.secondary.postgresql.entity.enums.VoteTypePost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "tb_voting")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class VotingPost {
    @Id
    private Long id;
    @Column("vote_session_id")
    private Long voteSessionId;
    @Column("associate_id")
    private Long associateId;
    @Column("vote_type")
    private VoteTypePost voteType;

}
