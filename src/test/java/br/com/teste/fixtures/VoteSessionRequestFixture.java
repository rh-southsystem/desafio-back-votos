package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;

import java.time.LocalDateTime;

public class VoteSessionRequestFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(VoteSessionRequest.class).addTemplate("valid", new Rule() {
            {
                add("description", "Vote Session 100");
                add("startDateTime", LocalDateTime.of(2022,10,1,00,00));
                add("endDateTime", LocalDateTime.of(2022,10,5,23,59));
            }
        });

    }
}
