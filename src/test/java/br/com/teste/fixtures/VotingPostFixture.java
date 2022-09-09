package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.adapters.secondary.postgresql.entity.VotingPost;
import br.com.southsystem.adapters.secondary.postgresql.entity.enums.VoteTypePost;

public class VotingPostFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(VotingPost.class).addTemplate("valid", new Rule() {
            {
                add("id", 1l);
                add("voteSessionId", 1L);
                add("associateId", 1l);
                add("voteType", VoteTypePost.NO);
            }
        });

        Fixture.of(VotingPost.class).addTemplate("valid_2", new Rule() {
            {
                add("id", random(Long.class, range(2L, 10L)));
                add("voteSessionId", random(Long.class, range(2L, 10L)));
                add("associateId", random(Long.class, range(2L, 10L)));
                add("voteType", VoteTypePost.YES);
            }
        });
    }
}
