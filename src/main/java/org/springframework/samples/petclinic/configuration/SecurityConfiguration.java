
package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
								.antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
								.antMatchers("/users/new").permitAll()
								.antMatchers("/hotels/").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/hotels/**").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/causes/**").permitAll()
								.antMatchers("/causes/save").hasAnyAuthority("admin")
								.antMatchers("/causes/new").hasAnyAuthority("admin")
								.antMatchers("/causes/delete").hasAnyAuthority("admin")
								.antMatchers("/admin/**").hasAnyAuthority("admin")
								.antMatchers("/vet/delete/**").hasAnyAuthority("admin")
								.antMatchers("/vet/*/specialty/**").hasAnyAuthority("admin")
								.antMatchers("/dash/").hasAnyAuthority("admin")
								.antMatchers("/dash/**").hasAnyAuthority("admin")
								.antMatchers("/vet/show/**").permitAll()
								.antMatchers("/owners/**").authenticated()
								.antMatchers("/vets/**").authenticated()
								.antMatchers("/vet/**").authenticated()
								.antMatchers("/clinic/").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/clinic/**").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/clinics/**").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/clinics/").hasAnyAuthority("veterinarian", "admin")
								.antMatchers("/reservation/").hasAnyAuthority("owner", "admin")
								.antMatchers("/reservations/**").hasAnyAuthority("owner", "admin")
								.antMatchers("/providers/").hasAnyAuthority("admin")
								.antMatchers("/providers/**").hasAnyAuthority("admin")
						

								.anyRequest().denyAll().and().formLogin()
			/* .loginPage("/login") */
			.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
			.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?").passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return encoder;
	}

}
