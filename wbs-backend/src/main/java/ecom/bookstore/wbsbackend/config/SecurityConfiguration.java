package ecom.bookstore.wbsbackend.config;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.filter.JwtTokenFilter;
import ecom.bookstore.wbsbackend.handlers.CustomOAuth2UserService;
import ecom.bookstore.wbsbackend.handlers.HttpCookieOAuth2AuthorizationRequestRepository;
import ecom.bookstore.wbsbackend.handlers.OAuth2AuthenticationFailureHandler;
import ecom.bookstore.wbsbackend.handlers.OAuth2AuthenticationSuccessHandler;
import ecom.bookstore.wbsbackend.utils.MapHelper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:10 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  @Autowired
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @Autowired
  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers("/auth/login", "/docs/**", "/swagger-ui/index.html#/")
        .permitAll()
        .anyRequest()
        .permitAll()
        .and()
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize")
        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
        .and()
        .redirectionEndpoint()
        .baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint()
        .userService(customOAuth2UserService)
        .and()
        .successHandler(oAuth2AuthenticationSuccessHandler)
        .failureHandler(oAuth2AuthenticationFailureHandler);

    // Exception handling configuration
    http.exceptionHandling()
        .accessDeniedHandler(
            (request, response, ex) -> {
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setStatus(HttpServletResponse.SC_OK);

              Map<String, Object> map;
              ResponseObject<?> responseObject =
                  new ResponseObject<>(HttpStatus.UNAUTHORIZED, ex.getMessage());
              map = MapHelper.convertObject(responseObject);

              response.getWriter().write(new JSONObject(map).toString());
            });

    http.exceptionHandling()
        .authenticationEntryPoint(
            (request, response, ex) -> {
              this.LOGGER.info(ex.toString());
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              Map<String, Object> map;
              ResponseObject<?> responseObject =
                  new ResponseObject<>(HttpStatus.UNAUTHORIZED, ex.getMessage());
              map = MapHelper.convertObject(responseObject);

              response.getWriter().write(new JSONObject(map).toString());
            });


    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }
}

