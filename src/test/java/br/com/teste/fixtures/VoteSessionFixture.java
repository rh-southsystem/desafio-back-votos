package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.application.domain.entity.VoteSession;

import java.time.LocalDateTime;

public class VoteSessionFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(VoteSession.class).addTemplate("valid", new Rule() {
            {
                add("id", 1L);
                add("description", "Vote Session 01");
                add("enabled", true);
                add("startDateTime", LocalDateTime.of(2022,10,1,00,00));
                add("endDateTime", LocalDateTime.of(2022,10,5,23,59));
            }
        });

        Fixture.of(VoteSession.class).addTemplate("valid_2", new Rule() {
            {
                add("id", random(Long.class, range(10L, 15L)));
                add("description", "Vote Session 0${id}");
                add("enabled", random(Boolean.class, true, false));
                add("startDateTime", LocalDateTime.of(2022,10,1,00,00));
                add("endDateTime", LocalDateTime.of(2022,10,5,23,59));
            }
        });

    }
}
