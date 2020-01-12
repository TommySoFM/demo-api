package web_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(securityDataSource);

		// add our users for in memory authentication
		
//		UserBuilder users = User.withDefaultPasswordEncoder();
//
//		auth.inMemoryAuthentication()
//			.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
//			.withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
//			.withUser(users.username("susan").password("test123").roles("EMPLOYEE", "ADMIN"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
//				.csrf().disable()
				.authorizeRequests()
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/showLoginPage")
				.loginProcessingUrl("/authenticateUser")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
		
}






