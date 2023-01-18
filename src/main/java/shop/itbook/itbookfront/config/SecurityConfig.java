package shop.itbook.itbookfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.itbook.itbookfront.adaptor.RestTemplateAdaptor;
import shop.itbook.itbookfront.auth.filter.CustomAuthorizationFilter;
//import shop.itbook.itbookfront.auth.handler.CustomLoginSuccessHandler;
import shop.itbook.itbookfront.auth.handler.CustomLoginSuccessHandler;
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
            .antMatchers("/adminpage").hasAuthority("ROLE_ADMIN")
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
//            .successHandler(customLoginSuceessHandler(null))
            .and()
            .logout()
            .logoutUrl("/logout")
            .addLogoutHandler(customLogoutHandler(null))
            .logoutSuccessUrl("/")
            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
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
     * @param restTemplateAdaptor
     * @param gatewayConfig
     * @param redisTemplate
     * @return customAuthenticationManager
     * @author 강명관
     */
    @Bean
    public CustomAuthenticationManager customAuthenticationManager(
        RestTemplateAdaptor restTemplateAdaptor, GatewayConfig gatewayConfig, RedisTemplate<String, String> redisTemplate) {
        return new CustomAuthenticationManager(restTemplateAdaptor, gatewayConfig, redisTemplate);
    }

    /**
     * UsernamePasswordAuthenticationFilter 대신 사용할 CustomFilter 입니다.
     *
     * @return CustomAuthorizationFilter
     * @author 강명관
     */
    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter() {
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter("/doLogin");
        customAuthorizationFilter.setAuthenticationManager(customAuthenticationManager(null, null, null));
//        customAuthorizationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler(null));
        return customAuthorizationFilter;
    }

//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new SavedRequestAwareAuthenticationSuccessHandler();
//    }


    /**
     * 로그아웃과 jwt 토큰을 지우기 위한 LogoutHandler 입니다.
     *
     * @return CustomLogoutHandler
     * @author 강명관
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler(RedisTemplate redisTemplate) {
        return new CustomLogoutHandler(redisTemplate);
    }

    @Bean
    public AuthenticationSuccessHandler customLoginSuccessHandler(
        RedisTemplate<String, String> redisTemplate) {
        return new CustomLoginSuccessHandler(redisTemplate);
    }

}
