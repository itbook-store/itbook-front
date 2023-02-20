package shop.itbook.itbookfront.category.interceptor;
//
//import java.util.List;
//import java.util.Objects;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
//import shop.itbook.itbookfront.category.model.MainCategory;
//import shop.itbook.itbookfront.category.service.CategoryService;
//import shop.itbook.itbookfront.category.util.CategoryUtil;
//import shop.itbook.itbookfront.common.response.PageResponse;
//import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
//import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
//
///**
// * @author 송다혜
// * @since 1.0
// */
//@RequiredArgsConstructor
//public class CategoryInterceptor implements HandlerInterceptor {
//
//    private final CategoryService categoryService;
//
//    public static final Integer SIZE_OF_ALL_CONTENT = Integer.MAX_VALUE;
//    public static final Integer PAGE_OF_ALL_CONTENT = 0;
//
//    @Override
//    public void postHandle(
//        HttpServletRequest request,
//        HttpServletResponse response,
//        Object handler,
//        ModelAndView modelAndView) {
//
//        PageResponse<CategoryListResponseDto> pageResponse =
//            categoryService.findCategoryList(String.format("/api/categories?page=%d&size=%d",
//                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT));
//
//        List<MainCategory> mainCategoryList =
//            CategoryUtil.getMainCategoryList(pageResponse.getContent());
//
//        modelAndView.addObject("mainCategoryList", mainCategoryList);
//    }
//}
