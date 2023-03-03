package shop.itbook.itbookfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.filter.CustomAuthorizationFilter;
import shop.itbook.itbookfront.auth.handler.CustomAccessDeniedHandler;
import shop.itbook.itbookfront.auth.handler.CustomLoginFailureHandler;
import shop.itbook.itbookfront.auth.handler.CustomLoginSuccessHandler;
import shop.itbook.itbookfront.auth.handler.CustomLogoutHandler;
import shop.itbook.itbookfront.auth.handler.CustomOAuthSuccessHandler;
import shop.itbook.itbookfront.auth.manager.CustomAuthenticationManager;
import shop.itbook.itbookfront.auth.service.CustomOauthService;
import shop.itbook.itbookfront.auth.util.AuthUtil;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * 스프링 시큐리티 설정을 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {

        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .antMatchers(AUTH_WHITELIST);
    }

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     * @author 강명관
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/adminpage/**").hasAuthority("ADMIN")
            .anyRequest().permitAll()
            .and()
            .csrf()
            .disable()
            .cors()
            .disable();

        http
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .loginProcessingUrl("/auth/login")
            .usernameParameter("memberId")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .successHandler(customLoginSuccessHandler(null))
            .failureHandler(customLoginFailureHandler());

        http
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("ITBOOK_SESSIONID")
            .addLogoutHandler(customLogoutHandler(null, null))
            .logoutSuccessUrl("/")

            .and()
            .addFilterAt(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
            .oauth2Login()
            .loginPage("/login").permitAll()
            .successHandler(customOAuthSuccessHandler(null, null))
            .failureHandler(customLoginFailureHandler())
            .userInfoEndpoint()
            .userService(customOAuth2UserService(null));

        http
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler());

        return http.build();
    }

    /**
     * 회원가입시 암호화된 비밀번호를 저장하기 위한 PasswordEncoder 입니다.
     *
     * @return passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CustomAuthorizationFilter에 사용할 CustomAuthenticationManager 입니다.
     *
     * @param authUtil 인증에 대한 공통 로직을 담당하는 클래스 입니다.
     * @return customAuthenticationManager
     * @author 강명관
     */
    @Bean
    public CustomAuthenticationManager customAuthenticationManager(AuthUtil authUtil) {
        return new CustomAuthenticationManager(authUtil);
    }

    /**
     * UsernamePasswordAuthenticationFilter 대신 사용할 CustomFilter 입니다.
     *
     * @return CustomAuthorizationFilter
     * @author 강명관
     */
    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter() {
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();
        customAuthorizationFilter.setAuthenticationManager(customAuthenticationManager(null));
        customAuthorizationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        return customAuthorizationFilter;
    }

    /**
     * 로그아웃과 jwt 토큰을 지우기 위한 LogoutHandler 입니다.
     *
     * @return CustomLogoutHandler
     * @author 강명관
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler(AuthAdaptor authAdaptor, CartService cartService) {
        return new CustomLogoutHandler(authAdaptor, cartService);
    }

    /**
     * OAuth2 Login 시, OAuth 서버에서 가져온 유저 정보를 컨트롤하기 위한 Custom OAuth2UserService 입니다.
     *
     * @param authAdaptor Shop server 해당 유저에 대한 정보가 존재하는지를 요청하기 위한 adaptor 클래스 입니다.
     * @return CsutomOAuth2UserService
     * @author 강명관
     */
    @Bean
    public CustomOauthService customOAuth2UserService(AuthAdaptor authAdaptor) {
        return new CustomOauthService(authAdaptor, passwordEncoder());
    }

    /**
     * OAuth2 로그인이 성공적으로 된 후, OAuth2 Server 에서 받은 정보를 통해 Auth서버로 보내 인가를 요청하기위한
     * CustomSuccessHandler 입니다.
     *
     * @param authUtil 인증에 대한 공통 로직을 담당하는 클래스 입니다.
     * @return CustomOAuthSuccessHandler
     * @author 강명관
     */
    @Bean
    public AuthenticationSuccessHandler customOAuthSuccessHandler(AuthUtil authUtil,
                                                                  CartService cartService) {
        return new CustomOAuthSuccessHandler(authUtil, cartService);
    }

    /**
     * AccessDenied 에 대한 처리를 위한 핸들러 입니다.
     *
     * @return CustomAccessDeniedHandler
     * @author 강명관
     */
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * 로그인 실패처리를 하기 위한 핸들러 입니다.
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    public AuthenticationFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    AuthenticationSuccessHandler customLoginSuccessHandler(CartService cartService) {
        return new CustomLoginSuccessHandler(cartService);
    }
}
