package fr.af.test.offer.swagger.plugin;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import static springfox.documentation.swagger.common.SwaggerPluginSupport.pluginDoesApply;

/**
 * @author msf
 *
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class SwaggerApiModelResolver implements ModelBuilderPlugin {

	/**
	 * Property holder
	 */
	@Autowired(required = false)
	private DescriptionResolver descriptions;

	/**
	 * 
	 */
	@Autowired(required = false)
	private TypeResolver typeResolver;

	@Override
	public void apply(ModelContext context) {
		ApiModel annotation = AnnotationUtils.findAnnotation(forClass(context), ApiModel.class);
		if (annotation != null) {
			context.getBuilder().description(descriptions.resolve(annotation.description()));
		}
	}

	/**
	 * Return the model class
	 * @param context
	 * @return
	 */
	private Class<?> forClass(ModelContext context) {
		return typeResolver.resolve(context.getType()).getErasedType();
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return pluginDoesApply(delimiter);
	}

}
