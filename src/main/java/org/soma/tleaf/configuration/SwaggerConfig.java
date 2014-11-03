package org.soma.tleaf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
@EnableSwagger
public class SwaggerConfig {

	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	@Bean //Don't forget the @Bean annotation
	public SwaggerSpringMvcPlugin customImplementation(){
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
		.apiInfo(apiInfo())
		.includePatterns("/.*");
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"TLeaf API",
				"API for Shack",
				"TLeaf API terms of service",
				"tleafshack@gmail.com",
				"TLeaf API Licence Type", // ??
				"TLeaf API License URL" // rest.tleaf.us/license
				);
		return apiInfo;
	}

}
