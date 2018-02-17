package com.lmig.ci.lmbc.empr.muw.employee.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class EmployeeRepositoryRestConfigurerAdapter extends RepositoryRestConfigurerAdapter {

    @Autowired
    private MessageSource msgSource;

   @Bean
   public Validator validator() {
	   LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
	   validator.setValidationMessageSource(msgSource);
       return validator;       
   }

   @Override
   public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
       validatingListener.addValidator("afterCreate", validator());
       validatingListener.addValidator("beforeCreate", validator());
       validatingListener.addValidator("afterSave", validator());
       validatingListener.addValidator("beforeSave", validator());
   }
}