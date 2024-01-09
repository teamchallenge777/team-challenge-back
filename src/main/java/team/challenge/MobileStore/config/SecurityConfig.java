package team.challenge.MobileStore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.challenge.MobileStore.security.RestAuthenticationEntryPoint;
import team.challenge.MobileStore.security.TokenFilter;
import team.challenge.MobileStore.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import team.challenge.MobileStore.security.oauth.OAuth2AuthenticationFailureHandler;
import team.challenge.MobileStore.security.oauth.OAuth2AuthenticationSuccessHandler;
import team.challenge.MobileStore.security.oauth.OAuthUserService;
import team.challenge.MobileStore.service.UserService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    private final UserService userService;
    private final OAuthUserService oAuthUserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final TokenFilter tokenFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandler -> exceptionHandler.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .authorizeHttpRequests(request ->
                                request.requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/devices",
                                        "/api/v1/devices/one/**",
                                        "/api/v1/devices/main-page",
                                        "/api/v1/brands",
                                        "/api/v1/brands/**",
                                        "/api/v1/catalogue",
                                        "/api/v1/catalogue/**",
                                        "/api/v1/question",
                                        "/api/v1/question/**",
                                        "/api/v1/reviews",
                                        "/api/v1/reviews/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/auth/**")
                                .permitAll()
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                        )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .authorizationEndpoint(authEndpoint ->
                                        authEndpoint
                                                .baseUri("/oauth2/authorize")
                                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                                .redirectionEndpoint(redirectEndpoint ->
                                        redirectEndpoint
                                                .baseUri("/oauth2/callback"))
                                .userInfoEndpoint(userEndpoint ->
                                        userEndpoint
                                                .userService(oAuthUserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler));
        return http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
