package br.edu.ifs.rfid.apirfid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import br.edu.ifs.rfid.apirfid.shared.SecurityFilter;

@EnableCaching
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApiRfidApplication extends SpringBootServletInitializer {

	private static final String URL_PATTERN = "/*";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
		return app.sources(ApiRfidApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiRfidApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<SecurityFilter> jwtFilter() {
		final FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new SecurityFilter());
		registrationBean.addUrlPatterns(URL_PATTERN);
		return registrationBean;
	}
}
