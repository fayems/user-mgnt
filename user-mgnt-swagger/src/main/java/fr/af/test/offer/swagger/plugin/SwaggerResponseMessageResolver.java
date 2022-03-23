package fr.af.test.offer.swagger.plugin;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.readers.operation.SwaggerResponseMessageReader;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author msf
 *
 */
@Component
@Order(value = SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class SwaggerResponseMessageResolver extends SwaggerResponseMessageReader {

	private static final String DEFAULT_KEY_FORMATTER = "${swagger.http-status.%d}";
	
	private String keyFormatter;
	
	/**
	 * Property holder
	 */
	@Autowired(required = false)
	private DescriptionResolver descriptions;

	/**
	 * @param typeNameExtractor
	 * @param typeResolver
	 */
	public SwaggerResponseMessageResolver(
			@Autowired(required = false) TypeNameExtractor typeNameExtractor,
			@Autowired(required = false) TypeResolver typeResolver) {
		super(typeNameExtractor, typeResolver);
	}

	@Override
	protected Set<ResponseMessage> read(OperationContext context) {
		Set<ResponseMessage> responses = super.read(context);
		Set<ResponseMessage> responseMessages = newHashSet();
		responses.forEach(response ->
			responseMessages.add(new ResponseMessageBuilder()
					.code(response.getCode())
					.message(extractHttpMessage(response))
					.responseModel(response.getResponseModel())
					.headersWithDescription(response.getHeaders())
					.build())
		);
		return responseMessages;
	}
	
	/**
	 * Return the corresponding message at http code
	 * @param response
	 * @return
	 */
	protected String extractHttpMessage(final ResponseMessage response){
		final String message = response.getMessage();
		if (StringUtils.hasText(message)) {
			return descriptions.resolve(message);
		}
		String key = keyFormatter == null ? DEFAULT_KEY_FORMATTER : keyFormatter;
		String keyFormated = String.format(key, response.getCode());
		return descriptions.resolve(keyFormated);
	}

	/**
	 * @param keyFormatter the keyFormatter to set
	 */
	public void setKeyFormatter(String keyFormatter) {
		if (keyFormatter != null){
			StringBuilder key = new StringBuilder(keyFormatter);
			if (key.indexOf("${") != 0){
				key.insert(0,  "${"); 
			}
			if (key.indexOf("}") != key.length() - 1){
				key.append("}");
			}
			this.keyFormatter = key.toString();
		}
	}

	/**
	 * @return the keyFormatter
	 */
	public String getKeyFormatter() {
		return this.keyFormatter == null ? DEFAULT_KEY_FORMATTER : this.keyFormatter;
	}

	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(DescriptionResolver descriptions) {
		this.descriptions = descriptions;
	}

}
