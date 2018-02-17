package com.lmig.ci.lmbc.empr.muw.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ApplicationRepositoryRestConfigurerAdapter extends RepositoryRestConfigurerAdapter {

	@Autowired
	private MessageSource messageSource;
	
	@Bean
	public Validator validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource);
		return validator;
	}
	
	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingRepositoryEventListener) {
		validatingRepositoryEventListener.addValidator("afterCreate", this.validator());
		validatingRepositoryEventListener.addValidator("beforeCreate", validator());
		validatingRepositoryEventListener.addValidator("afterSave", validator());
		validatingRepositoryEventListener.addValidator("beforeSave", validator());
	}
}
