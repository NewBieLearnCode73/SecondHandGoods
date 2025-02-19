package com.dinhchieu.demo.config;

import com.dinhchieu.demo.service.Impl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // Put jwtFilter before filterUsernamePassword
        // Meaning default using Jwt for this application
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        httpSecurity.authorizeHttpRequests(registry ->{
             // Support swagger
             registry.requestMatchers(Endpoints.SWAGGER_ENDPOINTS).permitAll();

            // Support for public endpoints
             registry.requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS).permitAll();
             registry.requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS).permitAll();

             // Support for admin endpoints
             registry.requestMatchers(HttpMethod.GET, Endpoints.ADMIN_GET_ENDPOINTS).hasRole("ADMIN");
             registry.requestMatchers(HttpMethod.POST, Endpoints.ADMIN_POST_ENDPOINTS).hasRole("ADMIN");
             registry.requestMatchers(HttpMethod.DELETE, Endpoints.ADMIN_DELETE_ENDPOINTS).hasRole("ADMIN");
             registry.requestMatchers(HttpMethod.PATCH, Endpoints.ADMIN_PATCH_ENDPOINTS).hasRole("ADMIN");

             // Support for staff endpoints
             registry.requestMatchers(HttpMethod.GET, Endpoints.STAFF_GET_ENDPOINTS).hasRole("STAFF");
             registry.requestMatchers(HttpMethod.POST, Endpoints.STAFF_POST_ENDPOINTS).hasRole("STAFF");
             registry.requestMatchers(HttpMethod.DELETE, Endpoints.STAFF_DELETE_ENDPOINTS).hasRole("STAFF");
             registry.requestMatchers(HttpMethod.PATCH, Endpoints.STAFF_PATCH_ENDPOINTS).hasRole("STAFF");

             // Support for user endpoints
             registry.requestMatchers(HttpMethod.GET, Endpoints.USER_GET_ENDPOINTS).hasRole("USER");
             registry.requestMatchers(HttpMethod.POST, Endpoints.USER_POST_ENDPOINTS).hasRole("USER");
             registry.requestMatchers(HttpMethod.DELETE, Endpoints.USER_DELETE_ENDPOINTS).hasRole("USER");
             registry.requestMatchers(HttpMethod.PATCH, Endpoints.USER_PATCH_ENDPOINTS).hasRole("USER");


             registry.anyRequest().authenticated();
         });



         httpSecurity.csrf(AbstractHttpConfigurer::disable);

         return httpSecurity.build();
    }


}
