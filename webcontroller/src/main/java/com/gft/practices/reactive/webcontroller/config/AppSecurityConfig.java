package com.gft.practices.reactive.webcontroller.config;

import com.gft.practices.reactive.beans.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class AppSecurityConfig {

  @Autowired
  private ServerProperties serverProperties;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    String contextPath = serverProperties.getServlet().getContextPath();
    return http
        .authorizeExchange().pathMatchers(contextPath + Constants.PATH_PATTERN).permitAll()
        .anyExchange().authenticated()
        .and().build();
  }

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User
        .withUsername("user")
        .password("11user12")
        .roles("USER")
        .build();
    return new MapReactiveUserDetailsService(user);
  }

}
