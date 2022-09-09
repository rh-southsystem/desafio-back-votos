package br.com.teste.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.southsystem.adapters.secondary.api.entity.APIResponse;

public class APIResponseFixture implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(APIResponse.class).addTemplate("able", new Rule() {
            {
                add("status", "ABLE_TO_VOTE");
            }
        });

        Fixture.of(APIResponse.class).addTemplate("unable", new Rule() {
            {
                add("status", "UNABLE_TO_VOTE");
            }
        });
    }
}
