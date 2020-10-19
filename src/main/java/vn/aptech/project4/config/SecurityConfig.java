package vn.aptech.project4.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;

import vn.aptech.project4.customGoogle.CustomAuthenticationSuccessHandler;
import vn.aptech.project4.service.CustomerService1;

@Configuration
public class SecurityConfig {

	@Order(1)
	@Configuration
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource securityDataSource;
		@Autowired
		private UserDetailsService customUserDetailsService;
		
		public AdminSecurityConfig() {
			super();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// use jdbc authentication ... oh yeah!!!
			auth.userDetailsService(customUserDetailsService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/admin/**")
	            .authorizeRequests()
	                .anyRequest().hasAnyRole("EMPLOYEE","ADMIN","MANAGER")
       				.and().formLogin()
					.loginPage("/showMyLoginPage").loginProcessingUrl("/admin/authenticateTheUser").failureUrl("/loginErrorAdmin").permitAll()
					.defaultSuccessUrl("/admin/customer/list")
					.and().logout().logoutUrl("/logoutAdmin").deleteCookies("JESSIONID")
					.permitAll().and().exceptionHandling().accessDeniedPage("/access-denied")
					.and().rememberMe().key("uniqueAndSecret");
					
				
		}

	}


	@Order()
	@Configuration
	public static class CustomerSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
	    private OidcUserService oidcUserService;

	    @Autowired
	    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

		
		@Autowired
		public CustomerService1 customerService1;


		public CustomerSecurityConfig() {
			super();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(customerService1);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/user/**").authenticated()
					.and()
					.formLogin()
					.loginPage("/loginCustomer").loginProcessingUrl("/authenticateTheCustomer").failureUrl("/loginError").permitAll()
					.defaultSuccessUrl("/user")
					.and()
					
					.logout().logoutUrl("/logoutCustomer").deleteCookies("JESSIONID")
					.permitAll().and().exceptionHandling().accessDeniedPage("/user/access-denied")
					.and().rememberMe().key("uniqueAndSecret");
					
					
			
			http.authorizeRequests().antMatchers("/user/**").authenticated()
			.and()
            .oauth2Login()
            .loginPage("/loginCustomer")
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
            .and()
            .userInfoEndpoint()
            .oidcUserService(oidcUserService)
            .and()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize");

		}
		
		
	}
	 
	 
}