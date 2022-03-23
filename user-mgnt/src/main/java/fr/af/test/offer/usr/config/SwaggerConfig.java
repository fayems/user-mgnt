package fr.af.test.offer.usr.config;

import fr.af.test.offer.swagger.SwaggerApiInfoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan({"fr.af.test.offer.swagger.plugin"})
@EnableConfigurationProperties(SwaggerApiInfoProperties.class)
public class SwaggerConfig {

	/**
	 * Property holder
	 */
	private final DescriptionResolver descriptions;

	public SwaggerConfig(DescriptionResolver descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * @param properties
	 * @return
	 */
	@Bean
	public Docket api(SwaggerApiInfoProperties properties) {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("fr.af.test.offer.usr.controller"))
				.build()
				.apiInfo(metaData(properties))
				.useDefaultResponseMessages(false);
	}

	/**
	 * @param properties
	 * @return
	 */
	private ApiInfo metaData(final SwaggerApiInfoProperties properties) {
		final ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title(properties.getTitle())
				.description(properties.getDescription())
				.license(properties.getLicense())
				.licenseUrl(properties.getLicenseUrl())
				.version(properties.getVersion())
				.termsOfServiceUrl(properties.getTermsOfServiceUrl());

		final SwaggerApiInfoProperties.Contact contact = properties.getContact();
		if (contact != null) {
			builder.contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()));
		}
		return builder.build();
	}
}
