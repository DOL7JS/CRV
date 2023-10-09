package cz.upce.nnpro_backend.security;

import com.vaadin.flow.server.auth.ViewAccessChecker;
import com.vaadin.flow.spring.security.*;
import cz.upce.frontend.login.LoginView;
import cz.upce.nnpro_backend.config.JwtAuthenticationEntryPoint;
import cz.upce.nnpro_backend.config.JwtRequestFilter;
import de.codecamp.vaadin.security.spring.config.VaadinSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.access.RequestMatcherDelegatingAccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;


    public SecurityConfig(UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Vaadin auth
    //          http.authorizeRequests().antMatchers(HttpMethod.GET, "/images/*.png").permitAll();
    //          super.configure(http);
    //          setLoginView(http, LoginView.class);

    //JWT REST api auth
    //      http.csrf().disable().authorizeRequests()
    //                .antMatchers("/user/login").permitAll()
    //                .antMatchers("/swagger-ui/**").permitAll()
    //                .antMatchers("/v3/api-docs/**").permitAll()
    //                .anyRequest().authenticated().and()
    //                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
    //                .and().sessionManagement()
    //                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //api configuration
        http.
                authorizeRequests().antMatchers("/api/user/login").anonymous().and()
                .authorizeRequests().antMatchers("/swagger-ui/**").anonymous().and()
                .authorizeRequests().antMatchers("/v3/api-docs/**").anonymous().and()
                .authorizeRequests().antMatchers("/api/**").authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //Vaadin configuration
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/images/*.png").permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class);//<-- Remove when you want only use jwt authentication

        http.cors().and().csrf().ignoringAntMatchers("/api/user/login", "/api/**");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBCrypt());
    }

    @Bean
    public PasswordEncoder passwordEncoderBCrypt() {
        return new BCryptPasswordEncoder();
    }

}
