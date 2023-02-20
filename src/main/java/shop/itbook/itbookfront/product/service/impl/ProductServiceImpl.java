package shop.itbook.itbookfront.product.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductAdaptor productAdaptor;
    private final String DAILYHITS_COOKIENAME = "ITBOOK-VIEW";
    private final Integer ONEHOUR = 60 * 60;


    @Override
    @CacheEvict(value = "products", allEntries = true)
    public Long addProduct(MultipartFile thumbnails,
                           ProductAddRequestDto requestDto) {
        return productAdaptor.addProduct(thumbnails, requestDto);
    }

    @Override
    public PageResponse<ProductDetailsResponseDto> getProductList(String url) {

        return productAdaptor.findProductList(url);
    }

    @Override
    public PageResponse<CategoryDetailsResponseDto> getCategoryList(String url) {
        productAdaptor.findCategoryList(url);
        return productAdaptor.findCategoryList(url);
    }

    @Override
    @CacheEvict(value = "products", key = "#productNo")
    public void modifyProduct(Long productNo, MultipartFile thumbnails,
                              ProductModifyRequestDto requestDto) {
        productAdaptor.modifyProduct(productNo, thumbnails, requestDto);
    }

    @Override
    public void modifyRelationProduct(Long basedProductNo, ProductRelationRequestDto requestDto) {
        productAdaptor.modifyRelationProduct(basedProductNo, requestDto);

    }

    @Override
    @Cacheable(value = "products", key = "#productNo")
    public ProductDetailsResponseDto getProduct(Long productNo) {
        return productAdaptor.findProduct(productNo);
    }

    @Override
    @Cacheable(value = "productTypes")
    public List<ProductTypeResponseDto> findProductTypeList() {
        return productAdaptor.findProductTypeList(
            "/api/products/product-types?page=0&size=" + Integer.MAX_VALUE).getContent();
    }

    @Override
    public PageResponse<ProductSalesRankResponseDto> findSalesRankProductList
        (@PageableDefault Pageable pageable, String sortingCriteria) {
        return productAdaptor.findSalesRankProductList(pageable, sortingCriteria);
    }

    @Override
    public PageResponse<ProductRelationResponseDto> findRelationProductList(String url) {
        return productAdaptor.findRelationProductList(url);
    }

    @Override
    @CacheEvict(value = "products", key = "#productNo")
    public void changeBooleanField(Long productNo, String fieldName) {
        productAdaptor.changeBooleanField(productNo, fieldName);
    }

    @Override
    @CacheEvict(value = "products", key = "#productNo")
    public void updateDailyHits(Long productNo) {
        productAdaptor.changeDailyHits(productNo);
    }

    @Override
    public Cookie checkCookieForDailyHits(Long productNo, HttpServletRequest request,
                                          HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookieOrigin =
            Arrays.stream(cookies).filter(c -> c.getName().equals(DAILYHITS_COOKIENAME))
                .findFirst();

        if (cookieOrigin.isEmpty()) {
            Cookie newCookie = new Cookie(DAILYHITS_COOKIENAME, String.valueOf(productNo));
            newCookie.setMaxAge(ONEHOUR);
            newCookie.setPath("/");
            this.updateDailyHits(productNo);
            response.addCookie(newCookie);
            return newCookie;
        }

        Cookie cookie = cookieOrigin.get();
        if (!cookie.getValue().contains(String.valueOf(productNo))) {
            this.updateDailyHits(productNo);
            cookie.setMaxAge(ONEHOUR);
            cookie.setPath("/");
            cookie.setValue(cookie.getValue() + "/" + productNo);
            response.addCookie(cookie);
            return cookie;
        }
        return cookie;
    }

}
