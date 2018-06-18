package fluffybunny.malbunny.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("bunnyUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("Authentication Manager Builder");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		System.out.println(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("HTTP Security");
		System.out.println("Loading Load");
	    http.authorizeRequests().antMatchers("/user**")
		.access("hasRole('ROLE_ADMIN')").and().formLogin().loginProcessingUrl("/j_spring_security_check").defaultSuccessUrl("/loginsuccess")
		.loginPage("/login").failureUrl("/login?error")
		.usernameParameter("username")
		.passwordParameter("password")
		.and().logout().logoutSuccessUrl("/login?logout")
		.and().csrf()
		.and().exceptionHandling().accessDeniedPage("/403");
	}
	
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
    	System.out.println("Password Encoder");
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}