package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.domain.entity.enums.VoteType;

public class VotingFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Voting.class).addTemplate("valid", new Rule() {
            {
                add("id", 1l);
                add("voteSession", one(VoteSession.class, "valid"));
                add("associate", one(Associate.class, "valid"));
                add("voteType", VoteType.NO);
            }
        });

    }
}
