package shop.itbook.itbookfront.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.interceptor.SessionInterceptor;
import shop.itbook.itbookfront.auth.interceptor.TokenReissueInterceptor;
import shop.itbook.itbookfront.cart.interceptor.CartInterceptor;

/**
 * @author 최겸준
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthAdaptor authAdaptor;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final List<String> staticResourcesPath =
        List.of("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.map");

    private final List<String> loginPath =
        List.of("/login", "/auth/login", "/api/members/oauth/login/find");


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/admin/category-addition")
            .setViewName("adminpage/categoryadmin/categoryAddSelect");

        registry.addViewController("/admin/products/select-add-product-type")
            .setViewName("adminpage/product/select-add-form");

        registry.addViewController("/non-member/order-details/show-select-form")
            .setViewName("mainpage/order/nonMemberOrderDetailsSelectForm");

        registry.addViewController("/non-member/order-details/show-form")
            .setViewName("mainpage/order/nonMemberOrderDetailsForm");

        registry.addViewController("/non-member/subscription-order-details/show-form")
            .setViewName("mainpage/order/nonMemberSubscriptionOrderDetailsForm");


        registry.addViewController("/error/403error").setViewName("/error/403error");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CartInterceptor(redisTemplate))
            .addPathPatterns("/**")
//            .addPathPatterns("/async/cart/**", "/cart/**")
            .excludePathPatterns(staticResourcesPath);

        registry.addInterceptor(new SessionInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(staticResourcesPath);

        registry.addInterceptor(new TokenReissueInterceptor(authAdaptor))
            .addPathPatterns("/**")
            .excludePathPatterns(loginPath)
            .excludePathPatterns("/logout")
            .excludePathPatterns(staticResourcesPath);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
