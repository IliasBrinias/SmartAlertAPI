package com.unipi.msc.smartalertapi.config;

import com.unipi.msc.smartalertapi.Model.User.Role;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/register","/auth/login").permitAll()
                .requestMatchers("/alert/*/notify").hasAuthority(Role.OFFICER.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//                    response.setContentType("application/json;charset=UTF-8");
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        if (authException.getLocalizedMessage().equals("Bad credentials")){
//                            jsonObject.put("message", ErrorMessages.ACCESS_DENIED);
//                            response.setStatus(403);
//                            response.getWriter().write(jsonObject.toString());
//                        }else{
//                            response.setStatus(500);
//                        }
//                    }catch (Exception ignore){}
//                })
//                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
