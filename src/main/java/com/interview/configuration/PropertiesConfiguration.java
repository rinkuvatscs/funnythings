package com.interview.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class PropertiesConfiguration {

	@Value("${className}")
	private String className;
	@Value("${url}")
	private String url;
	@Value("${usrname}")
	private String username;
	@Value("${pswd}")
	private String password;
	

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource getDataSource() {
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(className);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() {
	
		return new JdbcTemplate(getDataSource());
		
	}
}
