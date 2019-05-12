package org.sid.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		
		/*
		auth.inMemoryAuthentication().withUser("admin").password((bcpe.encode("0000"))).roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password((bcpe.encode("0000"))).roles("USER");
		*/
				
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
			.authoritiesByUsernameQuery("select username as principal, roles as role from users_roles where username=?")
			.passwordEncoder(getBCPE())					//Md4PasswordEncoder() is depreciated
			.rolePrefix("ROLE_");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {			//en supprimant cette methode, on annule l'authentification
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");	//les url avec /admin/ necesite une authentification avec ADMIN
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/404");
		http.exceptionHandling().accessDeniedPage("/403");
	}	
	
	/*		Faire la contestualisation cad n'afficher que les option dt le simple user a besoin. on le fait avec thymeleaf ou javascript
	 *		Il suffit de verifier si le user a les droit, sinon on masque les elts inutiles
	 * */
	
	@Bean
	PasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
}
