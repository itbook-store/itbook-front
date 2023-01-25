package shop.itbook.itbookfront.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 최겸준
 * @since 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/category-addition")
            .setViewName("categoryadmin/categoryAddSelect");
        registry.addViewController("/admin/categories/category-addition/main-category")
            .setViewName("categoryadmin/mainCategoryAddForm");
    }
}
