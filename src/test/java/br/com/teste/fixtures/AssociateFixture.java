package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.application.domain.entity.Associate;

public class AssociateFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Associate.class).addTemplate("valid", new Rule() {
            {
                add("id", 1L);
                add("name", "Emanuel Hugo");
                add("cpf", "96722007146");
            }
        });

        Fixture.of(Associate.class).addTemplate("valid_2", new Rule() {
            {
                add("id", random(Long.class, range(2L, 10L)));
                add("name", firstName() + " " + lastName());
                add("cpf", "05340638103");
            }
        });
    }
}
