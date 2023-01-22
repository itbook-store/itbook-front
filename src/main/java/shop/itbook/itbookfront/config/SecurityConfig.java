package shop.itbook.itbookfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.filter.CustomAuthorizationFilter;
import shop.itbook.itbookfront.auth.handler.CustomLogoutHandler;
import shop.itbook.itbookfront.auth.manager.CustomAuthenticationManager;

/**
 * 스프링 시큐리티 설정을 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@EnableWebSecurity(debug = true)
public class SecurityConfig {

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
//            .antMatchers("/adminpage").hasAuthority("ROLE_ADMIN")
//            .antMatchers("/oauth/github").authenticated()
            .anyRequest().permitAll()
            .and()
            .csrf()
            .disable()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .loginProcessingUrl("/doLogin")
            .usernameParameter("memberId")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .logoutUrl("/logout")
            .addLogoutHandler(customLogoutHandler())
            .logoutSuccessUrl("/")
            .and()
            .addFilterAt(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

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
     * @param authAdaptor Auth 서버랑 통신하는 RestTemplate Adaptor
     * @param gatewayConfig Gateway Server IP를 전역으로 사용하기 위한 Config
     * @return customAuthenticationManager
     * @author 강명관
     */
    @Bean
    public CustomAuthenticationManager customAuthenticationManager(
        AuthAdaptor authAdaptor, GatewayConfig gatewayConfig) {
        return new CustomAuthenticationManager(authAdaptor, gatewayConfig);
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
        customAuthorizationFilter.setAuthenticationManager(customAuthenticationManager(null, null));
        return customAuthorizationFilter;
    }

    /**
     * 로그아웃과 jwt 토큰을 지우기 위한 LogoutHandler 입니다.
     *
     * @return CustomLogoutHandler
     * @author 강명관
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler();
    }
}
