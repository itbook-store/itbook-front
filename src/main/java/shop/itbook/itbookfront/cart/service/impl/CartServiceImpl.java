package shop.itbook.itbookfront.cart.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.cart.adaptor.CartAdaptor;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * 장바구니의 비지니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Integer> redisTemplate;

    private final CartAdaptor cartAdaptor;

    @Override
    public boolean addProductAnonymousToCart(String cookieValue, Integer productNo) {

        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet) && productNoSet.contains(productNo)) {
            return false;
        }

        redisTemplate.opsForSet().add(cookieValue, productNo);

        return true;
    }

    @Override
    public boolean addProductMemberToCart(CartMemberRequestDto cartMemberRequestDto) {
        return cartAdaptor.addProductInCart(cartMemberRequestDto);
    }

    @Override
    public List<ProductDetailsResponseDto> getCartListAnonymous(String cookieValue) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.isNull(productNoSet) || productNoSet.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> collect = productNoSet.stream().collect(Collectors.toList());

        StringTokenizer st = new StringTokenizer(collect.toString(), "[]");
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());

            if (!st.hasMoreTokens()) {
                break;
            }
            sb.append(", ");
        }

        return cartAdaptor.getProductListAnonymous(String.valueOf(sb));
    }

    @Override
    public List<CartProductDetailsResponseDto> getCartListMember(CartMemberNoRequestDto cartMemberNoRequestDto) {
        return cartAdaptor.getProductListMember(cartMemberNoRequestDto);
    }

    @Override
    public void deleteProductAnonymousToCart(String cookieValue, Integer productNo) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet) || productNoSet.isEmpty()) {
            redisTemplate.opsForSet().remove(cookieValue, productNo);
        }
    }

    @Override
    public void deleteAllProductAnonymousToCart(String cookieValue) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet)) {
            productNoSet.forEach(o -> redisTemplate.opsForSet().remove(cookieValue, o));

        }
    }

    @Override
    public void deleteProductMemberToCart(CartMemberRequestDto cartMemberRequestDto) {
        cartAdaptor.deleteProductInCart(cartMemberRequestDto);
    }

    @Override
    public void deleteAllProductMemberToCart(CartMemberNoRequestDto cartMemberNoRequestDto) {
        cartAdaptor.deleteAllProductInCart(cartMemberNoRequestDto);
    }

    @Override
    public void modifyProductCountToCart(CartMemberRequestDto cartMemberRequestDto) {
        cartAdaptor.modifyProductCountInCart(cartMemberRequestDto);
    }
}
