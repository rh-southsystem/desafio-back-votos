package br.com.config;

import org.junit.runner.RunWith;

import io.cucumber.core.runner.Runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resource/feature")
public class ExecutaTest {
	
	@CucumberOptions(features = "src/test/resource/feature/pautaTest.feature")
	public static class PautaTest extends ExecutaTest {
	}

}
