package fr.af.test.offer.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author msf
 *
 */
@Validated
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class SwaggerApiInfoProperties {

	/**
	 * Title api
	 */
	private String title;
	/**
	 * Description api
	 */
	private String description;
	/**
	 * Terms of service url
	 */
	private String termsOfServiceUrl;
	/**
	 * Licence name
	 */
	private String license;
	/**
	 * Licence url
	 */
	private String licenseUrl;
	/**
	 * Version
	 */
	private String version;
	/**
	 * Contact
	 */
	@Valid
	private Contact contact;

	/**
	 * Contact model
	 */
	@Getter @Setter
	public static class Contact {
		/**
		 * Contact name
		 */
		@NotNull
		private String name;
		/**
		 * Url
		 */
		private String url;
		/**
		 * Email
		 */
		private String email;
	}
}
