package com.teamtreehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class TemplateConfig {
	
	//login olmuş user, power butonunun yanında ismi gözükecek.
	//SpringResourceTemplateResolver bulunamadı. pom.xml'e thymeleaf-spring4 eklendi.
	@Bean
	public TemplateResolver templateResolver() {
		final SpringResourceTemplateResolver templateResolver=new SpringResourceTemplateResolver();
		final TemplateResolver resolver=new TemplateResolver();
		resolver.setCacheable(false);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
//		templateResolver.setCacheable(false);
//		templateResolver.setPrefix("classpath:/templates/");
//		templateResolver.setSuffix(".html");
		return resolver;
		
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine springTemplateEngine=new SpringTemplateEngine();
		springTemplateEngine.addTemplateResolver(templateResolver());
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		return springTemplateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		
	}
	
	

}
