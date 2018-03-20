package com.teamtreehouse.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.teamtreehouse.service.UserService;
import com.teamtreehouse.web.FlashMessage;
import com.teamtreehouse.web.FlashMessage.Status;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	//password'ler sql'de plain text olarak tutuluyor. b crypt hash olarak kıyaslanacak aşağıdaki metot ile.bu yüzden
	//database'de de hashed olarak tutulmalı.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//assets içindeki herşeye erişimi engelle.
		web.ignoring().antMatchers("/assets/**");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			//bu konumda authentication yapılmıştır. hasRole metodu aşağıdaki USER'ın önüne role_ getirir. 
			//o yüzden USER yazılır.
				.anyRequest().hasRole("USER")
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
				.and()
				.logout()
				.permitAll()
				//aşağıdakinin yerine logoutsuccesshandler da kullanılabilirdi.
				.logoutSuccessUrl("/login")
				.and()
				//csrf attack için aşağıdaki eklendi. web sayfasına hidden random generated csrf tipi ekler.
				//server'da bu değer session data'ta kaydedilir.form submission yapıldığında kıyaslanır.farklıysa
				//request reject edilir.
				.csrf();
	}


	public AuthenticationSuccessHandler loginSuccessHandler() {
		//video'da lambda expression ile ;
		return (request,response,authentication) -> response.sendRedirect("/");
		//yaptı. ben de bunun yerine aşağıdaki yeni class'ı oluşturup onu döndürdüm. 
//		return new AuthenticationSuccessHandlerImpl();
	}
	
	public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			response.sendRedirect("/");
		}
		
	}


	public  AuthenticationFailureHandler loginFailureHandler() {
		return (request,response,authentication) -> {
			request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please"
					+ "try again", Status.FAILURE));
			response.sendRedirect("/login");
		};
	}
	
	//TaskDao'daki comment'e bak. spEl ile principal kullanarak şu an login olmuş kullanıcı authentication bilgilerine ihtiyaç
	// var. o yüzden aşağıdaki metot yazıldı. ana amaç bir kullanıcıyla login olunduğunda tüm task'ları değil sadece
	//o kullanıcının task'larını görmek.
	@Bean
	public EvaluationContextExtension securityExtension() {
		return new EvaluationContextExtensionSupport() {
			
			@Override
			public String getExtensionId() {
				return "security";
			}
			
			@Override
			public Object getRootObject() {
				Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
				return new SecurityExpressionRoot(authentication) {
				};
			}
			
		};
	}
	
	
	
}
