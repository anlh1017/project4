package vn.aptech.project4.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "vn.aptech.project4")
public class AppConfig implements WebMvcConfigurer {
	// set up variable to hold the properties
		@Autowired
		private Environment env;
	    @Autowired
	    private ApplicationContext applicationContext;
		// set up a logger for diagnostics
		private Logger logger = Logger.getLogger(getClass().getName());
		
		// define a bean for our security datasource
		@Bean
		public DataSource securityDataSource() {
			// create connection pool
			DataSourceBuilder securityDataSource = DataSourceBuilder.create();
			// set the jdbc driver class
			try {
				securityDataSource.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			// log the connection props
			// for sanity's sake, log this info
			// just to make sure we are REALLY reading data from properties file
			logger.info(">>> jdbc.url=" + env.getProperty("spring.datasource.url"));
			logger.info(">>> jdbc.user=" + env.getProperty("spring.datasource.username"));
			// set database connection props
			securityDataSource.url(env.getProperty("spring.datasource.url"));
			securityDataSource.username(env.getProperty("spring.datasource.username"));
			securityDataSource.password(env.getProperty("spring.datasource.password"));
			// set connection pool props

			return securityDataSource.build();
		}

	    @Bean
	    public SpringResourceTemplateResolver templateResolver() {

	        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

	        templateResolver.setApplicationContext(applicationContext);
	        templateResolver.setPrefix("classpath:/templates/");
	        templateResolver.setSuffix(".html");

	        return templateResolver;
	    }

	    @Bean
	    public SpringTemplateEngine templateEngine() {

	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver());
	        templateEngine.addDialect(new SpringSecurityDialect());
	        templateEngine.setEnableSpringELCompiler(true);

	        return templateEngine;
	    }
	    @Bean
	    public ViewResolver viewResolver() {

	        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	        ViewResolverRegistry registry = new ViewResolverRegistry(null, applicationContext);

	        resolver.setTemplateEngine(templateEngine());
	        registry.viewResolver(resolver);

	        return resolver;
	    }
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
	        registry.addResourceHandler("/skin/**").addResourceLocations("classpath:/static/skin/");
	        registry.addResourceHandler("/allcp/**").addResourceLocations("classpath:/static/allcp/");
	        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
	        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
	        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/");
			registry.addResourceHandler("/files/**").addResourceLocations("file:upload-dir/");

	        registry.addResourceHandler("/stars/**").addResourceLocations("classpath:/static/stars/");




	    }

		
}
