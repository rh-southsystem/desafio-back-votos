package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;

public class VoteSessionRequestFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(VoteSessionRequest.class).addTemplate("valid", new Rule() {
            {
                add("description", "Vote Session 100");
                add("startDateTime", "2022-10-10 00:00");
                add("endDateTime", "2022-10-15 23:59");
            }
        });

    }
}
