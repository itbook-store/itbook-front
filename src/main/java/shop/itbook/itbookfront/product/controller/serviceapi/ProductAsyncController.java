package shop.itbook.itbookfront.product.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class ProductAsyncController {


}
