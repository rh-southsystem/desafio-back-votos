package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.adapters.primary.rest.dto.VotingRequest;

public class VotingRequestFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(VotingRequest.class).addTemplate("dateExpired", new Rule() {
            {
                add("voteSessionId", 2L);
                add("associateId",  random(Long.class, range(1L, 3L)));
                add("voteType", "NO");
            }
        });

        Fixture.of(VotingRequest.class).addTemplate("invalid", new Rule() {
            {
                add("voteSessionId", 1L);
                add("associateId", 1l);
                add("voteType", random(String.class, "YES","NO"));
            }
        });

        Fixture.of(VotingRequest.class).addTemplate("valid", new Rule() {
            {
                add("voteSessionId", 1L);
                add("associateId", random(Long.class, range(2L, 3L)));
                add("voteType", random(String.class, "YES","NO"));
            }
        });

        Fixture.of(VotingRequest.class).addTemplate("unable_vote", new Rule() {
            {
                add("voteSessionId", 1L);
                add("associateId", 8L);
                add("voteType", random(String.class, "YES","NO"));
            }
        });
    }
}
