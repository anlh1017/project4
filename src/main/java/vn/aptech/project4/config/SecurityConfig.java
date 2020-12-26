package vn.aptech.project4.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import vn.aptech.project4.oauth.CustomOAuth2UserService;
import vn.aptech.project4.oauth.OAuth2LoginSuccessHandler;
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
			http.authorizeRequests()
					.antMatchers("/oauth2/**").permitAll()
					.antMatchers("/user/**").authenticated()
					.and()
					.formLogin()
					.loginPage("/loginCustomer").loginProcessingUrl("/authenticateTheCustomer").failureUrl("/loginError").permitAll()
					.defaultSuccessUrl("/user")
					.and()
					.oauth2Login()
						.loginPage("/loginCustomer")
						.userInfoEndpoint().userService(oAuth2UserService)
						.and()
						.successHandler(auth2LoginSuccessHandler)
					.and()
					
					.logout().logoutUrl("/logoutCustomer").deleteCookies("JESSIONID")
					.permitAll().and().exceptionHandling().accessDeniedPage("/user/access-denied")
					.and().rememberMe().key("uniqueAndSecret");
					
					
			

		}
		
		@Autowired
		private CustomOAuth2UserService oAuth2UserService;
		@Autowired
		private OAuth2LoginSuccessHandler auth2LoginSuccessHandler;
		
	}
	 
	 
}